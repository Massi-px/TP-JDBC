package org.example;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        DAOAcces daoAcces = new DAOAcces();
        daoAcces.listAcces();

        daoAcces.stopConnection();
    }
}
