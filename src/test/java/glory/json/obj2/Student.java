package glory.json.obj2;

import java.util.List;

public class Student {
    private String name;
    private int age;
    List<?> numbers;

    public Student(){}

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
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

    public List<?> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<?> numbers) {
        this.numbers = numbers;
    }
//    public List<Book> getBooks() {
//        return books;
//    }
//
//    public void setBooks(List<Book> books) {
//        this.books = books;
//    }
}
