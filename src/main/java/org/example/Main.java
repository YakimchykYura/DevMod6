package org.example;

import org.example.Client.Client;
import org.example.Client.ClientService;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = Database.getInstance().getCONNECTION();

        Client client1 = new Client();
        client1.setId(6);
        client1.setName("inna");

        ClientService client = new ClientService(connection);
        long idClient1 = client.create(client1);
        System.out.println(idClient1);

        System.out.println(client.getById(6));

        client.setName(6, "Ihor");

        client.deleteById(6);

        for (int i = 0; i < client.listAll().size(); i++) {
            System.out.println(client.listAll().get(i));
        }

        Database.getInstance().closeConnection();
    }
}