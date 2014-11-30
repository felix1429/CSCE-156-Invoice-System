package data.sorting;

import data.databaseModels.DatabaseAccessModel;

import java.util.ArrayList;

public class SortingData {

    private ArrayList<String[]> names = new ArrayList<String[]>();
    private ArrayList<String> invoices = new ArrayList<String>();

    private static DatabaseAccessModel dam = new DatabaseAccessModel(
            "jdbc:mysql://cse.unl.edu/thennig", "thennig", "Z6nzb9");

    public static ArrayList<String[]> getNames() {

        String query = "SELECT DISTINCT PersonLastName, PersonFirstName "
                + "FROM Persons JOIN Invoices ON Invoices.PersonID = Persons.PersonID;";

        
    }
}
