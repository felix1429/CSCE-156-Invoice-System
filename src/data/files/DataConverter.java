package data.files;

import java.io.*;

public class DataConverter {

    public static void main(String args[]) throws IOException {
        DataConverter dc = new DataConverter();
        dc.iterateOverInputFiles();
    }

    //iterates over files in input dir
    public void iterateOverInputFiles() throws IOException {
        PersonDataFile pData = new PersonDataFile("data/Persons.dat");
        writeToFile(pData, pData.getName().substring(0, pData.getName().length() - 4));
        CustomerDataFile cData = new CustomerDataFile("data/Customers.dat");
        writeToFile(cData, cData.getName().substring(0, cData.getName().length() - 4));
        ProductDataFile prData = new ProductDataFile("data/Products.dat");
        writeToFile(prData, prData.getName().substring(0, prData.getName().length() - 4));
    }

    public void writeToFile(DataFile theFile, String fileName) throws IOException {
        File json = new File("data/" + fileName + ".json");
        BufferedWriter bw = new BufferedWriter(new FileWriter(json));
        bw.write(theFile.getOuterJSONObject());
        bw.close();
    }
}
