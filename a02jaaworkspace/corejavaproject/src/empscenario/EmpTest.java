package empscenario;

public class EmpTest {

    public static void main(String[] args) {

         Employee emp = Logic.getEmployee();
        System.out.println(emp.getEmpno() + "  " + emp.getSalary());
    }
}
