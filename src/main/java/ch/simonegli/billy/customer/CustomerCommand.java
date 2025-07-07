package ch.simonegli.billy.customer;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.List;
import java.util.Optional;

@Command(name = "customer",
        description = "Manages customer data.",
        subcommands = {
                CustomerCommand.AddCommand.class,
                CustomerCommand.ListCommand.class,
                CustomerCommand.GetCommand.class,
                CustomerCommand.UpdateCommand.class,
                CustomerCommand.DeleteCommand.class
        })
public class CustomerCommand {
    public CustomerCommand() {
    }

    private static final CustomerService customerService = new CustomerService();

    @Command(name = "add", description = "Adds a new customer.")
    static class AddCommand implements Runnable {
        @Option(names = {"-n", "--name"}, description = "Customer name", required = true)
        private String name;
        @Option(names = {"-s", "--street"}, description = "Street", required = true)
        private String street;
        @Option(names = {"-h", "--houseNo"}, description = "House number", required = true)
        private String houseNo;
        @Option(names = {"-p", "--postalCode"}, description = "Postal code", required = true)
        private String postalCode;
        @Option(names = {"-t", "--town"}, description = "Town", required = true)
        private String town;
        @Option(names = {"-c", "--countryCode"}, description = "Country code (e.g., CH)", required = true)
        private String countryCode;

        @Override
        public void run() {
            Customer newCustomer = new Customer();
            newCustomer.setName(name);
            newCustomer.setStreet(street);
            newCustomer.setHouseNo(houseNo);
            newCustomer.setPostalCode(postalCode);
            newCustomer.setTown(town);
            newCustomer.setCountryCode(countryCode);
            customerService.addCustomer(newCustomer);
            System.out.println("Customer added successfully with ID: " + newCustomer.getId());
        }
    }

    @Command(name = "list", description = "Lists all customers.")
    static class ListCommand implements Runnable {
        @Override
        public void run() {
            List<Customer> customers = customerService.getAllCustomers();
            if (customers.isEmpty()) {
                System.out.println("No customers found.");
            } else {
                customers.forEach(customer -> System.out.println(customer.toString()));
            }
        }
    }

    @Command(name = "get", description = "Gets a customer by ID.")
    static class GetCommand implements Runnable {
        @Parameters(index = "0", description = "Customer ID")
        private String id;

        @Override
        public void run() {
            Optional<Customer> customer = customerService.getCustomerById(id);
            customer.ifPresentOrElse(
                    c -> System.out.println(c.toString()),
                    () -> System.out.println("Customer with ID " + id + " not found.")
            );
        }
    }

    @Command(name = "update", description = "Updates an existing customer.")
    static class UpdateCommand implements Runnable {
        @Parameters(index = "0", description = "Customer ID to update")
        private String id;
        @Option(names = {"-n", "--name"}, description = "Customer name")
        private String name;
        @Option(names = {"-s", "--street"}, description = "Street")
        private String street;
        @Option(names = {"-h", "--houseNo"}, description = "House number")
        private String houseNo;
        @Option(names = {"-p", "--postalCode"}, description = "Postal code")
        private String postalCode;
        @Option(names = {"-t", "--town"}, description = "Town")
        private String town;
        @Option(names = {"-c", "--countryCode"}, description = "Country code (e.g., CH)")
        private String countryCode;

        @Override
        public void run() {
            Optional<Customer> existingCustomer = customerService.getCustomerById(id);
            if (existingCustomer.isPresent()) {
                Customer customerToUpdate = existingCustomer.get();
                if (name != null) customerToUpdate.setName(name);
                if (street != null) customerToUpdate.setStreet(street);
                if (houseNo != null) customerToUpdate.setHouseNo(houseNo);
                if (postalCode != null) customerToUpdate.setPostalCode(postalCode);
                if (town != null) customerToUpdate.setTown(town);
                if (countryCode != null) customerToUpdate.setCountryCode(countryCode);

                Optional<Customer> updated = customerService.updateCustomer(id, customerToUpdate);
                updated.ifPresent(c -> System.out.println("Customer updated successfully: " + c.toString()));
            } else {
                System.out.println("Customer with ID " + id + " not found.");
            }
        }
    }

    @Command(name = "delete", description = "Deletes a customer by ID.")
    static class DeleteCommand implements Runnable {
        @Parameters(index = "0", description = "Customer ID to delete")
        private String id;

        @Override
        public void run() {
            if (customerService.deleteCustomer(id)) {
                System.out.println("Customer with ID " + id + " deleted successfully.");
            } else {
                System.out.println("Customer with ID " + id + " not found.");
            }
        }
    }
}
