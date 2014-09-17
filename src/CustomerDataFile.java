
public class CustomerDataFile extends DataFile{

    private Object keyArray[] = {
            "customerCode",
            "type",
            "primaryContact",
            "name",
            address
    };

    public CustomerDataFile(String filePath) {
        super(filePath);
    }
}
