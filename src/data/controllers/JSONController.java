package data.controllers;

import org.json.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class JSONController {

    public static HashMap<String, JSONObject> personCodeMap = new HashMap<String, JSONObject>();

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

    public JSONObject getPersonDataFromCode(String code) {
        return personCodeMap.get(code);
    }
}
