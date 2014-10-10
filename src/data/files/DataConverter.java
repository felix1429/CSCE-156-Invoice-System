package data.files;

import java.io.*;

public class DataConverter {

    public static void main(String args[]) throws IOException {
        DataConverter dc = new DataConverter();
        dc.iterateOverInputFiles();
    }

    /**
     * format for webgrader
    //iterates over files in input dir
    */
    /**
    public void iterateOverInputFiles() throws IOException {
        PersonDataFile pData = new PersonDataFile("data/Persons.dat");
        PersonDataFile pData = new PersonDataFile()
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
     */

    /**
     * format for local testing
     */

    //directory that holds all input files
    public final File inputFileRelativePath = new File("dataFiles/input");

    //directory that holds all output files
    public final File outputFileRelativePath = new File("dataFiles/output");

    //list of files in input dir
    File[] listOfInputFiles = inputFileRelativePath.listFiles();

    File[] listOfOutputFiles = outputFileRelativePath.listFiles();


    public void iterateOverInputFiles() throws IOException {
        for(File inputFile : listOfInputFiles) {
            if(inputFile.getName().equals("Persons.dat")) {
                PersonDataFile pData = new PersonDataFile(inputFile.getAbsolutePath());
//                System.out.println(pData.getOuterJSONObject());
//                writeToFile(pData);
                for(File inputFile1 : listOfInputFiles) {
                    if(inputFile1.getName().equals("Customers.dat")) {
                        CustomerDataFile cData = new CustomerDataFile(inputFile1.getAbsolutePath());
//                        System.out.println(cData.getOuterJSONObject());
//                        writeToFile(cData);
                    } else if (inputFile1.getName().equals("Products.dat")) {
                        ProductDataFile prData = new ProductDataFile(inputFile1.getAbsolutePath());
//                        System.out.println(prData.getOuterJSONObject());
//                        writeToFile(prData);
                    }
                }
                for(File blah : listOfInputFiles) {
                    if(blah.getName().equals("Invoices.dat")) {
                        InvoiceDataFile iData = new InvoiceDataFile(blah.getAbsolutePath());
                        System.out.println(iData.getOuterJSONObject());
                    }
                }
            }
        }

    }

    /**
     * for writing to local files
     */
    /**
    public void writeToFile(DataFile theFile) throws IOException {
        for(File outputFile : listOfOutputFiles) {
            String inputName = theFile.getName();
            String outputName = outputFile.getName();
            if(inputName.substring(0, inputName.length() - 4).equals(outputName.substring(0, outputName.length() - 5))) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
                bw.write(theFile.getOuterJSONObject());
                bw.close();
            }
        }
    }
     */
}
