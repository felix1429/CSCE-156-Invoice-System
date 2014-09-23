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

//    public JSONObject parseEmail() {
//
//    }

}
