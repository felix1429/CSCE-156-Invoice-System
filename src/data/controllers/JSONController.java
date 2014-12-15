package data.controllers;

import com.cinco.InvoiceData;
import org.json.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class JSONController {

    InvoiceData i = new InvoiceData();

    //internal method only to be called in dataFile constructor, ensuring that correct value is returned
    public int getNumberOfRecords(String arrList[]) throws IOException {
        return Integer.parseInt(arrList[0]);
    }

    private static String removeTrailingZeros(double d) {
        return String.valueOf(d).replaceAll("[0]*$", "").replaceAll(".$", "");
    }

    private static String removeTrailingZeros(float d) {
        return String.valueOf(d).replaceAll("[0]*$", "").replaceAll(".$", "");
    }

    //splits line into json tokens
    public String[] parseToTokens(String line) {
        return line.split(";");
    }

    public String createJSONShell(String JSONName, String finalString) {
        return "{\n\"" + JSONName + "\": " + finalString + "}";
    }

    public JSONObject getAddressJSONFromData(HashMap<String, String> personAddress) {
        JSONObject address = new JSONObject();
        address.put("zip", personAddress.get("ZipCode"));
        address.put("country", personAddress.get("Country"));
        address.put("city", personAddress.get("City"));
        address.put("street", personAddress.get("Street"));
        address.put("state", personAddress.get("State"));
        return address;
    }

    public JSONObject getPersonJSONFromData(HashMap<String, String> data) throws SQLException {
        JSONObject tempJObject = new JSONObject();
        tempJObject.put("lastName", data.get("PersonLastName"));
        tempJObject.put("firstName", data.get("PersonFirstName"));
        tempJObject.put("personCode", data.get("PersonCode"));
        ArrayList<String> emails = i.getEmailForPersonID(Integer.parseInt(data.get("PersonID")));
        if(!emails.contains(null)) {
            tempJObject.put("emails", emails);
        }
        tempJObject.put("address",
                getAddressJSONFromData(i.getPersonAddressFromID(Integer.parseInt(data.get("PersAddressID")))));
        return tempJObject;
    }

    public JSONArray productsDataToJSON() throws SQLException {
        JSONArray JSONArrayList = new JSONArray();
        ArrayList<HashMap<String, String>> results = i.getProductData();
        for(HashMap<String, String> product : results) {
            JSONArrayList.put(getProductJSONFromData(product));
        }
        return JSONArrayList;
    }

    public JSONObject getProductJSONFromData(HashMap<String, String> product) throws SQLException {
        JSONObject tempJObject = new JSONObject();
        tempJObject.put("code", product.get("ProductCode"));
        tempJObject.put("name", product.get("ProductName"));
        String type = product.get("ProductType");
        if(type.equals("E")) {
            tempJObject.put("pricePerUnit", product.get("PricePerUnit"));
        } else if(type.equals("C")) {
            HashMap<String, String> customer =
                    i.getPersonFromID(Integer.parseInt(product.get("PersonID")));
            tempJObject.put("consultant", getPersonJSONFromData(customer));
            tempJObject.put("name", product.get("ProductName"));
            tempJObject.put("hourlyFee", product.get("PricePerHour"));
        } else if(type.equals("L")) {
            tempJObject.put("serviceFee", product.get("ProductFee"));
            tempJObject.put("annualLicenseFee", product.get("PricePerYear"));
        }
        return tempJObject;
    }

    public JSONArray customersDataToJSON() throws SQLException {
        JSONArray JSONArrayList = new JSONArray();
        ArrayList<HashMap<String, String>> data = i.getCustomerData();
        for(HashMap<String, String> customer : data) {
            JSONArrayList.put(getCustomerJSONFromData(customer));
        }
        return JSONArrayList;
    }

    public JSONObject getCustomerJSONFromData(HashMap<String, String> customer) throws SQLException {
        JSONObject tempJObject = new JSONObject();
        tempJObject.put("customerCode", customer.get("CustomerCode"));
        tempJObject.put("customerType", customer.get("CustomerType"));
        HashMap<String, String> primaryContact =
                i.getPersonFromID(Integer.parseInt(customer.get("PersonID")));
        tempJObject.put("primaryContact", getPersonJSONFromData(primaryContact));
        tempJObject.put("name", customer.get("Name"));
        HashMap<String, String> address =
                i.getCustomerAddressFromID(Integer.parseInt(customer.get("CustAddressID")));
        tempJObject.put("address", getAddressJSONFromData(address));
        return tempJObject;
    }

    public JSONArray personsDataToJSON() throws SQLException {
        JSONArray JSONArrayList = new JSONArray();
        ArrayList<HashMap<String, String>> data = i.getPersonData();
        for(HashMap<String, String> person : data) {
            JSONArrayList.put(getPersonJSONFromData(person));
        }
        return JSONArrayList;
    }
}
