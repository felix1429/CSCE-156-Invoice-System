package datafiles;

import java.io.*;
import java.util.ArrayList;

import jsonhandler.JSONHandler;
import org.json.*;

//extends file so datafiles.DataFile object has all functionalities of a file
abstract class DataFile extends File{

    protected JSONHandler jHandler = new JSONHandler();
    protected BufferedReader br;
    //name of JSON file, ie persons, products, customers
    protected String JSONName;
    //holds tokens
    protected String tokenArray[];
    protected String filePath;
    protected int numberOfRecords;
    //arrayList for unknown number of email addresses
    protected ArrayList<String> emailAddresses = new ArrayList<String>();
    //array of person data fields
    protected Object person[] = {
            "personCode",
            "firstName",
            "lastName",
            emailAddresses
    };
    //array of address data fields
    protected String address[] = {
            "street",
            "city",
            "state",
            "zip",
            "country"
    };
    //array that contains indices of data fields
    protected Object keyArray[];


    //constructor
    public DataFile(String filePath) throws IOException {
        super(filePath);
        System.out.println(filePath);
        this.filePath = filePath;
        try {
            System.out.println(this.filePath);
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            //gets first line of file which is the number of records in the file
            this.numberOfRecords = _getNumberOfRecords(br);
            System.out.println(this.numberOfRecords);
            System.out.println("I am being called");
        } catch (FileNotFoundException e) {
            System.out.println("I fucked up");
            //TODO: add exception handling
        }
    }

    //internal method only to be called in dataFile constructor, ensuring that correct value is returned
    protected int _getNumberOfRecords(BufferedReader br) throws IOException {
        return Integer.parseInt(br.readLine());
    }

    protected String[] parseToTokens(String line) {
        tokenArray = line.split(";");
        return tokenArray;
    }

    protected JSONObject createJSONShell(String JSONName) {
        JSONObject outerJSONObject = new JSONObject();
        ArrayList<JSONObject> objectList = new ArrayList<JSONObject>();
        outerJSONObject.put(JSONName, objectList);
        return outerJSONObject;
    }
}
