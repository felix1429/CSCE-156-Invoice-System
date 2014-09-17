import java.io.File;
import java.util.ArrayList;

//extends file so DataFile object has all functionalities of a file
abstract class DataFile extends File{

    protected String filePath;

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

    public DataFile(String filePath) {
        super(filePath);
        this.filePath = filePath;
    }
}
