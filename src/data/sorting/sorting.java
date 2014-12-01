package data.sorting;

import data.controllers.SortingController;

import java.sql.SQLException;
import java.util.ArrayList;

public class Sorting implements Comparable<String> {

    @Override
    public int compareTo(String s) {
        int result = this.compareTo(s);

        if(result != 0) {
            return result;
        }

        return 0;
    }

    private void sortByCustomer() throws SQLException {
        ArrayList<String[]> names = SortingController.getNames();
        String[] namesConcat = new String[names.size()];
        for(String[] i : names) {
            namesConcat[names.lastIndexOf(i)] = i[0] + i[1  ];
        }

        for(int i = 0; i < namesConcat.length; i++) {
            int min = i;
            for(int j = i + 1; j < namesConcat.length; j ++) {
                if(namesConcat.compareTo())
            }
        }
    }

    private void sortByTotal() {

    }

    private void sortByType() {

    }

    private void sortBySalesperson() {

    }

    public ArrayList returnInvoices() {
        return new ArrayList();
    }
}
