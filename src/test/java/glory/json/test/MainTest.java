package glory.json.test;

import com.alibaba.fastjson.JSON;
import glory.json.obj.*;
import glory.json.obj2.Student;
import glory.json.obj3.Car;
import glory.liu.json.GloryJson;
import javafx.beans.binding.ObjectExpression;
import org.junit.Test;

import java.util.*;

public class MainTest {

    /**
     * 测试基本数据类型，数组的json化
     */
    @Test
    public void test1(){
        People people = new People();
        String[] hb = {"dance","sing","run","buy"};
        char[] chs = {'a','b','c','d','e'};
        int[] number = {23,43,54,56,45,12};
        boolean[] bools={true,false,false,true};
        people.setName("glory");
        people.setAge(20);
        people.setHobby(hb);
        people.setChs(chs);
        people.setNumber(number);
        people.setBt((byte)12);
        people.setSt((short)100);
        people.setLg(10000000);
        people.setFt(23.3f);
        people.setDb(129.2378);
        people.setCh('h');
        people.setBool(true);
        people.setBools(bools);
        System.out.println(GloryJson.toGloryJsonString(people));
        System.out.println(JSON.toJSONString(people));
    }

    @Test
    public void test2(){
        Dog dog = new Dog("Handson",3,5.6f);
        Toy toy = new Toy("小球",5.3f);
//        Toy[] toys = {new Toy("toy1",12.0f),new Toy("toy2",23.5f)};
        Toy[] toys = {new Toy("toy1",12.0f)};
        Component component = new Component("木材",26.4f);
        toy.setComponent(component);
        dog.setToy(toy);
        dog.setToys(toys);
        String json = GloryJson.toGloryJsonString(dog);
//        System.out.println(dog);
        //使用fastjson转换为自己的Json到对象  成功
//        System.out.println(JSON.parseObject(json,Dog.class));
//        System.out.println(json);
        System.out.println((Dog)GloryJson.gloryJsonToObject(json,Dog.class));
//        System.out.println(json);
//        String[] data = json.split("[:|\"|,|\\{|\\}|\\[|\\]]");
//        for (String s : data){
//            System.out.print(s+" ");
//        }
//        System.out.println();
//        GloryJson.gloryJsonToObject(json,Dog.class);
//        System.out.println(Arrays.toString(data));
//        System.out.println(json);
//        System.out.println();
//        GloryJson.handleJson(json);
//        System.out.println(JSON.toJSONString(dog));
    }

    @Test
    public void test3(){
        Student student = new Student("Glory",20);
        List<List<Integer>> number = new ArrayList<List<Integer>>();
        List<Integer> list1 = new ArrayList<Integer>();
        list1.add(10);
        list1.add(11);
        list1.add(12);
        list1.add(13);
        List<Integer> list2 = new ArrayList<Integer>();
        list2.add(20);
        list2.add(21);
        list2.add(22);
        list2.add(23);
        List<Integer> list3 = new ArrayList<Integer>();
        list3.add(30);
        list3.add(31);
        list3.add(32);
        list3.add(33);
        number.add(list1);
        number.add(list2);
        number.add(list3);
//        List<Book> books = new ArrayList<Book>();
//        books.add(new Book("英语",23.8f));
//        books.add(new Book("语文",28.8f));
//        books.add(new Book("数学",20.8f));
//        books.add(new Book("物理",83.8f));
//        student.setBooks(books);
        student.setNumbers(number);
        System.out.println(GloryJson.toGloryJsonString(student));
        System.out.println(JSON.toJSONString(student));
    }

    @Test
    public void test4(){
        Map<String,Integer> map = new HashMap<String, Integer>();
        map.put("price",124000);
        map.put("fastest",230);
        map.put("compentain",7);
        Car car = new Car("现代",map);

        System.out.println(GloryJson.toGloryJsonString(car));
        System.out.println(JSON.toJSONString(car));
    }

    @Test
    public void test5(){
        Trees trees = new Trees();
        trees.setHeight(120);
        List<String> list = new LinkedList<String>();
        list.add("a");
        list.add("aa");
        list.add("aaa");
        list.add("aaaa");
        trees.setData(list);
        System.out.println(GloryJson.toGloryJsonString(trees));
//        System.out.println(JSON.toJSONString(trees));
    }

    @Test
    public void test6(){
//        int a=10;
//        int b=a++;
//        System.out.println(a+" "+b);
//        String str = "012345678";
//        System.out.println(str.substring(1,4));

        String json = "{\"name\":\"Handson\",\"toys\":[{\"name\":\"toy1\",\"price\":12.0},{\"name\":\"toy2\",\"price\":23.5},{\"name\":\"toy3\",\"price\":11.9}],\"toy\":{\"name\":\"小球\",\"component\":{\"content1\":26.4,\"component1\":\"木材\"},\"price\":5.3},\"weight\":5.6,\"age\":3}";
//        GloryJson.handleJson(json);
    }

    @Test
    public void test7(){
        GloryJson.handleClassAndParam(Dog.class);
//        System.out.println((char)('A'+32));
//        String key = "a";
//        String methodName = "set" + (char)(key.charAt(0)-32) + key.substring(1);
//        System.out.println(methodName);

    }

    @Test
    public void test8(){
        String a ="[12.10,23.30,23.1,123]";
        String[] data = a.split("[^0-9.]");
        for(String e : data){
            System.out.print(e + " ");
        }
//        System.out.println(Arrays.toString(a.split("[^0-9]")));

//        Object[] b = (Object[]) Array.newInstance(a.getClass(),4);
//        System.out.println(Arrays.toString(b));
    }

}
