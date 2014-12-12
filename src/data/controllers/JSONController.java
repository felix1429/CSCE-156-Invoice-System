package data.controllers;

import com.cinco.InvoiceData;
import org.json.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class JSONController {

    //internal method only to be called in dataFile constructor, ensuring that correct value is returned
    public int getNumberOfRecords(String arrList[]) throws IOException {
        return Integer.parseInt(arrList[0]);
    }

    //splits line into json tokens
    public String[] parseToTokens(String line) {
        return line.split(";");
    }

    public String createJSONShell(String JSONName, String finalString) {
        return "{\n\"" + JSONName + "\": " + finalString + "}";
    }

    public JSONObject getPersonJSONFromData(HashMap<String, String> data) {

    }

    public JSONArray productsDataToJSON() throws SQLException {
        InvoiceData i = new InvoiceData();
        JSONArray JSONArrayList = new JSONArray();
        ArrayList<HashMap<String, String>> results = i.getProductData();
        for(HashMap<String, String> product : results) {
            JSONObject tempJObject = new JSONObject();
            tempJObject.put("code", product.get("ProductCode"));
            tempJObject.put("name", product.get("ProductName"));
            String type = product.get("ProductType");
            if(type.equals("E")) {
                tempJObject.put("pricePerUnit", product.get("PricePerUnit"));
            } else if(type.equals("C")) {
                tempJObject.put("consultant",
                        getPersonJSONFromData(i.getCustomerFromID(Integer.parseInt(product.get("PersonID")))));
            } else if(type.equals("L")) {
                tempJObject.put("serviceFee", product.get("ProductFee"));
                tempJObject.put("annualLicenseFee", product.get("PricePerYear"));
            }
            JSONArrayList.put(tempJObject);
        }
        return JSONArrayList;
    }
}
