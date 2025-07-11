package ch.simonegli.billy.customer;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerFileManager fileManager;
    private List<Customer> customers;

    public CustomerService(CustomerFileManager fileManager) {
        this.fileManager = fileManager;
        this.customers = fileManager.readCustomers();
    }

    public Customer addCustomer(Customer customer) {
        customer.setId(UUID.randomUUID().toString());
        this.customers.add(customer);
        fileManager.writeCustomers(this.customers);
        return customer;
    }

    public List<Customer> getCustomers() {
        return this.customers;
    }

    public Customer getCustomerById(UUID id) {
        return this.customers.stream()
                .filter(c -> c.getId().equals(id.toString()))
                .findFirst()
                .orElse(null);
    }

    public Optional<Customer> updateCustomer(String id, Customer updatedCustomer) {
        Optional<Customer> existingCustomer = Optional.ofNullable(getCustomerById(UUID.fromString(id)));
        if (existingCustomer.isPresent()) {
            Customer customerToUpdate = existingCustomer.get();
            customerToUpdate.setName(updatedCustomer.getName());
            customerToUpdate.setStreet(updatedCustomer.getStreet());
            customerToUpdate.setHouseNo(updatedCustomer.getHouseNo());
            customerToUpdate.setPostalCode(updatedCustomer.getPostalCode());
            customerToUpdate.setTown(updatedCustomer.getTown());
            customerToUpdate.setCountryCode(updatedCustomer.getCountryCode());
            fileManager.writeCustomers(this.customers);
            return Optional.of(customerToUpdate);
        }
        return Optional.empty();
    }

    public boolean deleteCustomer(String id) {
        boolean removed = this.customers.removeIf(c -> c.getId().equals(id));
        if (removed) {
            fileManager.writeCustomers(this.customers);
        }
        return removed;
    }
}
