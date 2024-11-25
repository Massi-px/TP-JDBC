package org.example;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private static final String DATABASE_PROPERTIES_FILE_PATH = "database.properties";
    private static Database instance;
    private Connection connection;

    private Database() {
        Properties props = new Properties();
        try (InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(DATABASE_PROPERTIES_FILE_PATH)) {
            if (inputStream == null) {
                throw new RuntimeException("Database properties file not found");
            }
            props.load(inputStream);
            String url = props.getProperty("url");
            String username = props.getProperty("username");
            String password = props.getProperty("password");
            String driver = props.getProperty("driver");
            try {
                Class.forName(driver);
                this.connection = DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException e) {
                System.err.println("Driver non chargé !");
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("Erreur lors de la connexion !");
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la lecture du fichier de configuration !");
            e.printStackTrace();
        }
    }

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}