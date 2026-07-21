package programmingbasics.a02inheritanceexample;

public class Person {
    private String name;
    private int age;
    private int mobileNumber;
    private String email;

    public int getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(int mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }


    public void addEmil(String email)
    {
        this.email = email;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    // Method to display Person info
    public void displayInfo() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
    }
}
