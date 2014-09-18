import java.io.IOException;
import org.json.*;

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
        this.JSONName = "customers";
    }


}
