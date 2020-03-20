package glory.json.obj;

public class Toy {
    private String name;
    private float price;
    private Component component;
    public Toy(){}
    public Toy(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Toy{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", component=" + component +
                '}';
    }
}
