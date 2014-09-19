package datafiles;

import jsonhandler.JSONHandler;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class PersonDataFile extends DataFile {

    protected String tokenArray[];
    protected JSONObject outerJSONObject;
    protected ArrayList<JSONObject> JSONObjectList = new ArrayList<JSONObject>();
    protected JSONHandler jHandler = new JSONHandler();

    public PersonDataFile (String filePath) throws IOException {
        super(filePath);
        this.JSONName = "persons";
        this.outerJSONObject = jHandler.createJSONShell(this.JSONName);
//        this.JSONObjectList = this.outerJSONObject.get(this.JSONName);
        this.finalJSONString = this.convertToJSON(fileArray);
    }

    public String convertToJSON(ArrayList<String[]> fileArray) {
        //loop over lines of file
        for(int counter = 1;counter < fileArray.size();counter++) {
            //creates new object for each entry
            JSONObject tempJObject = new JSONObject();
            tokenArray = fileArray.get(counter);
            //loop over objects in person
            for (int count = 0;count < person.length;count++) {
                Object ob = person[count];
                if (!(ob instanceof ArrayList)) { //TODO: better checking for email/address
                    tempJObject.append(ob.toString(), tokenArray[count].toString());
                } else {
                    if(ob == emailAddresses) {

                    }else if(ob == address) {

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
