package ttl.larku.cust.app;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import ttl.larku.cust.dao.TheFactory;
import ttl.larku.cust.domain.Customer;
import ttl.larku.cust.service.CustomerService;

/**
 * @author whynot
 */
public class CustomerController {

//    CustomerService service = new CustomerService();
    CustomerService service = TheFactory.getCustomerService();

    public static void main(String[] args) {
        CustomerController cc = new CustomerController();
        //Customer customer = new Customer(10, "Joey", LocalDate.of(1950, 10, 10), new ArrayList<>(), Customer.Status.Normal);
        Customer customer = new Customer("Joey", LocalDate.of(1950, 10, 10), Customer.Status.Normal);

        cc.postACustomer(customer);

        cc.getAllCustomers();
    }

   public void postACustomer(Customer customer) {
        Customer newCustomer = service.addCustomer(customer);

        Collection<Customer> allCusts = service.getAllCustomers();
        System.out.println("allCusts");
        for(Customer c : allCusts) {
            System.out.println(c);
        }
    }

    public Collection<Customer> getAllCustomers() {

        Collection<Customer> allCusts = service.getAllCustomers();
        System.out.println("In Get All allCusts");
        for(Customer c : allCusts) {
            System.out.println(c);
        }

        return allCusts;
    }
}
