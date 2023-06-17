package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    public static String getConnectionUrl() {
        try{
            InputStream stream = PropertyReader.class.getClassLoader().getResourceAsStream("applications.properties");
            Properties properties = new Properties();

            if (stream == null) {
                System.out.println("Did not find applications.properties");
                return null;
            }
            properties.load(stream);
            return new StringBuilder("jdbc:")
                    .append(properties.getProperty("db.host"))
                    .append(":./")
                    .append(properties.getProperty("db.database"))
                    .toString();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
