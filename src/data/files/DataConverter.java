package data.files;

import data.databaseModels.InvoiceData;

import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;

public class DataConverter {

    public static void main(String args[]) throws IOException, ParseException, SQLException {
        DataConverter dc = new DataConverter();
        dc.testStuff();
    }

    public void testStuff() throws SQLException {
        InvoiceData.addLicenseToInvoice("INV002", "90fa", "2014-01-5", "2014-05-10");
    }
}
