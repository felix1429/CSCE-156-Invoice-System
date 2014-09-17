
public class CustomerDataFile extends DataFile{

    protected Object keyArray[] = {
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
