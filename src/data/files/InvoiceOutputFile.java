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
        this.finalOutput = ic.generateHeader() + generateInvoice();
        System.out.println(this.finalOutput);
    }

    public String generateInvoice() throws ParseException {
        for(int count = 0; count < input.length(); count++) {
            String output = "";
            JSONObject tempJObject = (JSONObject)input.get(count);
            InvoiceController.setTotalFees(0);
            InvoiceController.setTotalTotal(0);
            InvoiceController.setTaxesOwed(0);
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
            output += ic.generateProductLines(tempJObject);
            output += ic.addLine(ic.generateRepeatString(" ", 80) + ic.generateRepeatString("=", 24));
            output += ic.addLine("SUB-TOTALS" + ic.generateRepeatString(" ", 70)
                    + "$" + ic.generateRepeatString(" ", 9 - String.valueOf(InvoiceController.getTotalFees()).length())
                    + ic.putTwoZeros(InvoiceController.getTotalFees()) + "  $"
                    + ic.generateRepeatString(" ", 10 - String.valueOf(ic.putTwoZeros(InvoiceController.getTotalTotal())).length())
                    + ic.putTwoZeros(InvoiceController.getTotalTotal()));
            output += ic.addLine("COMPLIANCE FEE" + ic.generateRepeatString(" ", 79) + "$"
                    + ic.generateRepeatString(" ", 10 - String.valueOf(ic.putTwoZeros(ic.getComplianceFee(tempJObject))).length())
                    + ic.putTwoZeros(ic.getComplianceFee(tempJObject)));
            output += ic.addLine("TAXES" + ic.generateRepeatString(" ", 88) + "$"
                    + ic.generateRepeatString(" ", 10 - String.valueOf(ic.putTwoZeros(ic.roundToTwo(InvoiceController.getTaxesOwed()))).length())
                    + ic.putTwoZeros(ic.roundToTwo(InvoiceController.getTaxesOwed())));
            output += ic.addLine("TOTAL" + ic.generateRepeatString(" ", 88) + "$"
                    + ic.generateRepeatString(" ", 10 - String.valueOf(ic.putTwoZeros(ic.roundToTwo(ic.getOverallTotal(tempJObject)))).length())
                    + ic.putTwoZeros(ic.roundToTwo(ic.getOverallTotal(tempJObject))));
            output += "\n\n\n";
            finalOutput += output;
        }
        return finalOutput;
    }


}
