package glory.json.obj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dog {
    private String name;
    private int age;
    private float weight;
    private Toy toy;
    private Toy[] toys;

    public Dog(){}

    public Dog(String name, int age, float weight) {
        this.name = name;
        this.age = age;
        this.weight = weight;
    }

    public Toy[] getToys() {
        return toys;
    }

    public void setToys(Toy[] toys) {
        this.toys = toys;
    }

    public Toy getToy() {
        return toy;
    }

    public void setToy(Toy toy) {
        this.toy = toy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", toy=" + toy +
                ", toys=" + Arrays.toString(toys) +
                '}';
    }
}
