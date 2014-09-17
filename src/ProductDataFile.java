
public class ProductDataFile extends DataFile{

    protected Object keyArray[] = {
            "code",
            "productType"
    };

    private Object equipmentArray [] = {
            "name",
            "pricePerUnit"
    };

    private Object liscenceArray [] = {
            "name",
            "serviceFee",
            "annualLiscenceFee"
    };

    private Object consultationArray [] = {
            "name",
            person,
            "hourlyFee"
    };

    public ProductDataFile(String filePath) {
        super(filePath);
    }
}
