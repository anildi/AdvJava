package ttl.larku.cust.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import ttl.larku.cust.domain.Customer;

/**
 * @author whynot
 */
public class InMemoryCustomerDAO implements CustomerDAO {

    private Map<Integer, Customer> customers = new ConcurrentHashMap<>();
    //private static int nextId = 1;
    private static AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public Customer insert(Customer customer) {
//        int id = nextId++;
        int id = nextId.getAndIncrement();

        customer = new Customer(id, customer.getName(), customer.getDob(), customer.getPhoneNumbers(), customer.getStatus());

        customers.put(id, customer);

        return customer;
    }

    @Override
    public boolean delete(int id) {
        return customers.remove(id) != null;
    }

    //TODO - fix check then act
    @Override
    public boolean update(Customer customer) {
        if(customers.containsKey(customer.getId())) {
            customers.put(customer.getId(), customer);
            return true;
        }
        return false;
    }

    @Override
    public Customer get(int id) {
        return customers.get(id);
    }

    //TODO - change to List
    @Override
    public Collection<Customer> getAll() {
        return customers.values();
    }
}
