package pg57;

public class CustomerTestAssignment
{

    public static void main(String[] args) {


        // requres tonnes of intelligence... why first decide what to cook and then think how to cook.
                Customer[] customerList = CustomerLogic.getCustomers();
                for(Customer c : customerList)
                {
                    System.out.println(
                            c.getCustomerId()  +  "  " +
                                    c.getCustomerName()

                    );


                }



    }
}
