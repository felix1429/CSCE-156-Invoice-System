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
    private String productArray[];
    private String productFields[];
    private String productType;
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
            "numberOfUnits",
    };

    private Object consultationArray[] = {
            "billableHours",
    };

    private Object licenseArray[] = {
            "beginDate",
            "endDate",
    };

    public InvoiceDataFile(String filePath) throws IOException {
        super(filePath);
        this.finalJSON = this.convertToJSON((fileArray));
        this.finalJSONString = this.finalJSON.toString(2);
        System.out.println(finalJSONString);
    }

    private JSONArray convertToJSON(ArrayList<String[]> fileArray) {
        for(int counter = 1; counter < this.numberOfRecords; counter++) {
            tokenArray = fileArray.get(counter);
            JSONObject tempJObject = new JSONObject();
            for(int count = 0; count < 4; count++) {
                Object ob = keyArray[count];
                tempValue = tokenArray[count];
                switch (count) {
                    case 0:
                        tempJObject.put(ob.toString(), tempValue);
                        break;
                    case 1:
                        tempJObject.put("customer", DataFieldController.getCustomerDataFromCode(tempValue));
                        break;
                    case 2:
                        tempJObject.put("salesperson", DataFieldController.getPersonDataFromCode(tempValue));
                        break;
                    case 3:
                        productArray = ic.splitProductListToTokens(tempValue);
                        for(int foo = 0; foo < productArray.length; foo ++) {
                            tempValue = productArray[foo];
                            tempProduct = DataFieldController.getProductDataFromCode(tempValue);
                            productFields = ic.splitProductDataToTokens(tempValue);
                            productType = ic.getProductType(tempProduct);
                            if (productType.equals("equipment")) {
                                theArray = equipmentArray;
                                tempJObject.put(theArray[0].toString(), productFields[0]);
                            } else if (productType.equals("license")) {
                                theArray = licenseArray;
                                tempJObject.put(theArray[0].toString(), productFields[0]);
                                tempJObject.put(theArray[1].toString(), productFields[1]);
                            } else if (productType.equals("consultation")) {
                                theArray = consultationArray;
                                tempJObject.put(theArray[0].toString(), productFields[0]);
                            }
                            tempJObject.put(productType, tempProduct);
                            products.add(tempJObject);
                        }
                        break;
                    default:
                        System.out.println("something went wrong");
                        break;
                }
            }
            JSONArrayList.put(tempJObject);
        }
        return JSONArrayList;
    }
}
