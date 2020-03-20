package glory.json.obj2;

public class Book {
    private String bName;
    private float bPrice;

    public Book(){}

    public Book(String bName, float bPrice) {
        this.bName = bName;
        this.bPrice = bPrice;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public float getbPrice() {
        return bPrice;
    }

    public void setbPrice(float bPrice) {
        this.bPrice = bPrice;
    }
}
