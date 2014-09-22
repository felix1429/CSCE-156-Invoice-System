package datafiles;

import java.io.*;
import java.util.ArrayList;

import jsonhandler.JSONHandler;
import org.json.JSONObject;

//extends file so DataFile object has all functionalities of a file
abstract class DataFile extends File{

    protected String finalJSONString;
    protected JSONHandler jHandler = new JSONHandler();
    protected ArrayList<String[]> fileArray = new ArrayList<String[]>();
    protected String JSONName;
    protected JSONObject outerJSONObject;
    //holds json tokens from file lines
    protected String tokenArray[];
    protected String filePath;
    protected int numberOfRecords;
    //arrayList for unknown number of email addresses
    protected ArrayList<String> emailAddresses = new ArrayList<String>();
    //array of address data fields
    protected ArrayList<String> address = new ArrayList<String>() {
        {
            add("street");
            add("city");
            add("state");
            add("zip");
            add("country");
        }};
    protected ArrayList<String> name = new ArrayList<String>() {
        {
            add("firstName");
            add("lastName");
        }};
    //array of person data fields
    protected ArrayList<Object> person = new ArrayList<Object>(){
        {
            add("personCode");
            add(name);
            add(address);
            add(emailAddresses);
        }};


    //constructor
    public DataFile(String filePath) throws IOException {
        super(filePath);
        this.filePath = filePath;
        this.fileArray = this.readFileToArray();
        this.numberOfRecords = jHandler.getNumberOfRecords(fileArray.get(0));
    }

    //splits file into array of arrayLists
    protected ArrayList<String[]> readFileToArray() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line = null;
        while((line = br.readLine()) != null) {
            tokenArray = jHandler.parseToTokens(line);
            fileArray.add(tokenArray);
        }
        return fileArray;
    }
}
