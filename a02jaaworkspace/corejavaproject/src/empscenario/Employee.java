package empscenario;

public class Employee {

    private int empno;
    private double salary;

    public Employee(int empno, double salary) {
        this.empno = empno;
        this.salary = salary;
    }

    public int getEmpno() {
        return empno;
    }

    public void setEmpno(int empno) {
        this.empno = empno;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
