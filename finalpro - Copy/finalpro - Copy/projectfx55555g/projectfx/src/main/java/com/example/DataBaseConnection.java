package com.example;
import java.sql.Connection;
import java.sql.DriverManager;
public class DataBaseConnection {
    public Connection DatabaseLink;
    public Connection getConnection() {
        String databaseName = "postgres";
        String databaseUser = "postgres";
        String databasePassword = "123456";
        String databaseUrl = "jdbc:postgresql://localhost:5432/postgres";


        try{
            Class.forName("org.postgresql.Driver");
            DatabaseLink = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return DatabaseLink;
    }

}


