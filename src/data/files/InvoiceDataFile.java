package data.files;

import data.controllers.DataFieldController;

import java.io.IOException;
import java.util.ArrayList;

public class InvoiceDataFile extends DataFile{

    private DataFieldController dfc = new DataFieldController();
    private ArrayList<Object> person = dfc.getPersonList();
    private ArrayList<Object>
    protected Object keyArray[] = {
            "invoiceCode",
            person,
            person,

    };

    public InvoiceDataFile(String filePath) throws IOException {
        super(filePath);

    }
}
