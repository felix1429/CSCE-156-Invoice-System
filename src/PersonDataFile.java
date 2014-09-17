import java.util.ArrayList;

public class PersonDataFile extends DataFile{

    //arrayList for unknown number of email addresses
    private ArrayList<String> emailAddresses = new ArrayList<String>();

    private Object keyArray[] = {
            "personCode",
            "firstName",
            "lastName",
            emailAddresses
    };

    public PersonDataFile (String filePath) {
        super(filePath);
    }
}
