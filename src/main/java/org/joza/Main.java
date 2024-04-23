package org.joza;

import org.joza.connection.DatabaseCreator;

public class Main {

    public static void main(String[] args) {

        try {

            DatabaseCreator databaseCreator = new DatabaseCreator(); // DB & tables created on MySQL

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}