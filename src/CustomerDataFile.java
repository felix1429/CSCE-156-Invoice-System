import java.io.IOException;

public class CustomerDataFile extends DataFile{

    protected int numberOfRecords;
    protected Object keyArray[] = {
            "customerCode",
            "type",
            "primaryContact",
            "name",
            address
    };

    public CustomerDataFile(String filePath) throws IOException {
        super(filePath);
    }
}
