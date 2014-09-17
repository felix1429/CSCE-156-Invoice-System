import java.io.File;

//extends file so DataFile object has all functionalities of a file
abstract class DataFile extends File{

    protected String filePath;

    //array of address fields
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
