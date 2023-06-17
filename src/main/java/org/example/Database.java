package org.example;

import org.flywaydb.core.Flyway;

import java.sql.*;

public class Database {
    private static final Database INSTANCE = new Database();

    private Connection CONNECTION;
    private Database() {
        try{
            String connectionUrl = PropertyReader.getConnectionUrl();
            this.CONNECTION = DriverManager.getConnection(connectionUrl);
            flywayMigration(connectionUrl, null, null);
        } catch (SQLException ex) {
            throw new RuntimeException("Connection error");
        }
    }
    public static Database getInstance() {
        return INSTANCE;
    }
    public Connection getCONNECTION() {
        return CONNECTION;
    }
    public void closeConnection() {
        try{
            CONNECTION.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    private void flywayMigration(String connectionUrl, String userName, String password) {
        Flyway flyway = Flyway.configure().dataSource(connectionUrl, userName, password).load();
        flyway.migrate();
    }
}
