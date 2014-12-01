package data.controllers;

import com.sun.xml.internal.ws.api.server.SDDocument;
import data.sorting.SortingData;

import java.sql.SQLException;
import java.util.ArrayList;

public class SortingController {

    public static ArrayList<String[]> getNames() throws SQLException {
        return SortingData.getNames();
    }

    public static ArrayList<String[]> getTotals() throws SQLException {
        ArrayList<String> invoiceCodes = SortingData.getInvoiceCodes();
        ArrayList<String[]> invoiceTotals = new ArrayList<String[]>();
        for(String i : invoiceCodes) {
            invoiceTotals.add(new String[] {i, "0"});
        }

        ArrayList<Object[]> licenceTotals = SortingData.getLicenceTotals();
        for(Object[] i : licenceTotals) {
            if(i[1].equals("C")) {
                i[0] = String.valueOf(Double.parseDouble((String)i[0]) * 1.0425);
            }
            for(String[] j : invoiceTotals) {
                if(i[2].equals(j[0])) {
                    j[1] = String.valueOf(Double.parseDouble(j[1]) + Double.parseDouble((String)i[0]));
                }
            }
        }

        ArrayList<Object[]> equipmentTotals = SortingData.getEquipmentTotals();
        for(Object[] i : equipmentTotals) {
            if(i[1].equals("C")) {
                i[0] = String.valueOf(Double.parseDouble((String)i[0]) * 1.07);
            }
            for(String[] j : invoiceTotals) {
                if(i[2].equals(j[0])) {
                    j[1] = String.valueOf(Double.parseDouble(j[1]) + Double.parseDouble((String)i[0]));
                }
            }
        }

        ArrayList<Object[]> consultationTotals = SortingData.getConsultationTotals();
        for(Object[] i : consultationTotals) {
            if(i[1].equals("C")) {
                i[0] = String.valueOf(Double.parseDouble((String)i[0]) * 1.0425);
            }
            for(String[] j : invoiceTotals) {
                if(i[2].equals(j[0])) {
                    j[1] = String.valueOf(Double.parseDouble(j[1]) + Double.parseDouble((String)i[0]));
                }
            }
        }
        return invoiceTotals;
    }

    public static ArrayList<String[]> getCustomerTypes() throws SQLException {
        return SortingData.getCustomerTypes();
    }
}
