package data.controllers;

import org.json.JSONObject;

import java.util.ArrayList;

public class DataFieldController {

    private ArrayList<String> emailAddresses = new ArrayList<String>();
    private ArrayList<String> address = new ArrayList<String>() {
        {
            add("street");
            add("city");
            add("state");
            add("zip");
            add("country");
        }};
    private ArrayList<String> name = new ArrayList<String>() {
        {
            add("firstName");
            add("lastName");
        }};
    private ArrayList<Object> person = new ArrayList<Object>(){
        {
            add("personCode");
            add(name);
            add(address);
            add(emailAddresses);
        }};

    private String[] splitToTokens(String list) {
        return list.split(",");
    }

    public ArrayList<String> getAddressList() {
        return address;
    }

    public ArrayList<String> getNameList() {
        return name;
    }

    public ArrayList<Object> getPersonList() {
        return person;
    }

    public ArrayList<String> getEmailList() {
        return emailAddresses;
    }

    public ArrayList<String> parseEmail(String input) {
        String values[] = splitToTokens(input);
        ArrayList<String> tempArrayList = new ArrayList<String>();
        for(String st : values) {
            tempArrayList.add(st);
        }
        return tempArrayList;
    }

    public JSONObject parseAddress(String input, JSONObject temp) {
        String values[] = splitToTokens(input);
        String output;
        for(int i = 0; i < values.length; i++) {
            output = values[i];
            if(!address.get(i).equals("street")) {
                output = output.replaceAll(" ", "");
            }
            temp.put(address.get(i), output);
        }
        return temp;
    }

    public String[][] parseName(String input) {
        String values[] = splitToTokens(input);
        String[][] nameArray = new String[2][2];
        nameArray[0] = new String[] {"lastName", values[0]};
        nameArray[1] = new String[] {"firstName", values[1].replaceAll(" ","")};
        return nameArray;
    }

    //TODO: add persons to (personCode, person) map so they can be retrieved by other classes
}
