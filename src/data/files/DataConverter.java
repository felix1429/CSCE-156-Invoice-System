package data.files;

import com.cinco.InvoiceData;
import data.controllers.JSONController;
import data.sorting.Sorting;
import data.sorting.SortingData;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class DataConverter {

    public static void main(String args[]) throws IOException, ParseException, SQLException {
        DataConverter dc = new DataConverter();
        dc.testStuff();
    }

    public void testStuff() throws SQLException {

        JSONController j = new JSONController();
        System.out.println(j.createJSONShell("persons", j.personsDataToJSON().toString(4)));
//        Sorting s = new Sorting();
//        ArrayList<String[]> a = s.sortByTotal();
//        for(String[] i : a) {
//            for(String j : i) {
//                System.out.print(j + " ");
//            }
//            System.out.println(" ");
//        }
    }
}
