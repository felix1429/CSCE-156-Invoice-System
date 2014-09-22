package data.files;

import data.controllers.DataFieldController;

import java.io.IOException;
import java.util.ArrayList;

public class ProductDataFile extends DataFile {

    private DataFieldController dfc;
    private ArrayList<String> name = dfc.getNameList();
    private ArrayList<Object> person = dfc.getPersonList();

    protected Object keyArray[] = {
            "code",
            "productType"
    };
    private Object equipmentArray [] = {
            name,
            "pricePerUnit"
    };
    private Object liscenceArray [] = {
            name,
            "serviceFee",
            "annualLiscenceFee"
    };
    private Object consultationArray [] = {
            name,
            person,
            "hourlyFee"
    };

    public ProductDataFile(String filePath) throws IOException {
        super(filePath);
        this.JSONName = "products";
    }

    private String getPersonFromPersonCode(String personCode) {
        //TODO: flesh out method
        return "";
    }
}
