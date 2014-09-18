package datafiles;

import java.io.*;
import java.util.ArrayList;

import jsonhandler.JSONHandler;
import org.json.*;

//extends file so datafiles.DataFile object has all functionalities of a file
abstract class DataFile extends File{

    protected JSONHandler jHandler = new JSONHandler();
    protected ArrayList<String[]> fileArray = new ArrayList<String[]>();
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
        System.out.println(this.filePath);
        fileArray = this.readFileToArray();
        this.numberOfRecords = jHandler.getNumberOfRecords(fileArray.get(0));
        System.out.println(this.numberOfRecords);
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
