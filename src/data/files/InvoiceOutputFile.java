package data.files;

import org.json.*;

public class InvoiceOutputFile {

    private JSONArray input;

    public InvoiceOutputFile(JSONArray input) {
        this.input = input;
        generateInvoice();
    }

    public String generateInvoice() {
        for(int count = 0; count < input.length(); count++) {
            System.out.println(input.get(count));
        }
        return "";
    }
}
