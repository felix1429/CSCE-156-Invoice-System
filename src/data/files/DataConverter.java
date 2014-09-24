package data.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DataConverter {

    //directory that holds all input files
    public final File inputFileRelativePath = new File("dataFiles/input");

    //directory that holds all output files
    public final File outputFileRelativePath = new File("dataFiles/output");

    //list of files in input dir
    File[] listOfInputFiles = inputFileRelativePath.listFiles();


    public static void main(String args[]) throws IOException {
        DataConverter dc = new DataConverter();
        dc.iterateOverInputFiles();
    }

    //iterates over files in input dir
    public void iterateOverInputFiles() throws IOException {
        for(File inputFile : listOfInputFiles) {
            if(inputFile.getName().equals("Persons.dat")) {
                PersonDataFile pData = new PersonDataFile(inputFile.getAbsolutePath());
//                System.out.println(pData.getOuterJSONObject());
                for (File inputFile1 : listOfInputFiles) {
                    if (inputFile1.getName().equals("Customers.dat")) {
                        CustomerDataFile cData = new CustomerDataFile(inputFile1.getAbsolutePath());
//                        System.out.println(cData.getOuterJSONObject());
                    } else if (inputFile1.getName().equals("Products.dat")) {
                        ProductDataFile prData = new ProductDataFile(inputFile1.getAbsolutePath());

                    }
                }
            }
        }
    }
}
