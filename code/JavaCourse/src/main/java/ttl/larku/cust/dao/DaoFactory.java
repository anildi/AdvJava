package ttl.larku.cust.dao;

import java.util.ResourceBundle;

/**
 * @author whynot
 */
public class DaoFactory {

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
}
