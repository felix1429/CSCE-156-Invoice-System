package data.files;

import data.controllers.DataFieldController;
import data.controllers.JSONController;
import org.json.*;

import java.io.IOException;
import java.util.ArrayList;

public class CustomerDataFile extends DataFile {

    protected String tempValue;
    protected JSONArray finalJSON;
    protected JSONArray JSONArrayList = new JSONArray();
    private DataFieldController dfc = new DataFieldController();
    private ArrayList<String> address = dfc.getAddressList();
    protected Object keyArray[] = {
            "customerCode",
            "type",
            "primaryContact",
            "name",
            address
    };

    public CustomerDataFile(String filePath) throws IOException {
        super(filePath);
        this.JSONName = "customers";
        this.finalJSON = this.convertToJSON(fileArray);
        this.finalJSONString = this.finalJSON.toString(2);
        this.outerJSONObject = jHandler.createJSONShell(this.JSONName, this.finalJSONString);
    }

    private JSONArray convertToJSON(ArrayList<String[]> fileArray) {
        for(int counter = 1;counter <= this.numberOfRecords;counter++) {
            tokenArray = fileArray.get(counter);
            JSONObject tempJObject = new JSONObject();
            for (int count = 0; count < tokenArray.length; count++) {
                Object ob = keyArray[count];
                tempValue = tokenArray[count];
                JSONObject aTempJSONObject = new JSONObject();
                if (!(ob instanceof ArrayList)) {
                    if (ob.toString().equals("primaryContact")) {
//                        aTempJSONObject.put(ob.toString(), JSONController.getPersonDataFromCode(tempValue));
                        tempJObject.put(ob.toString(), JSONController.getPersonDataFromCode(tempValue));
                    } else {
                        tempJObject.put(ob.toString(), tempValue);
                    }
                } else {
                    tempJObject.put("address", dfc.parseAddress(tempValue, aTempJSONObject));
                }
            }
            JSONArrayList.put(tempJObject);
        }
        return JSONArrayList;
    }

    public String getOuterJSONObject() {
        return this.outerJSONObject;
    }
}
