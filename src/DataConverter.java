import java.io.File;
import java.io.FileNotFoundException;

public class DataConverter {

    //directory that holds all input files
    public final File inputFileRelativePath = new File("../data/input");

    //directory that holds all output files
    public final File outputFileRelativePath = new File("../data/output");

    //list of files in input dir
    File[] listOfInputFiles = inputFileRelativePath.listFiles();


    public static void main(String args[]) {
        DataConverter dc = new DataConverter();
        System.out.println(dc.inputFileRelativePath.getAbsolutePath());
    }

    public void iterateOverInputFiles(File inputFileRelativePath) throws FileNotFoundException{
        for(File inputFile : listOfInputFiles) {
            if(inputFile.getName().equals("Customers.dat")) {
                //
            }else if(inputFile.getName().equals("Persons.dat")) {

            }else if(inputFile.getName().equals("Products.dat")) {

            }else {
                throw new FileNotFoundException("There was an error in retrieving the file\n"
                + inputFile.getName() + " was not found in the input directory");
            }
        }
    }
}
