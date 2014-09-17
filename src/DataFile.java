import java.io.*;
import java.util.ArrayList;
import org.json.*;

//extends file so DataFile object has all functionalities of a file
abstract class DataFile extends File{

    protected BufferedReader br;
    protected String filePath;
    protected int numberOfRecords;
    //arrayList for unknown number of email addresses
    protected ArrayList<String> emailAddresses = new ArrayList<String>();
    //array of person data fields
    protected Object person[] = {
            "personCode",
            "firstName",
            "lastName",
            emailAddresses
    };
    //array of address data fields
    protected String address[] = {
            "street",
            "city",
            "state",
            "zip",
            "country"
    };
    //array that contains indices of data fields
    protected Object keyArray[];


    //constructor
    public DataFile(String filePath) throws IOException {
        super(filePath);
        this.filePath = filePath;
        try {
            br = new BufferedReader(new FileReader(filePath));
            //gets first line of file which is the number of records in the file
            this.numberOfRecords = __getNumberOfRecords();
        } catch (FileNotFoundException e) {
            //TODO: add exception handling
        }
    }

    //internal method only to be called in dataFile constructor, ensuring that correct value is returned
    protected int __getNumberOfRecords() throws IOException {
        return Integer.parseInt(br.readLine());
    }
}
