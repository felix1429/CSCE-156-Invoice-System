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
        InvoiceData.addConsultationToInvoice("INV002", "782g", 500);
    }
}
