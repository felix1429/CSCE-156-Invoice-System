package data.databaseModels;

import java.sql.*;
import java.util.List;
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

    public PreparedStatement prepareStatement(String query,  Object args[]) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(query);
        int counter = 1;
        for(Object o : args) {
            if(o instanceof String) {
                ps.setString(counter, (String) o);
            }else if(o instanceof Integer) {
                ps.setInt(counter, (Integer) o);
            }else if(o instanceof Double) {
                ps.setDouble(counter, (Double) o);
            }
            counter++;
        }
        return ps;
    }

    public void closeConnection(ResultSet rs, PreparedStatement ps) throws SQLException {
        if(rs != null && !rs.isClosed())
            rs.close();
        if(ps != null && !ps.isClosed())
            ps.close();
        if(conn != null && !conn.isClosed())
            conn.close();
    }
}
