package data.sorting;

import data.databaseModels.DatabaseAccessModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SortingData {

    private static ArrayList<String[]> names = new ArrayList<String[]>();
    private static ArrayList<Object[]> productTotals = new ArrayList<Object[]>();
    private static ArrayList<String> invoices = new ArrayList<String>();

    private static DatabaseAccessModel dam = new DatabaseAccessModel(
            "jdbc:mysql://cse.unl.edu/thennig", "thennig", "Z6nzb9");

    public static ArrayList<String[]> getNames() throws SQLException {

        names.clear();

        String query = "SELECT DISTINCT PersonLastName, PersonFirstName "
                + "FROM Persons JOIN Invoices ON Invoices.PersonID = Persons.PersonID;";

        PreparedStatement ps = dam.prepareStatement(query, new Object[] {});
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            names.add(new String[] {rs.getString("PersonLastName"),
                    rs.getString("PersonFirstName")});
        }
        dam.closeConnection(rs, ps);

        return names;
    }

    public static ArrayList<Object[]> getLicenceTotals() throws SQLException {

        productTotals.clear();

        String query = "SELECT (prod.ProductFee + (prod.PricePerYear / 365) * (SELECT DATEDIFF(inv.EndDate,inv.BeginDate))) "
                + "AS LicenseTotal, CustomerType, InvoiceCode FROM Invoices inv "
                + "JOIN Products prod ON prod.ProductID = inv.ProductID JOIN Customers cust "
                + "ON cust.CustomerID = inv.CustomerID WHERE ProductType = 'L';";

        PreparedStatement ps = dam.prepareStatement(query, new Object[] {});
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            productTotals.add(new String[] {String.valueOf(rs.getFloat("LicenseTotal")),
                    rs.getString("CustomerType"), rs.getString("InvoiceCode")});
        }

        return productTotals;
    }

    public static ArrayList<Object[]> getEquipmentTotals() throws SQLException {

        productTotals.clear();

        String query = "SELECT (prod.PricePerUnit * inv.NumberOfUnits) AS EquipmentTotal, CustomerType, InvoiceCode "
                + "FROM Invoices inv JOIN Products prod ON prod.ProductID = inv.ProductID "
                + "JOIN Customers cust ON cust.CustomerID = inv.CustomerID WHERE ProductType = 'E'";

        PreparedStatement ps = dam.prepareStatement(query, new Object[] {});
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            productTotals.add(new String[] {String.valueOf(rs.getFloat("EquipmentTotal")),
                    rs.getString("CustomerType"), rs.getString("InvoiceCode")});
        }


        return productTotals;
    }

    public static ArrayList<Object[]> getConsultationTotals() throws SQLException {

        productTotals.clear();

        String query = "SELECT (prod.ProductFee + (prod.PricePerHour * inv.NumberOfHours)) "
                + "AS ConsultationTotal, CustomerType, InvoiceCode FROM Invoices inv "
                + "JOIN Products prod ON prod.ProductID = inv.ProductID JOIN Customers cust "
                + "ON cust.CustomerID = inv.CustomerID WHERE ProductType = 'C';";

        PreparedStatement ps = dam.prepareStatement(query, new Object[] {});
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            productTotals.add(new String[] {String.valueOf(rs.getFloat("ConsultationTotal")),
                    rs.getString("CustomerType"), rs.getString("InvoiceCode")});
        }

        dam.closeConnection(rs, ps);

        return productTotals;
    }

    public static ArrayList<String> getInvoiceCodes() throws SQLException {

        ArrayList<String> invoiceCodes = new ArrayList<String>();

        String query = "SELECT DISTINCT InvoiceCode from Invoices;";

        PreparedStatement ps = dam.prepareStatement(query, new Object[] {});
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            invoiceCodes.add(rs.getString("InvoiceCode"));
        }

        return invoiceCodes;
    }

    public static ArrayList<String[]> getCustomerTypes() throws SQLException {

        ArrayList<String[]> customerTypes = new ArrayList<String[]>();

        String query = "SELECT DISTINCT CustomerType, InvoiceCode, PersonLastName, PersonFirstName"
                + " FROM Customers JOIN Invoices"
                + " ON Invoices.CustomerID = Customers.CustomerID JOIN Persons"
                + " ON Persons.PersonID = Invoices.PersonID;";

        PreparedStatement ps = dam.prepareStatement(query, new Object[] {});
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            customerTypes.add(new String[] {rs.getString("CustomerType"),
                    rs.getString("InvoiceCode"), rs.getString("PersonLastName"), rs.getString("PersonFirstName")});
        }

        dam.closeConnection(rs, ps);

        return customerTypes;
    }
}
