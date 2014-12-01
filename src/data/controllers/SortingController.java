package data.controllers;

import data.sorting.SortingData;

import java.sql.SQLException;
import java.util.ArrayList;

public class SortingController {

    public static ArrayList<String[]> getNames() throws SQLException {
        return SortingData.getNames();
    }


}
