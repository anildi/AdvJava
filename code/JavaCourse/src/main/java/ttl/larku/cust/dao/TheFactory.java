package ttl.larku.cust.dao;

import java.util.ResourceBundle;
import ttl.larku.cust.service.CustomerService;

/**
 * @author whynot
 */
public class TheFactory {

    public static CustomerDAO getDao() {
        ResourceBundle bundle = ResourceBundle.getBundle("customerapp");
        String profile = bundle.getString("custapp.profile");
        switch (profile) {
            case "development":
                return new InMemoryCustomerDAO();
            case "production":
                return new JPACustomerDAO();
            default:
                throw new RuntimeException("Unknown profile: " + profile);
        }
    }

    public static CustomerService getCustomerService() {
        CustomerDAO dao = getDao();
        CustomerService cs = new CustomerService(dao);

        return cs;
    }
}
