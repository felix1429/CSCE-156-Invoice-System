package data.files;

import data.controllers.DataFieldController;
import data.controllers.InvoiceController;

import java.io.IOException;
import java.util.ArrayList;

/*
Invoices structured like so
Invoice Number
Salesperson: name
Customer Info:
    name, customer code
    address
Code  Item  Fees  Total
~~~
~~~
Sub-Total
Compliance Fee
Taxes
Total
 */
public class InvoiceDataFile extends DataFile{

    private DataFieldController dfc = new DataFieldController();
    private InvoiceController ic = new InvoiceController();
    private ArrayList<Object> person = dfc.getPersonList();
    private ArrayList<Object> products = new ArrayList<Object>();
    protected Object keyArray[] = {
            "invoiceCode",
            person,
            person,
            products
    };

    private Object equipmentArray[] = {
            "numberOfUnits"
    };

    private Object consultationArray[] = {
            "billableHours"
    };

    private Object liscenceArray[] = {
            "beginDate",
            "endDate"
    };

    public InvoiceDataFile(String filePath) throws IOException {
        super(filePath);

    }

//    private void convertTo
}
