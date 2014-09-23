package data.files;

import data.controllers.DataFieldController;

import java.io.IOException;
import java.util.ArrayList;

public class CustomerDataFile extends DataFile {

    private DataFieldController dfc = new DataFieldController();
    private ArrayList<String> address = dfc.getAddressList();
    protected int numberOfRecords;
    protected Object keyArray[] = {
            "customerCode",
            "type",
            "primaryContact",
            "name",
            address
    };

    public CustomerDataFile(String filePath) throws IOException {
        super(filePath);
        this.JSONName = "customers";
    }


}
