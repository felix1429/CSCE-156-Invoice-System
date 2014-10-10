package data.controllers;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class InvoiceController {

    private String output = "";

    public String getProductType(JSONObject input) {
        if(input.has("pricePerUnit")) {
            return "equipment";
        }else if(input.has("serviceFee")) {
            return "license";
        }else if(input.has("hourlyFee")) {
            return "consultation";
        }else {
            return "Something has gone wrong";
        }
    }

    public String[] splitProductListToTokens(String input) {
        return input.split(",");
    }

    public String[] splitProductDataToTokens(String input) {
        return input.split(":");
    }

    public String addLine(String in) {
        return in + "\n";
    }

    public String getNestedData(JSONObject in, String ... key) {
        if(key.length == 2) {
            return (String)in.getJSONObject(key[0]).get(key[1]);
        }else if(key.length == 3) {
            return (String)in.getJSONObject(key[0]).getJSONObject(key[1]).get(key[2]);
        }else if(key.length == 4) {
            return (String)in.getJSONObject(key[0]).getJSONObject(key[1]).getJSONObject(key[2]).get(key[3]);
        }else {
            return "";
        }
    }
    
    public String getCustomerAddress(JSONObject in) {
        String address = "";
        address += addLine("  " + getNestedData(in, "customer", "address", "street"));
        address += addLine("  " + getNestedData(in, "customer", "address", "city") + " "
                + getNestedData(in, "customer", "address", "state") + " "
                + getNestedData(in, "customer", "address", "zip") + " "
                + getNestedData(in, "customer", "address", "country"));
        return address;
    }

    public String generateRepeatString(String in, int number) {
        return new String(new char[number]).replace("\0", in);
    }

    public String getProductInfo(JSONObject in) throws ParseException {
        String output = "";
        if(in.has("numberOfUnits")) {
            output += in.get("name") + " ("
                    + in.get("numberOfUnits") + " units @ $"
                    + in.get("pricePerUnit") + "/yr)";
            output += generateRepeatString(" ", 70 - output.length());
            return output;
        }else if(in.has("annualLicenseFee")) {
            output += in.get("name") + " ("
                    + getDaysBetweenDates((String)in.get("beginDate"), (String)in.get("endDate"))
                    + " days @ $"+ in.get("annualLicenseFee") + "/yr)";
            output += generateRepeatString(" ", 70 - output.length());
            return output;
        }else if(in.has("billableHours")) {
            output += in.get("name") + " ("
                    + in.get("billableHours") + " hrs @ $"
                    + in.get("hourlyFee") + "/hr)";
            output += generateRepeatString(" ", 70 - output.length());
            return output;
        }else {
            return "something went wrong";
        }
    }

    public String getMoneyInfo

    public String getDaysBetweenDates(String startDate, String endDate) throws ParseException {
        SimpleDateFormat theFormat = new SimpleDateFormat("dd MM yyyy");
        String[] startArray = startDate.split("-");
        String[] endArray = endDate.split("-");
        String start = startArray[2] + " " + startArray[1] + " " + startArray[0];
        String end = endArray[2] + " " + endArray[1] + " " + endArray[0];
        Date beginDate = theFormat.parse(start);
        Date endingDate = theFormat.parse(end);
        long diff = endingDate.getTime() - beginDate.getTime();
        return String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
    }

    public ArrayList<JSONObject> getProductList(JSONObject in) {
        ArrayList<JSONObject> productList = new ArrayList<JSONObject>();
        Iterator<?> keys = in.keys();
        while(keys.hasNext()) {
            String key = (String)keys.next();
            if(key.equals("license") || key.equals("consultation") || key.equals("equipment")) {
                productList.add((JSONObject)in.get(key));
            }
        }
        return productList;
    }

    public String generateProductLIne(JSONObject in, String code) throws ParseException {
        ArrayList<JSONObject> productList = getProductList(in);
        ArrayList<String> productInfoList = new ArrayList<String>();
        for(JSONObject j : productList) {
            String output = getProductInfo(j);
            output +=
            productInfoList.add(getProductInfo(j));
        }
        output = code + generateRepeatString(" ", 6)
                + ;
        return output;
    }
}
