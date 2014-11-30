package data.sorting;

import data.databaseModels.DatabaseAccessModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SortingData {

    private static ArrayList<String[]> names = new ArrayList<String[]>();
    private static ArrayList<String> invoices = new ArrayList<String>();

    private static DatabaseAccessModel dam = new DatabaseAccessModel(
            "jdbc:mysql://cse.unl.edu/thennig", "thennig", "Z6nzb9");

    public static ArrayList<String[]> getNames() throws SQLException {

        String query = "SELECT DISTINCT PersonLastName, PersonFirstName "
                + "FROM Persons JOIN Invoices ON Invoices.PersonID = Persons.PersonID;";

        PreparedStatement ps = dam.prepareStatement(query, new Object[] {});
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            names.add(new String[] {rs.getString("PersonLastName"),
                    rs.getString("PersonFirstName")});
        }
        dam.closeConnection(rs, ps);

        return names;
    }


}
