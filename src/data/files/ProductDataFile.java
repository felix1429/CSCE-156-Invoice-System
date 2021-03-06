package data.files;

import data.controllers.DataFieldController;
import data.controllers.JSONController;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class ProductDataFile extends DataFile {

    private String tempProductCode;
    private DataFieldController dfc = new DataFieldController();
    private ArrayList<Object> person = dfc.getPersonList();
    private Object theArray[];
    private Object keyArray[] = {
            "code",
            "productType",
    };
    private Object equipmentArray [] = {
            "name",
            "pricePerUnit"
    };
    private Object licenseArray [] = {
            "name",
            "serviceFee",
            "annualLicenseFee"
    };
    private Object consultationArray [] = {
            "name",
            person,
            "hourlyFee"
    };

    public ProductDataFile(String filePath) throws IOException {
        super(filePath);
        this.JSONName = "products";
        this.finalJSON = this.convertToJSON(fileArray);
        this.finalJSONString = this.finalJSON.toString(2);
        this.outerJSONObject = jHandler.createJSONShell(this.JSONName, this.finalJSONString);
    }

    private JSONArray convertToJSON(ArrayList<String[]> fileArray) {
        for (int counter = 1; counter <= this.numberOfRecords; counter++) {
            tokenArray = fileArray.get(counter);
            JSONObject tempJObject = new JSONObject();
            tempProductCode = tokenArray[0];
            tempJObject.put(keyArray[0].toString(), tempProductCode);
            String type = tokenArray[1];
            if(type.equals("E")) {
                theArray = equipmentArray;
            }else if(type.equals("L")) {
                theArray = licenseArray;
            }else if(type.equals("C")) {
                theArray = consultationArray;
            }
            for(int count = 2; count < tokenArray.length; count ++) {
                Object ob = theArray[count - 2];
                if(ob != person) {
                    tempJObject.put(ob.toString(), tokenArray[count]);
                }else {
                    tempJObject.put("consultant", DataFieldController.getPersonDataFromCode(tokenArray[count]));
                }
            }
            DataFieldController.addToProductCodeMap(tempProductCode, tempJObject);
            JSONArrayList.put(tempJObject);
        }
        return JSONArrayList;
    }
}
