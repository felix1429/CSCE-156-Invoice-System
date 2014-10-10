package data.files;

import java.io.*;
import java.util.ArrayList;

import data.controllers.JSONController;
import org.json.JSONArray;
import org.json.JSONObject;

//extends file so DataFile object has all functionalities of a file
abstract class DataFile extends File{

    protected String finalJSONString;
    protected JSONArray finalJSON;
    protected JSONController jHandler = new JSONController();
    protected ArrayList<String[]> fileArray = new ArrayList<String[]>();
    protected String JSONName;
    protected JSONArray JSONArrayList = new JSONArray();
    protected String outerJSONObject;
    //holds json tokens from file lines
    protected String tokenArray[];
    protected String filePath;
    protected String tempValue;
    protected int numberOfRecords;

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

    protected String getOuterJSONObject() {
        return this.outerJSONObject;
    }
}
