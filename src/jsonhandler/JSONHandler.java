package jsonhandler;

import org.json.*;

import java.io.IOException;
import java.util.ArrayList;

public class JSONHandler {

    private String tokenArray[];

    //internal method only to be called in dataFile constructor, ensuring that correct value is returned
    public int getNumberOfRecords(String arrList[]) throws IOException {
        return Integer.parseInt(arrList[0]);
    }

    //splits line into json tokens
    public String[] parseToTokens(String line) {
        tokenArray = line.split(";");
        return tokenArray;
    }

    public JSONObject createJSONShell(String JSONName) {
        JSONObject outerJSONObject = new JSONObject();
        ArrayList<JSONObject> objectList = new ArrayList<JSONObject>();
        outerJSONObject.put(JSONName, objectList);
        return outerJSONObject;
    }
}
