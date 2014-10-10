package data.files;

import data.controllers.InvoiceController;
import org.json.*;

import java.text.ParseException;

public class InvoiceOutputFile {

    private JSONArray input;
    private String finalOutput = "";
    private InvoiceController ic = new InvoiceController();
    private String invoiceSalespersonDivide = ic.generateRepeatString("=", 24);
    private String customerProductDivide = ic.generateRepeatString("-", 43);
    private int rowLength = 103;
    private int moneyLength = 23;
    public InvoiceOutputFile(JSONArray input) throws ParseException {
        this.input = input;
        generateInvoice();
    }

    public String generateInvoice() throws ParseException {
        for(int count = 0; count < input.length(); count++) {
            String output = "";
            JSONObject tempJObject = (JSONObject)input.get(count);
            output += ic.addLine("Invoice " + tempJObject.get("invoice"));
            output += ic.addLine(invoiceSalespersonDivide);
            output += ic.addLine("Salesperson: " + ic.getNestedData(tempJObject, "salesperson", "lastName") + ", "
                    + ic.getNestedData(tempJObject, "salesperson", "firstName"));
            output += ic.addLine("Customer Info:");
            output += ic.addLine("  " + ic.getNestedData(tempJObject, "customer", "name") + " ("
                    + ic.getNestedData(tempJObject, "customer", "customerCode") + ")");
            output += ic.addLine("  " + ic.getNestedData(tempJObject, "customer", "primaryContact", "lastName") + ", "
                    + ic.getNestedData(tempJObject, "customer", "primaryContact", "firstName"));
            output += ic.getCustomerAddress(tempJObject);
            output += ic.addLine(customerProductDivide);
            output += ic.addLine("Code" + ic.generateRepeatString(" ", 6) + "Item"
                    + ic.generateRepeatString(" ", 73) + "Fees"
                    + ic.generateRepeatString(" ", 8) + "Total");
            output += ic.addLine(ic.generateProductLines(tempJObject));

            finalOutput = output;
            System.out.println(finalOutput);
        }
        return finalOutput;
    }


}
