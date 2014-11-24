package data.files;

import data.databaseModels.InvoiceData;

import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;

public class DataConverter {

    InvoiceData id = new InvoiceData();

    public static void main(String args[]) throws IOException, ParseException, SQLException {
        DataConverter dc = new DataConverter();
        dc.testStuff();
    }

    public void testStuff() throws SQLException {
        InvoiceData.addEquipmentToInvoice("INV004", "b29e", 1000);
    }
}
