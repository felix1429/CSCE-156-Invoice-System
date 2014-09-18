package datafiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DataConverter {

    //directory that holds all input files
    public final File inputFileRelativePath = new File("data/input");

    //directory that holds all output files
    public final File outputFileRelativePath = new File("data/output");

    //list of files in input dir
    File[] listOfInputFiles = inputFileRelativePath.listFiles();


    public static void main(String args[]) throws IOException {
        DataConverter dc = new DataConverter();
        dc.iterateOverInputFiles(dc.inputFileRelativePath);
    }

    //iterates over files in input dir
    public void iterateOverInputFiles(File inputFileRelativePath) throws IOException {
        for(File inputFile : listOfInputFiles) {
            if(inputFile.getName().equals("Customers.dat")) {
                CustomerDataFile cData = new CustomerDataFile(inputFileRelativePath + inputFile.getName());
            }else if(inputFile.getName().equals("Persons.dat")) {
                PersonDataFile pData = new PersonDataFile(inputFileRelativePath + inputFile.getName());
            }else if(inputFile.getName().equals("Products.dat")) {
                ProductDataFile pData = new ProductDataFile(inputFileRelativePath + inputFile.getName());
            }else {
                throw new FileNotFoundException("There was an error in retrieving the file\n"
                + inputFile.getName() + " was not found in the input directory");
            }
        }
    }
}
