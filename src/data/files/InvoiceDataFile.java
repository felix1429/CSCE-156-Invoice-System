package data.files;

import data.controllers.DataFieldController;
import data.controllers.InvoiceController;
import org.json.JSONArray;
import org.json.JSONObject;

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
    private JSONObject tempProduct;
    private Object theArray[];
    private String productType;
    private String productArray[];
    protected String tempValue;
    protected JSONArray finalJSON;
    protected JSONArray JSONArrayList = new JSONArray();
    protected Object keyArray[] = {
            "Invoice",
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

    private void convertToOutputString(ArrayList<String[]> fileArray) {
        for(int counter = 1; counter < this.numberOfRecords; counter++) {
            tokenArray = fileArray.get(counter);
            JSONObject tempJObject = new JSONObject();
            for(int count = 0; count < 4; count++) {
                Object ob = keyArray[count];
                tempValue = tokenArray[count];
                if(count == 0) {
                    tempJObject.put(ob.toString(), tempValue);
                }else if(count == 1) {
                    tempJObject.put("customer", DataFieldController.getCustomerDataFromCode(tempValue));
                }else if(count == 2) {
                    tempJObject.put("salesperson", DataFieldController.getPersonDataFromCode(tempValue));
                }else if(count == 3) {
                    productArray = ic.splitToTokens(tempValue);
                    for(int foo = 0; foo < productArray.length; foo ++) {
                        tempValue = productArray[foo];
                        tempProduct = DataFieldController.getProductDataFromCode(tempValue);
                        productType = ic.getProductType(tempProduct);
                        if (productType.equals("equipment")) {
                            theArray = equipmentArray;
                        } else if (productType.equals("liscnence")) {
                            theArray = liscenceArray;
                        } else if (productType.equals("consultation")) {
                            theArray = consultationArray;
                        }
                        products.add(tempJObject);
                    }
                }
            }
        }
    }
}
