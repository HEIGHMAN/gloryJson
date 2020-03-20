package glory.json.obj3;

import java.util.Map;

public class Car {
    private String carName;
    private Map<?,?> info;

    public Car(){}

    public Car(String carName, Map<?, ?> info) {
        this.carName = carName;
        this.info = info;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public Map<?, ?> getInfo() {
        return info;
    }

    public void setInfo(Map<?, ?> info) {
        this.info = info;
    }
}
