package data.databaseModels;

import java.sql.*;

/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 *
 */
public class InvoiceData {

	private static DatabaseAccessModel dam = new DatabaseAccessModel(
			"jdbc:mysql://cse.unl.edu/thennig", "thennig", "Z6nzb9");

	/**
	 * Method that removes every person record from the database
	 */
	public static void removeAllPersons() throws SQLException {

		String query = "DELETE FROM Persons";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {});
		ps.executeUpdate();

		dam.closeConnection(ps);
	}

	/**
	 * Removes the person record from the database corresponding to the
	 * provided personCode
	 * @param personCode
	 */
	public static void removePerson(String personCode) throws SQLException {

		String query = "DELETE FROM Persons where PersonCode = ?;";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {personCode});
		ps.executeUpdate();

		dam.closeConnection(ps);
	}
	
	/**
	 * Method to add a person record to the database with the provided data.
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
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
	
	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) throws SQLException {

		String query = "INSERT INTO Email (PersonID, EmailAddress) VALUES "
				+ "((SELECT PersonID FROM Persons WHERE PersonCode = ?),?);";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {personCode, email});
		ps.executeUpdate();

		dam.closeConnection(ps);
	}
	
	/**
	 * Method that removes every customer record from the database
	 */
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

	/**
	 * Removes all product records from the database
	 */
	public static void removeAllProducts() throws SQLException {

		String query = "DELETE FROM Products";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {});
		ps.executeUpdate();

		dam.closeConnection(ps);
	}

	/**
	 * Removes a particular product record from the database corresponding to the
	 * provided <code>productCode</code>
	 * @param productCode
	 */
	public static void removeProduct(String productCode) throws SQLException {

		String query = "DELETE FROM Products where ProductCode = ?;";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {productCode});
		ps.executeUpdate();

		dam.closeConnection(ps);
	}

	/**
	 * Adds an equipment record to the database with the
	 * provided data.  
	 */
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
	
	/**
	 * Adds an license record to the database with the
	 * provided data.  
	 */
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

	/**
	 * Adds an consultation record to the database with the
	 * provided data.  
	 */
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
	
	/**
	 * Removes all invoice records from the database
	 */
	public static void removeAllInvoices() throws SQLException {

		String query = "DELETE FROM Invoices";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {});
		ps.executeUpdate();

		dam.closeConnection(ps);

	}
	
	/**
	 * Removes the invoice record from the database corresponding to the
	 * provided <code>invoiceCode</code>
	 * @param invoiceCode
	 */
	public static void removeInvoice(String invoiceCode) throws SQLException {

		String query = "DELETE FROM Invoices where InvoiceCode = ?;";

		PreparedStatement ps = dam.prepareStatement(query, new Object[] {invoiceCode});
		ps.executeUpdate();

		dam.closeConnection(ps);

	}
	
	/**
	 * Adds an invoice record to the database with the given data.  
	 */
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
	
	/**
	 * Adds a particular equipment (corresponding to <code>productCode</code> to an 
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of units
	 */
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
	
	/**
	 * Adds a particular equipment (corresponding to <code>productCode</code> to an 
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * begin/end dates
	 */
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
//TODO: make int product codes go in as strings
			query = "INSERT INTO Invoices "
					+ "(InvoiceCode,CustomerID,PersonID,ProductID,NumberOfUnits,NumberOfHours,BeginDate,EndDate) "
					+ "VALUES(?,NULL,NULL,(SELECT ProductID FROM Products WHERE ProductCode = ?),"
					+ "0,0,?,?);";

			ps = dam.prepareStatement(query, new Object[] {invoiceCode, productCode, startDate, endDate});
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

	/**
	 * Adds a particular equipment (corresponding to <code>productCode</code> to an 
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of billable hours.
	 */
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
}
