package data.sorting;

import data.controllers.SortingController;

import java.sql.SQLException;
import java.util.ArrayList;

public class Sorting {

    public ArrayList<String[]> sortByCustomer() throws SQLException {
        ArrayList<String[]> names = SortingController.getNames();

        for(int i = 0; i < names.size(); i++) {
            int min = i;
            for(int j = i + 1; j < names.size(); j ++) {
                System.out.println(j);
                if(names.get(j)[0].compareTo(names.get(i)[0]) < 0) {
                    min = j;
                }
            }
            String[] tmp = names.get(i);
            names.set(i, names.get(min));
            names.set(min, tmp);
        }
        return names;
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
