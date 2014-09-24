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
            if(inputFile.getName().equals("Customers.dat")) {
                CustomerDataFile cData = new CustomerDataFile(inputFile.getAbsolutePath());
                System.out.println(cData.finalJSONString);
            }else if(inputFile.getName().equals("Persons.dat")) {
                PersonDataFile pData = new PersonDataFile(inputFile.getAbsolutePath());
            }else if(inputFile.getName().equals("Products.dat")) {
                ProductDataFile prData = new ProductDataFile(inputFile.getAbsolutePath());
            }else {
                throw new FileNotFoundException("There was an error in retrieving the file\n"
                + inputFile.getName() + " was not found in the input directory");
            }
        }
    }
}
