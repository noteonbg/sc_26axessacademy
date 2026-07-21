package pg57;

public class CustomerLogic {
    public static Customer[] getCustomers() {


        Customer[] customers =new Customer[2];
        customers[0]=new Customer(1,"A");
        customers[1]=new Customer(2,"B");
        return customers;
    }
}
