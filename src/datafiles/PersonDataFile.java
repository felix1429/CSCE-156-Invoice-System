package datafiles;

import jsonhandler.JSONHandler;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class PersonDataFile extends DataFile {

    protected String tempValue;
    protected JSONObject outerJSONObject;
    protected ArrayList<JSONObject> JSONObjectList = new ArrayList<JSONObject>();
    protected JSONHandler jHandler = new JSONHandler();

    public PersonDataFile (String filePath) throws IOException {
        super(filePath);
        this.JSONName = "persons";
        this.outerJSONObject = jHandler.createJSONShell(this.JSONName);
        this.finalJSONString = this.convertToJSON(fileArray);
    }

    public String convertToJSON(ArrayList<String[]> fileArray) {
        //loop over lines of file
        for(int counter = 1;counter < fileArray.size();counter++) {
            tokenArray = fileArray.get(counter);
            //creates new object for each entry
            JSONObject tempJObject = new JSONObject();
            //loop over objects in person
            for (int count = 0;count < person.length;count++) {
                Object ob = person[count];
                tempValue = tokenArray[count];
                if (!(ob instanceof ArrayList)) { //TODO: better checking for email/address/name
                    //TODO: I know when you use append the value is in an array, but org.json has a valueToString method
                    tempJObject.append(ob.toString(), tempValue);
                } else {
                    if(ob == emailAddresses) {
                        tempJObject.append(ob.toString(), tempValue);
                        System.out.println(tempJObject.toString());
                    }else if(ob == address) {
                        tempJObject.append(ob.toString(), tempValue);
                    } else if(ob == name) {
                        tempJObject.append(ob.toString(), tempValue);


                    }else {
                        System.out.println("This should not happen");
                    }
                }
            }
            JSONObjectList.add(tempJObject);
        }
        return "";
    }
}
