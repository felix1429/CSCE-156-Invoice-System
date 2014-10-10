package data.files;

import data.controllers.InvoiceController;
import org.json.*;

import java.text.ParseException;

public class InvoiceOutputFile {

    private JSONArray input;
    private String finalDetailOutput = "";
    private String finalSummaryOutput = "";
    private String finalOutput;
    private InvoiceController ic = new InvoiceController();
    private String invoiceSalespersonDivide = ic.generateRepeatString("=", 24);
    private String customerProductDivide = ic.generateRepeatString("-", 43);
    public InvoiceOutputFile(JSONArray input) throws ParseException {
        this.input = input;
        this.finalDetailOutput = ic.generateDetailsHeader() + generateInvoiceDetailReports();
        this.finalSummaryOutput = ic.generateSummaryHeader() + generateSummaryReport();
        this.finalOutput = this.finalSummaryOutput + this.finalDetailOutput;
        System.out.println(this.finalOutput);
    }

    public String generateInvoiceDetailReports() throws ParseException {
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
            finalDetailOutput += output;
        }
        return finalDetailOutput;
    }

    public String generateSummaryReport() throws ParseException {
        String output = "";
        output += ic.addLine("Invoice   Customer" + ic.generateRepeatString(" ", 42)
                + "Salesperson" + ic.generateRepeatString(" ", 22) + "Subtotal"
                + ic.generateRepeatString(" ", 8) + "Fees" + ic.generateRepeatString(" ", 7)
                + "Taxes" + ic.generateRepeatString(" ", 7) + "Total");
        finalSummaryOutput += output;
        double endSubtotal = 0;
        double endFees = 0;
        double endTaxes = 0;
        double endTotal = 0;
        for(int counter = 0; counter < input.length(); counter++) {
            output = "";
            InvoiceController.setTotalFees(0);
            InvoiceController.setTotalTotal(0);
            InvoiceController.setTaxesOwed(0);
            JSONObject tempJObject = (JSONObject)input.get(counter);
            output += tempJObject.get("invoice") + ic.generateRepeatString(" ", 4);
            output += ic.getNestedData(tempJObject, "customer", "name")
                    + ic.generateRepeatString(" ", 50 - ic.getNestedData(tempJObject, "customer", "name").length());
            output += ic.getNestedData(tempJObject, "salesperson", "lastName") + ", " + ic.getNestedData(tempJObject, "salesperson", "firstName")
                    + ic.generateRepeatString(" ", 29 - (ic.getNestedData(tempJObject, "salesperson", "lastName") + " " + ic.getNestedData(tempJObject, "salesperson", "firstName")).length());
            ic.generateProductLines(tempJObject);
            String subtotal = ic.putTwoZeros(InvoiceController.getTotalTotal());
            output += "$" + ic.generateRepeatString(" ", 10 - subtotal.length())
                    + subtotal;
            String fees = ic.putTwoZeros(InvoiceController.getTotalFees());
            output += " $" + ic.generateRepeatString(" ", 10 - fees.length())
                    + fees;
            String taxes = ic.putTwoZeros(ic.roundToTwo(InvoiceController.getTaxesOwed()));
            output += " $" + ic.generateRepeatString(" ", 10 - taxes.length())
                    + taxes;
            String total = ic.putTwoZeros(ic.roundToTwo(ic.getOverallTotal(tempJObject)));
            output += ic.addLine(" $" + ic.generateRepeatString(" ", 10 - total.length())
                    + total);
            finalSummaryOutput += output;
            endSubtotal += Double.parseDouble(subtotal);
            endFees += Double.parseDouble(fees);
            endTaxes += Double.parseDouble(taxes);
            endTotal += Double.parseDouble(total);
        }
        finalSummaryOutput += ic.addLine(ic.generateRepeatString("=", 137));
        finalSummaryOutput += "TOTALS" + ic.generateRepeatString(" ", 84) + "$ " + ic.putTwoZeros(endSubtotal);
        finalSummaryOutput += " $" + ic.generateRepeatString(" ", 10 - (ic.putTwoZeros(endFees).length()))
                + ic.putTwoZeros(endFees);
        finalSummaryOutput += " $" + ic.generateRepeatString(" ", 10 - (ic.putTwoZeros(endTaxes).length()))
                + ic.putTwoZeros(endTaxes);
        finalSummaryOutput += " $" + ic.generateRepeatString(" ", 10 - (ic.putTwoZeros(endTotal).length()))
                + ic.putTwoZeros(endTotal);
        finalSummaryOutput += "\n\n\n\n";
        return finalSummaryOutput;
    }
}
