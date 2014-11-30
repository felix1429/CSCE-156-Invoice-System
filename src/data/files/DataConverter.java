package data.files;

import com.cinco.InvoiceData;
import data.sorting.SortingData;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class DataConverter {

    public static void main(String args[]) throws IOException, ParseException, SQLException {
        DataConverter dc = new DataConverter();
        dc.testStuff();
    }

    public void testStuff() throws SQLException {
        SortingData.getNames();
    }
}
