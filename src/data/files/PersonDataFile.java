package data.files;

import data.controllers.DataFieldController;
import data.controllers.JSONController;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class PersonDataFile extends DataFile {

    protected String tempValue;
    protected JSONObject outerJSONObject;
    protected JSONArray JSONArrayList = new JSONArray();
    protected JSONController jHandler = new JSONController();
    private DataFieldController dfc = new DataFieldController();
    private ArrayList<Object> person = dfc.getPersonList();
    private ArrayList<String> name = dfc.getNameList();
    private ArrayList<String> address = dfc.getAddressList();
    private ArrayList<String> emailAddresses = dfc.getEmailList();

    public PersonDataFile (String filePath) throws IOException {
        super(filePath);
        this.JSONName = "persons";
        this.outerJSONObject = jHandler.createJSONShell(this.JSONName);
        this.finalJSONString = this.convertToJSON(fileArray);
        System.out.println(this.finalJSONString);
    }

    public String convertToJSON(ArrayList<String[]> fileArray) {
        //loop over lines of file
        for(int counter = 1;counter < fileArray.size();counter++) {
            tokenArray = fileArray.get(counter);
            //creates new object for each entry
            JSONObject tempJObject = new JSONObject();
            //loop over objects in person
            for (int count = 0;count < tokenArray.length;count++) {
                Object ob = person.get(count);
                tempValue = tokenArray[count];
                if (!(ob instanceof ArrayList)) {
                    tempJObject.put(ob.toString(), tempValue);
                } else {
                    JSONObject aTempJSONObject = new JSONObject();
                    if(ob == emailAddresses) {

                        tempJObject.append("emailAddresses", tempValue);
                    }else if(ob == address) {
                        tempJObject.append("address", tempValue);
                    } else if(ob == name) {
                        tempJObject.append("name", tempValue);
                    }else {
                        System.out.println("This should not happen");
                    }
                }
            }
            JSONArrayList.put(tempJObject);
        }
        return JSONArrayList.toString(4);
    }
}
