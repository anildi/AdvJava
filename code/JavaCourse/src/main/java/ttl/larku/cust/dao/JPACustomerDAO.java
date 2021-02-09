package ttl.larku.cust.dao;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import ttl.larku.cust.domain.Customer;

/**
 * @author whynot
 */
public class JPACustomerDAO implements CustomerDAO{

    private Map<Integer, Customer> customers = new ConcurrentHashMap<>();
    //private static int nextId = 1;
    private static AtomicInteger nextId = new AtomicInteger(1);

    public Customer insert(Customer customer) {
//        int id = nextId++;
        int id = nextId.getAndIncrement();

        customer.setName("JPA: " + customer.getName());

        customer = new Customer(id, customer.getName(), customer.getDob(), customer.getPhoneNumbers(), customer.getStatus());

        customers.put(id, customer);

        return customer;
    }

    public boolean delete(int id) {
        return customers.remove(id) != null;
    }

    //TODO - fix check then act
    public boolean update(Customer customer) {
        if(customers.containsKey(customer.getId())) {
            customers.put(customer.getId(), customer);
            return true;
        }
        return false;
    }

    public Customer get(int id) {
        return customers.get(id);
    }

    //TODO - change to List
    public Collection<Customer> getAll() {
        return customers.values();
    }
}
