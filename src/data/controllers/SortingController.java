package data.controllers;

import data.sorting.SortingData;

import java.sql.SQLException;
import java.util.ArrayList;

public class SortingController {

    public static ArrayList<String[]> getNames() throws SQLException {
        return SortingData.getNames();
    }

    public static ArrayList<String[]> getTotals() throws SQLException {
        ArrayList<String[]> invoiceTotals = new ArrayList<String[]>();
        ArrayList<String> invoiceCodes = SortingData.getInvoiceCodes();

        for(String i : invoiceCodes) {
            invoiceTotals.add(new String[]{i, "0"});
        }

        applyTax("license", 1.0425, invoiceTotals);
        applyTax("consultation", 1.0425, invoiceTotals);
        applyTax("equipment", 1.07, invoiceTotals);

        return invoiceTotals;
    }

    private static void applyTax(String productType, Double tax, ArrayList<String[]> invoiceTotals) throws SQLException {

        ArrayList<Object[]> list;
        if(productType.equals("consultation")) {
            list = SortingData.getConsultationTotals();
        }else if(productType.equals("equipment")) {
            list = SortingData.getEquipmentTotals();
        }else {
            list = SortingData.getLicenceTotals();
        }

        for(Object[] i : list) {
            if(i[1].equals("C")) {
                i[0] = String.valueOf(Double.parseDouble((String)i[0]) * tax);
            }
            for(String[] j : invoiceTotals) {
                if(i[2].equals(j[0])) {
                    j[1] = String.valueOf(Double.parseDouble(j[1]) + Double.parseDouble((String)i[0]));
                }
            }
        }

    }

    public static ArrayList<String[]> getCustomerTypes() throws SQLException {
        return SortingData.getCustomerTypes();
    }
}
