package data.files;

import com.cinco.InvoiceData;
import data.controllers.JSONController;
import data.sorting.Sorting;
import data.sorting.SortingData;
import org.json.JSONArray;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class DataConverter {

    public static void main(String args[]) throws IOException, ParseException, SQLException {
        DataConverter dc = new DataConverter();
        dc.testStuff();
    }

    public void testStuff() throws SQLException, ParseException {

        JSONController j = new JSONController();
        JSONArray k = j.invoicesDataToJSON();

        InvoiceOutputFile io = new InvoiceOutputFile(k);
    }
}
