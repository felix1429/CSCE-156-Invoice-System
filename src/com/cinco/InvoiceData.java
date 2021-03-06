package com.cinco;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sun.applet2.AppletParameters;
import data.databaseModels.DatabaseAccessModel;

/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 *
 */
public class InvoiceData {

	private static DatabaseAccessModel dam = new DatabaseAccessModel(
			"jdbc:mysql://cse.unl.edu/thennig", "thennig", "Z6nzb9");


	public static void removeAllPersons() throws SQLException {

		String query = "DELETE FROM Persons";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {});
		ps.executeUpdate();

		dam.closeConnection(ps);
	}
	
	public static void removePerson(String personCode) throws SQLException {

		String query = "DELETE FROM Persons where PersonCode = ?;";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {personCode});
		ps.executeUpdate();

		dam.closeConnection(ps);
	}
	
	public static void addPerson(String personCode, String firstName, String lastName,
			String street, String city, String state, String zip, String country) throws SQLException {

		Integer personID = null;

		String query = "SELECT * FROM Persons WHERE "
				+ "PersonCode = ? AND PersonFirstName = ? AND PersonLastName = ?;";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {personCode, firstName, lastName});
		ResultSet rs = ps.executeQuery();

		while(rs.next()){
			personID = (Integer) rs.getObject("PersonID");
		}

		if(personID == null) {
			query = "INSERT INTO PersonAddress (Street, City, CityState, ZipCode, Country) "
					+ "VALUES(?,?,?,?,?)";

			ps = dam.prepareStatement(query, new Object[] {street, city, state, zip, country});
			ps.executeUpdate();

			query = "INSERT INTO Persons (PersonCode, PersAddressID, PersonLastName, PersonFirstName) "
					+ "VALUES(?,LAST_INSERT_ID(),?,?);";

			ps = dam.prepareStatement(query, new Object[] {personCode, lastName, firstName});
			ps.executeUpdate();
		}

		dam.closeConnection(rs, ps);
	}
	
	public static void addEmail(String personCode, String email) throws SQLException {

		String query = "INSERT INTO Email (PersonID, EmailAddress) VALUES "
				+ "((SELECT PersonID FROM Persons WHERE PersonCode = ?),?);";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {personCode, email});
		ps.executeUpdate();

		dam.closeConnection(ps);
	}
	
	public static void removeAllCustomers() throws SQLException {

		String query = "DELETE FROM Customers";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {});
		ps.executeUpdate();

		dam.closeConnection(ps);
	}

	public static void addCustomer(String customerCode, String type, String primaryContactPersonCode, String name, 
			String street, String city, String state, String zip, String country) throws SQLException {

		Integer customerID = null;

		String query = "SELECT * FROM Customers WHERE "
				+ "CustomerCode = ? AND CustomerType = ? AND CustomerName = ?;";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {customerCode, type, name});
		ResultSet rs = ps.executeQuery();

		while(rs.next()){
			customerID = (Integer) rs.getObject("CustomerID");
		}

		if(customerID == null) {
			query = "INSERT INTO CustomerAddress (Street, City, CityState, ZipCode, Country) "
					+ "VALUES(?,?,?,?,?);";

			ps = dam.prepareStatement(query, new Object[] {street, city, state, zip, country});
			ps.executeUpdate();

			query = "INSERT INTO Customers (CustAddressID, CustomerCode, CustomerType, PersonCode, PersonID, CustomerName) "
					+ "Values (LAST_INSERT_ID(),?,?,?,(SELECT PersonID FROM Persons WHERE PersonCode = ?),?);";

			ps = dam.prepareStatement(query, new Object[] {customerCode, type, primaryContactPersonCode,
					primaryContactPersonCode, name});
			ps.executeUpdate();
		}

		dam.closeConnection(rs, ps);
	}
	
	public static void removeAllProducts() throws SQLException {

		String query = "DELETE FROM Products";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {});
		ps.executeUpdate();

		dam.closeConnection(ps);
	}
	
	public static void removeProduct(String productCode) throws SQLException {

		String query = "DELETE FROM Products where ProductCode = ?;";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {productCode});
		ps.executeUpdate();

		dam.closeConnection(ps);
	}
	
	public static void addEquipment(String productCode, String name, Double pricePerUnit) throws SQLException {

		Integer productID = null;

		String query = "SELECT * FROM Products WHERE ProductCode = ? AND ProductName = ? AND PricePerUnit = ?;";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {productCode, name, pricePerUnit});
		ResultSet rs = ps.executeQuery();

		while(rs.next()) {
			productID = (Integer) rs.getObject("ProductID");
		}

		if(productID == null) {

			query = "INSERT INTO Products (ProductCode, ProductType, ProductName, PersonID, "
					+ "PricePerUnit, ProductFee, PricePerHour, PricePerYear) "
					+ "VALUES(?,\"E\",?,NULL,?,0,0,0);";

			ps = dam.prepareStatement(query, new Object[] {productCode, name, pricePerUnit});
			ps.executeUpdate();
		}

		dam.closeConnection(rs, ps);
	}
	
	public static void addLicense(String productCode, String name, double serviceFee, double annualFee) throws SQLException {

		Integer productID = null;

		String query = "SELECT * FROM Products WHERE ProductCode = ? AND ProductName = ? AND "
				+ "ProductFee = ? AND PricePerYear = ?;";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {productCode, name, serviceFee, annualFee});
		ResultSet rs = ps.executeQuery();

		while(rs.next()) {
			productID = (Integer) rs.getObject("ProductID");
		}

		if(productID == null) {

			query = "INSERT INTO Products (ProductCode, ProductType, ProductName, PersonID, "
					+ "PricePerUnit, ProductFee, PricePerHour, PricePerYear) "
					+ "VALUES(?,\"L\",?,NULL,0,?,0,?);";

			ps = dam.prepareStatement(query, new Object[] {productCode, name, serviceFee, annualFee});
			ps.executeUpdate();
		}

		dam.closeConnection(rs, ps);

	}
	
	public static void addConsultation(String productCode, String name, String consultantPersonCode, Double hourlyFee) throws SQLException {

		Integer productID = null;

		String query = "SELECT * FROM Products WHERE ProductCode = ? AND ProductName = ? AND "
				+ "(SELECT PersonID FROM Persons WHERE PersonCode = ?) AND PricePerHour = ?;";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {productCode, name, consultantPersonCode, hourlyFee});
		ResultSet rs = ps.executeQuery();

		while(rs.next()) {
			productID = (Integer) rs.getObject("ProductID");
		}

		if(productID == null) {

			query = "INSERT INTO Products (ProductCode, ProductType, ProductName, PersonID, "
					+ "PricePerUnit, ProductFee, PricePerHour, PricePerYear) "
					+ "VALUES(?,\"C\",?,(SELECT PersonID FROM Persons WHERE PersonCode = ?),0,150.00,?,0);";

			ps = dam.prepareStatement(query, new Object[] {productCode, name, consultantPersonCode, hourlyFee});
			ps.executeUpdate();
		}

		dam.closeConnection(rs, ps);

	}
	
	public static void removeAllInvoices() throws SQLException {

		String query = "DELETE FROM Invoices";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {});
		ps.executeUpdate();

		dam.closeConnection(ps);

	}
	
	public static void removeInvoice(String invoiceCode) throws SQLException {

		String query = "DELETE FROM Invoices where InvoiceCode = ?;";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {invoiceCode});
		ps.executeUpdate();

		dam.closeConnection(ps);

	}
	
	public static void addInvoice(String invoiceCode, String customerCode, String salesPersonCode) throws SQLException {

		Integer invoiceID = null;

		String query = "SELECT * FROM Invoices WHERE InvoiceCode = ? AND "
				+ "(SELECT CustomerID FROM Customers WHERE CustomerCode = ?) AND "
				+ "(SELECT PersonID FROM Persons WHERE PersonCode = ?);";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {invoiceCode, customerCode, salesPersonCode});
		ResultSet rs = ps.executeQuery();

		while(rs.next()) {
			invoiceID = (Integer) rs.getObject("InvoiceID");
		}

		if(invoiceID == null) {

			query = "INSERT INTO Invoices "
					+ "(InvoiceCode,CustomerID,PersonID,ProductID,NumberOfUnits,NumberOfHours,BeginDate,EndDate) "
					+ "VALUES(?,(SELECT CustomerID FROM Customers WHERE CustomerCode = ?),"
					+ "(SELECT PersonID FROM Persons WHERE PersonCode = ?),NULL,0,0,NULL,NULL);";

			ps = dam.prepareStatement(query, new Object[] {invoiceCode, customerCode, salesPersonCode});
			ps.executeUpdate();

		}
		dam.closeConnection(rs, ps);
	}
	
	public static void addEquipmentToInvoice(String invoiceCode, String productCode, int numUnits) throws SQLException {

		Integer invoiceID = null;
		boolean found = false;
		int customerID = 0;
		int personID = 0;

		String query = "SELECT * FROM Invoices WHERE InvoiceCode = ?;";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {invoiceCode});
		ResultSet rs = ps.executeQuery();

		while(rs.next()) {
			invoiceID = (Integer) rs.getObject("InvoiceID");
			found = invoiceCode.equals(rs.getString("InvoiceCode"));
			if(found) {
				break;
			}
		}

		if(invoiceID != null && found) {

			query = "INSERT INTO Invoices "
					+ "(InvoiceCode,CustomerID,PersonID,ProductID,NumberOfUnits,NumberOfHours,BeginDate,EndDate) "
					+ "VALUES(?,NULL,NULL,(SELECT ProductID FROM Products WHERE ProductCode = ?),"
					+ "?,0,NULL,NULL);";

			ps = dam.prepareStatement(query, new Object[] {invoiceCode, productCode, numUnits});
			ps.executeUpdate();


			query = "SELECT CustomerID FROM Invoices WHERE InvoiceCode = ?;";
			ps = dam.prepareStatement(query, new Object[] {invoiceCode});
			rs = ps.executeQuery();
			while(rs.next()) {
				customerID = rs.getInt("CustomerID");
				if (customerID == (rs.getInt("CustomerID"))) {
					break;
				}
			}

			query = "UPDATE Invoices SET CustomerID = ? WHERE InvoiceCode = ?;";
			ps = dam.prepareStatement(query, new Object[] {customerID, invoiceCode});
			ps.executeUpdate();

			query = "SELECT PersonID FROM Invoices WHERE InvoiceCode = ?;";
			ps = dam.prepareStatement(query, new Object[] {invoiceCode});
			rs = ps.executeQuery();
			while(rs.next()) {
				personID = rs.getInt("PersonID");
				if (personID == (rs.getInt("PersonID"))) {
					break;
				}
			}

			query = "UPDATE Invoices SET PersonID = ? WHERE InvoiceCode = ?;";
			ps = dam.prepareStatement(query, new Object[] {personID, invoiceCode});
			ps.executeUpdate();

		}
		dam.closeConnection(rs, ps);
	}
	
	public static void addLicenseToInvoice(String invoiceCode, String productCode, String startDate, String endDate) throws SQLException {

		Integer invoiceID = null;
		boolean found = false;
		int customerID = 0;
		int personID = 0;

		String query = "SELECT * FROM Invoices WHERE InvoiceCode = ?;";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {invoiceCode});
		ResultSet rs = ps.executeQuery();

		while(rs.next()) {
			invoiceID = (Integer) rs.getObject("InvoiceID");
			found = invoiceCode.equals(rs.getString("InvoiceCode"));
			if(found) {
				break;
			}
		}

		if(invoiceID != null && found) {
			query = "INSERT INTO Invoices "
					+ "(InvoiceCode,CustomerID,PersonID,ProductID,NumberOfUnits,NumberOfHours,BeginDate,EndDate) "
					+ "VALUES(?,NULL,NULL,(SELECT ProductID FROM Products WHERE ProductCode = ?),"
					+ "0,0,?,?);";

			Boolean isString = true;
			ps = dam.prepareStatement(query, isString, new Object[] {invoiceCode, productCode, startDate, endDate});
			ps.executeUpdate();


			query = "SELECT CustomerID FROM Invoices WHERE InvoiceCode = ?;";
			ps = dam.prepareStatement(query, new Object[] {invoiceCode});
			rs = ps.executeQuery();
			while(rs.next()) {
				customerID = rs.getInt("CustomerID");
				if (customerID == rs.getInt("CustomerID")) {
					break;
				}
			}

			query = "UPDATE Invoices SET CustomerID = ? WHERE InvoiceCode = ?;";
			ps = dam.prepareStatement(query, new Object[] {customerID,invoiceCode});
			ps.executeUpdate();

			query = "SELECT PersonID FROM Invoices WHERE InvoiceCode = ?;";
			ps = dam.prepareStatement(query, new Object[] {invoiceCode});
			rs = ps.executeQuery();
			while(rs.next()) {
				personID = rs.getInt("PersonID");
				if (personID == (rs.getInt("PersonID"))) {
					break;
				}
			}

			query = "UPDATE Invoices SET PersonID = ? WHERE InvoiceCode = ?;";
			ps = dam.prepareStatement(query, new Object[] {personID, invoiceCode});
			ps.executeUpdate();

		}
		dam.closeConnection(rs, ps);
	}
	
	public static void addConsultationToInvoice(String invoiceCode, String productCode, double numHours) throws SQLException {

		Integer invoiceID = null;
		boolean found = false;
		int customerID = 0;
		int personID = 0;

		String query = "SELECT * FROM Invoices WHERE InvoiceCode = ?;";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {invoiceCode});
		ResultSet rs = ps.executeQuery();

		while(rs.next()) {
			invoiceID = (Integer) rs.getObject("InvoiceID");
			found = invoiceCode.equals(rs.getString("InvoiceCode"));
			if(found) {
				break;
			}
		}
		if(invoiceID != null && found) {

			query = "INSERT INTO Invoices "
					+ "(InvoiceCode,CustomerID,PersonID,ProductID,NumberOfUnits,NumberOfHours,BeginDate,EndDate) "
					+ "VALUES(?,NULL,NULL,(SELECT ProductID FROM Products WHERE ProductCode = ?),"
					+ "0,?,NULL,NULL);";

			ps = dam.prepareStatement(query, new Object[] {invoiceCode, productCode, numHours});
			ps.executeUpdate();


			query = "SELECT CustomerID FROM Invoices WHERE InvoiceCode = ?;";
			ps = dam.prepareStatement(query, new Object[] {invoiceCode});
			rs = ps.executeQuery();

			while(rs.next()) {
				customerID = rs.getInt("CustomerID");
				if (customerID == (rs.getInt("CustomerID"))) {
					break;
				}
			}

			query = "UPDATE Invoices SET CustomerID = ? WHERE InvoiceCode = ?;";
			ps = dam.prepareStatement(query, new Object[] {customerID, invoiceCode});
			ps.executeUpdate();

			query = "SELECT PersonID FROM Invoices WHERE InvoiceCode = ?;";
			ps = dam.prepareStatement(query, new Object[] {invoiceCode});
			rs = ps.executeQuery();
			while(rs.next()) {
				personID = rs.getInt("PersonID");
				if (personID == (rs.getInt("PersonID"))) {
					break;
				}
			}

			query = "UPDATE Invoices SET PersonID = ? WHERE InvoiceCode = ?;";
			ps = dam.prepareStatement(query, new Object[] {personID, invoiceCode});
			ps.executeUpdate();

		}
		dam.closeConnection(rs, ps);
	}

	public ArrayList<HashMap<String, String>> getProductData() throws SQLException {

		String query = "SELECT * FROM Products";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {});
		ResultSet rs = ps.executeQuery();

		ArrayList<HashMap<String, String>> results = new ArrayList<HashMap<String, String>>();
		while(rs.next()) {
			HashMap<String, String> product = new HashMap<String, String>();
			product.put("ProductID", rs.getString("ProductID"));
			product.put("ProductCode", rs.getString("ProductCode"));
			product.put("ProductType", rs.getString("ProductType"));
			product.put("ProductName", rs.getString("ProductName"));
			product.put("PersonID", rs.getString("PersonID"));
			product.put("PricePerUnit", String.valueOf(rs.getFloat("PricePerUnit")));
			product.put("ProductFee", String.valueOf(rs.getFloat("ProductFee")));
			product.put("PricePerHour", String.valueOf(rs.getFloat("PricePerHour")));
			product.put("PricePerYear", String.valueOf(rs.getFloat("PricePerYear")));
			results.add(product);
		}

		return results;
	}

	public HashMap<String, String> getProductDataFromCode(String productCode) throws SQLException {

		String query = "SELECT * FROM Products WHERE ProductID = ?";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {productCode});
		ResultSet rs = ps.executeQuery();

		HashMap<String, String> results = new HashMap<String, String>();
		while(rs.next()) {
			results.put("ProductID", rs.getString("ProductID"));
			results.put("ProductCode", rs.getString("ProductCode"));
			results.put("ProductType", rs.getString("ProductType"));
			results.put("ProductName", rs.getString("ProductName"));
			results.put("PersonID", rs.getString("PersonID"));
			results.put("PricePerUnit", String.valueOf(rs.getFloat("PricePerUnit")));
			results.put("ProductFee", String.valueOf(rs.getFloat("ProductFee")));
			results.put("PricePerHour", String.valueOf(rs.getFloat("PricePerHour")));
			results.put("PricePerYear", String.valueOf(rs.getFloat("PricePerYear")));
		}

		return results;
	}
	
	//list of invoices grouped by invoice code in list containing map of product codes values
	public HashMap<String, ArrayList<HashMap<String, String>>> getInvoiceData() throws SQLException {

		String query = "SELECT * FROM Invoices";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {});
		ResultSet rs = ps.executeQuery();

		HashMap<String, ArrayList<HashMap<String, String>>> invoices =
				new HashMap<String, ArrayList<HashMap<String, String>>>();
		while(rs.next()) {
			if(!invoices.containsKey(rs.getString("InvoiceCode"))) {
				ArrayList<HashMap<String, String>> products =
						getProductsFromInvoiceCode(rs.getString("InvoiceCode"));
				invoices.put(rs.getString("InvoiceCode"), products);
			}
		}

		return invoices;
	}

	public ArrayList<HashMap<String, String>> getProductsFromInvoiceCode(String code) throws SQLException {

		String query = "SELECT * FROM Invoices WHERE InvoiceCode = ?";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {code});
		ResultSet rs = ps.executeQuery();

		ArrayList<HashMap<String, String>> products = new ArrayList<HashMap<String, String>>();
		while(rs.next()) {
			HashMap<String, String> product = new HashMap<String, String>();
			product.put("ProductID", rs.getString("ProductID"));
			product.put("InvoiceCode", rs.getString("InvoiceCode"));
			product.put("CustomerID", rs.getString("CustomerID"));
			product.put("PersonID", rs.getString("PersonID"));
			product.put("ProductID", rs.getString("ProductID"));
			product.put("NumberOfUnits", rs.getString("NumberOfUnits"));
			product.put("NumberOfHours", rs.getString("NumberOfHours"));
			product.put("BeginDate", String.valueOf(rs.getDate("BeginDate")));
			product.put("EndDate", String.valueOf(rs.getDate("EndDate")));
			products.add(product);
		}

		return products;
	}

	public HashMap<String, String> getPersonFromID(int personID) throws SQLException {

		String query = "SELECT * FROM Persons WHERE PersonID = ?";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {personID});
		ResultSet rs = ps.executeQuery();

		HashMap<String, String> person = new HashMap<String, String>();
		while(rs.next()) {
			person.put("PersonID", String.valueOf(rs.getInt("PersonID")));
			person.put("PersonCode", rs.getString("PersonCode"));
			person.put("PersAddressID", String.valueOf(rs.getInt("PersAddressID")));
			person.put("PersonLastName", rs.getString("PersonLastName"));
			person.put("PersonFirstName", rs.getString("PersonFirstName"));
		}

		return person;
	}

	public HashMap<String, String> getPersonAddressFromID(int persAddressID) throws SQLException {

		String query = "SELECT * FROM PersonAddress WHERE PersAddressID = ?";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {persAddressID});
		ResultSet rs = ps.executeQuery();

		HashMap<String, String> personAddress = new HashMap<String, String>();
		while(rs.next()) {
			personAddress.put("PersAddressID", String.valueOf(rs.getInt("PersAddressID")));
			personAddress.put("Street", rs.getString("Street"));
			personAddress.put("City", rs.getString("City"));
			personAddress.put("State", rs.getString("CityState"));
			personAddress.put("ZipCode", rs.getString("ZipCode"));
			personAddress.put("Country", rs.getString("Country"));
		}

		return personAddress;
	}

	public HashMap<String, String> getCustomerAddressFromID(int custAddressID) throws SQLException {

		String query = "SELECT * FROM CustomerAddress WHERE CustAddressID = ?";
		
		PreparedStatement ps = dam.prepareStatement(query, new Object[] {custAddressID});
		ResultSet rs = ps.executeQuery();
		
		HashMap<String, String> customerAddress = new HashMap<String, String>();
		while(rs.next()) {
			customerAddress.put("CustAddressID", String.valueOf(rs.getInt("CustAddressID")));
			customerAddress.put("Street", rs.getString("Street"));
			customerAddress.put("City", rs.getString("City"));
			customerAddress.put("State", rs.getString("CityState"));
			customerAddress.put("ZipCode", rs.getString("ZipCode"));
			customerAddress.put("Country", rs.getString("Country"));
		}

		return customerAddress;
	}

	public ArrayList<String> getEmailForPersonID(int personID) throws SQLException {

		String query = "SELECT * FROM Email WHERE PersonID = ?";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {personID});
		ResultSet rs = ps.executeQuery();

		ArrayList<String> emails = new ArrayList<String>();
		while(rs.next()) {
			emails.add(rs.getString("EmailAddress"));
		}

		return emails;
	}

	public ArrayList<HashMap<String, String>> getCustomerData() throws SQLException {

		String query = "SELECT * FROM Customers";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {});
		ResultSet rs = ps.executeQuery();

		ArrayList<HashMap<String, String>> customers = new ArrayList<HashMap<String, String>>();
		while(rs.next()) {
			HashMap<String, String> customer = new HashMap<String, String>();
			customer.put("CustomerCode", rs.getString("CustomerCode"));
			customer.put("CustAddressID", rs.getString("CustAddressID"));
			customer.put("CustomerType", rs.getString("CustomerType"));
			customer.put("PersonCode", rs.getString("PersonCode"));
			customer.put("PersonID", rs.getString("PersonID"));
			customer.put("CustomerName", rs.getString("CustomerName"));
			customers.add(customer);
		}

		return customers;
	}

	public HashMap<String, String> getCustomerDataFromID(int id) throws SQLException {

		String query = "SELECT * FROM Customers WHERE CustomerID = ?";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {id});
		ResultSet rs = ps.executeQuery();

		HashMap<String, String> customer = new HashMap<String, String>();
		while(rs.next()) {
			customer.put("CustomerCode", rs.getString("CustomerCode"));
			customer.put("CustAddressID", rs.getString("CustAddressID"));
			customer.put("CustomerType", rs.getString("CustomerType"));
			customer.put("PersonCode", rs.getString("PersonCode"));
			customer.put("PersonID", rs.getString("PersonID"));
			customer.put("CustomerName", rs.getString("CustomerName"));
		}

		return customer;
	}

	public ArrayList<HashMap<String, String>> getPersonData() throws SQLException {

		String query = "SELECT * FROM Persons";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {});
		ResultSet rs = ps.executeQuery();

		ArrayList<HashMap<String, String>> persons = new ArrayList<HashMap<String, String>>();
		while(rs.next()) {
			HashMap<String, String> person = new HashMap<String, String>();
			person.put("PersonID", String.valueOf(rs.getInt("PersonID")));
			person.put("PersonCode", rs.getString("PersonCode"));
			person.put("PersAddressID", String.valueOf(rs.getInt("PersAddressID")));
			person.put("PersonLastName", rs.getString("PersonLastName"));
			person.put("PersonFirstName", rs.getString("PersonFirstName"));
			persons.add(person);
		}

		return persons;
	}
}
