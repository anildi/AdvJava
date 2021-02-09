package ttl.larku.cust.dao;

import java.util.Collection;
import ttl.larku.cust.domain.Customer;

/**
 * @author whynot
 */
public interface CustomerDAO {
    Customer insert(Customer customer);

    boolean delete(int id);

    //TODO - fix check then act
    boolean update(Customer customer);

    Customer get(int id);

    //TODO - change to List
    Collection<Customer> getAll();
}
