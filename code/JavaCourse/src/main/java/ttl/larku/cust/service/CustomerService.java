package ttl.larku.cust.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import ttl.larku.cust.dao.CustomerDAO;
import ttl.larku.cust.dao.DaoFactory;
import ttl.larku.cust.dao.InMemoryCustomerDAO;
import ttl.larku.cust.dao.JPACustomerDAO;
import ttl.larku.cust.domain.Customer;

/**
 * @author whynot
 */
public class CustomerService {

    //private CustomerDAO customerDAO = new CustomerDAO();
    //private JPACustomerDAO customerDAO = new JPACustomerDAO();

    private CustomerDAO customerDAO = DaoFactory.getDao();

    //private CustomerDAO customerDAO = new JPACustomerDAO();
//    private CustomerDAO customerDAO = new InMemoryCustomerDAO();

    public Customer addCustomer(Customer customer) {
        if(customer.getDob().until(LocalDate.now(), ChronoUnit.YEARS) < 20) {
            throw new RuntimeException("Must be at least 20 years old: " + customer);
        }
        return customerDAO.insert(customer);
    }

    public boolean updateCustomer(Customer customer) {
        return customerDAO.update(customer);
    }

    public boolean deleteCustomer(int id) {
        return customerDAO.delete(id);
    }

    public Customer getCustomer(int id) {
        return customerDAO.get(id);
    }

    public Collection<Customer> getAllCustomers() {
        return customerDAO.getAll();
    }
}
