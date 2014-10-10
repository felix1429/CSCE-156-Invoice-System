package data.controllers;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class InvoiceController {

    private static double totalFees;
    private static double totalTotal;
    private static double taxesOwed;

    public static void setTotalFees(double in) {
        totalFees = in;
    }

    public static double getTotalFees() {
        return totalFees;
    }

    public static void setTotalTotal(double in) {
        totalTotal = in;
    }

    public static double getTotalTotal() {
        return totalTotal;
    }

    public static double getTaxesOwed() {
        return taxesOwed;
    }

    public static void setTaxesOwed(double in) {
        taxesOwed = in;
    }

    public double getOverallTotal(JSONObject j) {
        double total = 0;
        total += InvoiceController.getTotalFees() + InvoiceController.getTotalTotal() + getComplianceFee(j) + getTaxesOwed();
        return total;
    }

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

    public double roundToTwo(double in) {
        return Math.round(in * 100.0) / 100.0;
    }

    public String putTwoZeros(double in) {
        String st = String.valueOf(in);
        if(st.substring((st.length() - 2)).contains(".")) {
            st += "0";
        }
        return st;
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

    public double getComplianceFee(JSONObject in) {
        double complianceFee = 0;
        if(getNestedData(in, "customer", "type").equals("G")) {
            complianceFee += 125;
        }
        return complianceFee;
    }

    public String getMoneyInfo(JSONObject in, JSONObject customer) throws ParseException {
        boolean taxed = getNestedData(customer, "customer", "type").equals("C");
        double fee = 0;
        double total = 0;
        String output = "";
        if (in.has("billableHours")) {
            fee = 150;
            double amount = (in.getDouble("billableHours") * in.getDouble("hourlyFee")) + fee;
            if(taxed) {
                setTaxesOwed(getTaxesOwed() + ((amount - fee) * .0425));
            }
            total = amount;
        } else if (in.has("annualLicenseFee")) {
            fee = in.getDouble("serviceFee");
            double amount = (Double.parseDouble(getDaysBetweenDates((String) in.get("beginDate"), (String) in.get("endDate"))) / 365) * in.getDouble("annualLicenseFee") + fee;
            if(taxed) {
                setTaxesOwed(getTaxesOwed() + ((amount - fee) * .0425));
            }
            total = amount;
        } else if (in.has("pricePerUnit")) {
            double price = in.getDouble("numberOfUnits") * in.getDouble("pricePerUnit");
            if(taxed) {
                setTaxesOwed(getTaxesOwed() + (price * .07));
            }
            total = price;
        }
        total = roundToTwo(total);
        String totalStr = String.valueOf(total);
        if(totalStr.substring((totalStr.length() - 2)).contains(".")) {
            totalStr += "0";
        }
        setTotalTotal(getTotalTotal() + total);
        fee = roundToTwo(fee);
        String feeStr = String.valueOf(fee);
        if(feeStr.substring((feeStr.length() - 2)).contains(".")) {
            feeStr += "0";
        }
        setTotalFees(getTotalFees() + fee);
        output += "$" + generateRepeatString(" ", 10 - feeStr.length())
                + feeStr + "  $" + generateRepeatString(" ", 10 - totalStr.length())
                + totalStr;
        return output;
    }

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

    public String generateHeader() {
        String output = "";
        output += addLine("Individual Invoice Detail Reports");
        output += addLine(generateRepeatString("=", 50));
        return output;
    }

    public ArrayList<JSONObject> getProductList(JSONObject in) {
        ArrayList<JSONObject> productList = new ArrayList<JSONObject>();
        Iterator<?> keys = in.keys();
        while(keys.hasNext()) {
            String key = (String)keys.next();
            if(key.contains("license") || key.contains("consultation") || key.contains("equipment")) {
                productList.add((JSONObject)in.get(key));
            }
        }
        return productList;
    }

    public String generateProductLines(JSONObject in) throws ParseException {
        String output = "";
        String line = "";
        ArrayList<JSONObject> productList = getProductList(in);
        for(JSONObject j : productList) {
            line += j.get("code") + generateRepeatString(" ", 6);
            line += getProductInfo(j);
            line += getMoneyInfo(j, in);
            output += addLine(line);
            line = "";
        }
        return output;
    }
}
