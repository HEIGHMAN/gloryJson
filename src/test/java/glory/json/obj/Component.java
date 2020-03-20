package glory.json.obj;

/**
 * 玩具由哪些东西构成
 */
public class Component {
    private String component1;
    private float content1;

    public Component(){}

    public Component(String component1, float content1) {
        this.component1 = component1;
        this.content1 = content1;
    }

    public String getComponent1() {
        return component1;
    }

    public void setComponent1(String component1) {
        this.component1 = component1;
    }

    public float getContent1() {
        return content1;
    }

    public void setContent1(float content1) {
        this.content1 = content1;
    }

    @Override
    public String toString() {
        return "Component{" +
                "component1='" + component1 + '\'' +
                ", content1=" + content1 +
                '}';
    }
}
