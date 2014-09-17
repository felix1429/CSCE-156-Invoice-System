import java.io.File;

public class DataConverter {

    //directory that holds all input files
    public final File inputFileRelativePath = new File("/CSCE-156-Invoice-System/data/input");

    //directory that holds all output files
    public final File outputFileRelativePath = new File("/CSCE-156-Invoice-System/data/output");

    //list of files in input dir
    File[] listOfInputFiles = inputFileRelativePath.listFiles();


    public static void main(String args[]) {
        DataConverter dc = new DataConverter();

        System.out.println(dc.inputFileRelativePath.getAbsolutePath());
    }
}
