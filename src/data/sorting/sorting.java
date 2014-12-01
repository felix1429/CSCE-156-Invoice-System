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

    public ArrayList<String[]> sortByTotal() throws SQLException {
        ArrayList<String[]> invoiceTotals = SortingController.getTotals();

        for(int i = 0; i < invoiceTotals.size(); i++) {
            int min = i;
            for(int j = i + 1; j < invoiceTotals.size(); j ++) {
                if(Double.parseDouble(invoiceTotals.get(j)[1]) < Double.parseDouble(invoiceTotals.get(i)[1])) {
                    min = j;
                }
            }
            String[] tmp = invoiceTotals.get(i);
            invoiceTotals.set(i, invoiceTotals.get(min));
            invoiceTotals.set(min, tmp);
        }
        return invoiceTotals;
    }

    public ArrayList<String[]> sortByType() throws SQLException {
        ArrayList<String[]> customerTypes = SortingController.getCustomerTypes();
        ArrayList<String[]> corporate = new ArrayList<String[]>();
        ArrayList<String[]> government = new ArrayList<String[]>();
        for(String[] i : customerTypes) {
            if(i[0].equals("C")) {
                corporate.add(i);
            } else {
                government.add(i);
            }
        }

        ArrayList<String[]> types = new ArrayList<String[]>();
        corporate = sortBySalesperson(corporate);
        types.addAll(corporate);
        government = sortBySalesperson(government);
        types.addAll(government);
        return types;
    }

    private ArrayList<String[]> sortBySalesperson(ArrayList<String[]> in) {
        for(int i = 0; i < in.size(); i++) {
            int min = i;
            for(int j = i + 1; j < in.size(); j ++) {
                if(in.get(j)[0].compareTo(in.get(i)[0]) < 0) {
                    min = j;
                }
            }
            String[] tmp = in.get(i);
            in.set(i, in.get(min));
            in.set(min, tmp);
        }
        return in;
    }

    public ArrayList returnInvoices() {
        return new ArrayList();
    }
}
