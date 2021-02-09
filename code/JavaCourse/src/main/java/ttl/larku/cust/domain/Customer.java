package ttl.larku.cust.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author whynot
 */
public class Customer {

    public enum Status {
       Privileged,
       Normal,
       Restricted
    }

    private int id;
    private String name;
    private LocalDate dob;

    private List<String> phoneNumbers = new ArrayList<>();
    private Status status;

    public Customer(String name, LocalDate dob, Status status) {
        this.name = name;
        this.dob = dob;
        this.status = status;
    }

    public Customer(int id, String name, LocalDate dob, List<String> phoneNumbers, Status status) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.phoneNumbers = phoneNumbers;
        this.status = status;
    }

    public Customer() {
    }

    public int getId() {
        return id;
    }

//    public void setId(int id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", phoneNumbers=" + phoneNumbers +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id &&
                Objects.equals(name, customer.name) &&
                Objects.equals(dob, customer.dob) &&
                Objects.equals(phoneNumbers, customer.phoneNumbers) &&
                status == customer.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dob, phoneNumbers, status);
    }
}
