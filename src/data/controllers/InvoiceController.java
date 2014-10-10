package data.controllers;

import org.json.JSONObject;

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

    public String[] splitToTokens(String input) {
        return input.split(",");
    }

}
