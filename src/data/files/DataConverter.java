package data.files;

import data.databaseModels.InvoiceData;

import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;

public class DataConverter {

    InvoiceData id = new InvoiceData();

    public static void main(String args[]) throws IOException, ParseException, SQLException {
        DataConverter dc = new DataConverter();
        dc.addDude();
    }

    public void addDude() throws SQLException {
        InvoiceData.addPerson("abc", "Trevor", "Hennig", "N 17th", "Lincoln", "NE", "68508", "USA");
    }
}
