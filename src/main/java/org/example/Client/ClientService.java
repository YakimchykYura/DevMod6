package org.example.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private Connection connection;
    private PreparedStatement selectAllClient;
    private PreparedStatement selectMaxIdStatement;

    public ClientService(Connection connection) {

        this.connection = connection;
        try {
            this.selectAllClient = connection.prepareStatement("SELECT *FROM client");
            this.selectMaxIdStatement = connection.prepareStatement("SELECT max(id) AS maxId FROM client");
        } catch(SQLException e) {
            System.out.println("User Service construction exception. Reason: " + e.getMessage());
        }
    }
    public long create(Client client) throws SQLException {
        long id;

        try(PreparedStatement insertStatement = connection.prepareStatement(
                "INSERT INTO client VALUES (?, ?)"
        )) {
            insertStatement.setLong(1, client.getId());
            insertStatement.setString(2, client.getName());
            insertStatement.executeUpdate();

            try(ResultSet resultSet = selectMaxIdStatement.executeQuery()) {
                resultSet.next();
                id = resultSet.getLong("maxId");
            }
        } catch (SQLException e) {
                throw new SQLException();
        }
        return id;
    }
    public String getById(long id) throws SQLException {
        String name;

        try (PreparedStatement selectByStatement = connection.prepareStatement("SELECT name FROM client WHERE id = ?")) {

            selectByStatement.setLong(1, id);
            ResultSet resultSet = selectByStatement.executeQuery();
            resultSet.next();
            name = resultSet.getString(1);
        } catch (Exception e) {
                throw new SQLException();

        }
        return name;
    }
    public void setName(long id, String name) throws SQLException {

        try(PreparedStatement selectNameStatement = connection.prepareStatement("UPDATE client SET name = ? WHERE id = ?"))
        {
                selectNameStatement.setString(1, name);
                selectNameStatement.setLong(2, id);
                selectNameStatement.executeUpdate();
            } catch (Exception e) {
            throw new SQLException();
        }
        }
    public void deleteById(long id) throws SQLException {

        try(PreparedStatement deleteByIdStatement = connection.prepareStatement("DELETE FROM client WHERE id = ?")) {
            deleteByIdStatement.setLong(1, id);
            deleteByIdStatement.executeUpdate();
        } catch (SQLException e) {
                throw new SQLException();
        }
    }
    public List<Client> listAll() throws SQLException {
        List<Client> allClients = new ArrayList<>();

        try (ResultSet allClient = selectAllClient.executeQuery()) {
            while (allClient.next()) {

                Client client = new Client();
                client.setId(allClient.getLong(1));
                client.setName(allClient.getString(2));

                allClients.add(client);
            }
        } catch (SQLException e) {
                throw new SQLException();

        }
        return allClients;
    }
}
