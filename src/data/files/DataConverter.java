package data.files;

import java.io.*;

public class DataConverter {

    //directory that holds all input files
    public final File inputFileRelativePath = new File("dataFiles/input");

    //directory that holds all output files
    public final File outputFileRelativePath = new File("dataFiles/output");

    //list of files in input dir
    File[] listOfInputFiles = inputFileRelativePath.listFiles();

    File[] listOfOutputFiles = outputFileRelativePath.listFiles();


    public static void main(String args[]) throws IOException {
        DataConverter dc = new DataConverter();
        dc.iterateOverInputFiles();
    }

    //iterates over files in input dir
    public void iterateOverInputFiles() throws IOException {
        for(File inputFile : listOfInputFiles) {
            if(inputFile.getName().equals("Persons.dat")) {
                PersonDataFile pData = new PersonDataFile(inputFile.getAbsolutePath());
                writeToFile(pData);
                for (File inputFile1 : listOfInputFiles) {
                    if (inputFile1.getName().equals("Customers.dat")) {
                        CustomerDataFile cData = new CustomerDataFile(inputFile1.getAbsolutePath());
                        writeToFile(cData);
                    } else if (inputFile1.getName().equals("Products.dat")) {
                        ProductDataFile prData = new ProductDataFile(inputFile1.getAbsolutePath());

                    }
                }
            }
        }
    }

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
}
