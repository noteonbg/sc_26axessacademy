package programmingbasics.a05abstraction;

public abstract class MedicalProfessional {
    private String name;
    private int age;

    public MedicalProfessional(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    // Abstract method to be implemented by subclasses
    public abstract void showDetails();
}
