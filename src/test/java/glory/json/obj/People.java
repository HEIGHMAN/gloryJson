package glory.json.obj;

public class People {
    private String name;
    private int age;
    private String[] hobby;
    private char[] chs;
    private int[] number;
    private boolean bool;
    private boolean[] bools;
    private float ft;
    private double db;
    private byte bt;
    private short st;
    private long lg;
    private char ch;
    public People(){}

    public People(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public People(String name, int age, String[] hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public boolean[] getBools() {
        return bools;
    }

    public void setBools(boolean[] bools) {
        this.bools = bools;
    }

    public char getCh() {
        return ch;
    }

    public void setCh(char ch) {
        this.ch = ch;
    }

    public boolean isBool() {
        return bool;
    }

    public float getFt() {
        return ft;
    }

    public void setFt(float ft) {
        this.ft = ft;
    }

    public double getDb() {
        return db;
    }

    public void setDb(double db) {
        this.db = db;
    }

    public byte getBt() {
        return bt;
    }

    public void setBt(byte bt) {
        this.bt = bt;
    }

    public short getSt() {
        return st;
    }

    public void setSt(short st) {
        this.st = st;
    }

    public long getLg() {
        return lg;
    }

    public void setLg(long lg) {
        this.lg = lg;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char[] getChs() {
        return chs;
    }

    public void setChs(char[] chs) {
        this.chs = chs;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String[] getHobby() {
        return hobby;
    }

    public void setHobby(String[] hobby) {
        this.hobby = hobby;
    }

    public int[] getNumber() {
        return number;
    }

    public void setNumber(int[] number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
