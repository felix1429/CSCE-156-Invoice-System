package data.databaseModels;

import java.sql.*;

class DatabaseAccessModel {

    private String url;
    private String username;
    private String password;

    private static Connection conn;

    public DatabaseAccessModel(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        this._instantiateDatabase();
    }

    private Connection _instantiateDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            System.out.println("InstantiationException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        try {
            conn = DriverManager.getConnection(this.url, this.username, this.password);
        } catch (SQLException e) {
            System.out.println("SQLException: ");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return conn;
    }

    public Connection getConnection() {
        return conn;
    }
}
