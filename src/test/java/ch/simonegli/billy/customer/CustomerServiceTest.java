package ch.simonegli.billy.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class CustomerServiceTest {

    private CustomerFileManager mockFileManager;
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        mockFileManager = Mockito.mock(CustomerFileManager.class);
        // Mock initial state: fileManager returns an empty list by default
        Mockito.when(mockFileManager.readCustomers()).thenReturn(new ArrayList<>());
        customerService = new CustomerService(mockFileManager);
    }

    @Test
    @DisplayName("Given a new customer, when addCustomer is called, then the customer is added and saved")
    void givenNewCustomer_whenAddCustomerIsCalled_thenCustomerIsAddedAndSaved() {
        // Arrange
        Customer customer = new Customer();
        customer.setName("Test Name");
        customer.setStreet("Test Street");
        customer.setHouseNo("1");
        customer.setPostalCode("1234");
        customer.setTown("Test Town");
        customer.setCountryCode("CH");

        // Act
        Customer addedCustomer = customerService.addCustomer(customer);

        // Assert
        Assertions.assertNotNull(addedCustomer.getId());
        Assertions.assertEquals("Test Name", addedCustomer.getName());
        Mockito.verify(mockFileManager, Mockito.times(1)).writeCustomers(Mockito.anyList());
        Assertions.assertEquals(1, customerService.getAllCustomers().size());
    }

    @Test
    @DisplayName("Given existing customers, when getAllCustomers is called, then all customers are returned")
    void givenExistingCustomers_whenGetAllCustomersIsCalled_thenAllCustomersAreReturned() {
        // Arrange
        Customer customer1 = new Customer();
        customer1.setId("id1");
        customer1.setName("Name1");
        customer1.setStreet("Street1");
        customer1.setHouseNo("1");
        customer1.setPostalCode("1111");
        customer1.setTown("Town1");
        customer1.setCountryCode("CH");

        Customer customer2 = new Customer();
        customer2.setId("id2");
        customer2.setName("Name2");
        customer2.setStreet("Street2");
        customer2.setHouseNo("2");
        customer2.setPostalCode("2222");
        customer2.setTown("Town2");
        customer2.setCountryCode("CH");

        Mockito.when(mockFileManager.readCustomers()).thenReturn(Arrays.asList(customer1, customer2));
        customerService = new CustomerService(mockFileManager); // Re-initialize to load mocked data

        // Act
        List<Customer> customers = customerService.getAllCustomers();

        // Assert
        Assertions.assertEquals(2, customers.size());
        Assertions.assertTrue(customers.contains(customer1));
        Assertions.assertTrue(customers.contains(customer2));
    }

    @Test
    @DisplayName("Given an existing customer ID, when getCustomerById is called, then the customer is returned")
    void givenExistingCustomerId_whenGetCustomerByIdIsCalled_thenCustomerIsReturned() {
        // Arrange
        Customer customer = new Customer();
        customer.setId("id1");
        customer.setName("Name1");
        customer.setStreet("Street1");
        customer.setHouseNo("1");
        customer.setPostalCode("1111");
        customer.setTown("Town1");
        customer.setCountryCode("CH");

        Mockito.when(mockFileManager.readCustomers()).thenReturn(Arrays.asList(customer));
        customerService = new CustomerService(mockFileManager);

        // Act
        Optional<Customer> foundCustomer = customerService.getCustomerById("id1");

        // Assert
        Assertions.assertTrue(foundCustomer.isPresent());
        Assertions.assertEquals(customer, foundCustomer.get());
    }

    @Test
    @DisplayName("Given a non-existing customer ID, when getCustomerById is called, then an empty optional is returned")
    void givenNonExistingCustomerId_whenGetCustomerByIdIsCalled_thenEmptyOptionalIsReturned() {
        // Arrange (no customers in file manager)

        // Act
        Optional<Customer> foundCustomer = customerService.getCustomerById("nonExistentId");

        // Assert
        Assertions.assertFalse(foundCustomer.isPresent());
    }

    @Test
    @DisplayName("Given an existing customer, when updateCustomer is called, then the customer is updated and saved")
    void givenExistingCustomer_whenUpdateCustomerIsCalled_thenCustomerIsUpdatedAndSaved() {
        // Arrange
        Customer originalCustomer = new Customer();
        originalCustomer.setId("id1");
        originalCustomer.setName("Name1");
        originalCustomer.setStreet("Street1");
        originalCustomer.setHouseNo("1");
        originalCustomer.setPostalCode("1111");
        originalCustomer.setTown("Town1");
        originalCustomer.setCountryCode("CH");

        Mockito.when(mockFileManager.readCustomers()).thenReturn(new ArrayList<>(Arrays.asList(originalCustomer)));
        customerService = new CustomerService(mockFileManager);

        Customer updatedInfo = new Customer();
        updatedInfo.setId("id1");
        updatedInfo.setName("Updated Name");
        updatedInfo.setStreet("Updated Street");
        updatedInfo.setHouseNo("2");
        updatedInfo.setPostalCode("2222");
        updatedInfo.setTown("Updated Town");
        updatedInfo.setCountryCode("DE");

        // Act
        Optional<Customer> result = customerService.updateCustomer("id1", updatedInfo);

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("Updated Name", result.get().getName());
        Assertions.assertEquals("Updated Street", result.get().getStreet());
        Mockito.verify(mockFileManager, Mockito.times(1)).writeCustomers(Mockito.anyList());
    }

    @Test
    @DisplayName("Given a non-existing customer, when updateCustomer is called, then an empty optional is returned")
    void givenNonExistingCustomer_whenUpdateCustomerIsCalled_thenEmptyOptionalIsReturned() {
        // Arrange (no customers in file manager)
        Customer updatedInfo = new Customer();
        updatedInfo.setId("id99");
        updatedInfo.setName("Updated Name");
        updatedInfo.setStreet("Updated Street");
        updatedInfo.setHouseNo("2");
        updatedInfo.setPostalCode("2222");
        updatedInfo.setTown("Updated Town");
        updatedInfo.setCountryCode("DE");

        // Act
        Optional<Customer> result = customerService.updateCustomer("id99", updatedInfo);

        // Assert
        Assertions.assertFalse(result.isPresent());
        Mockito.verify(mockFileManager, Mockito.never()).writeCustomers(Mockito.anyList());
    }

    @Test
    @DisplayName("Given an existing customer ID, when deleteCustomer is called, then the customer is removed and saved")
    void givenExistingCustomerId_whenDeleteCustomerIsCalled_thenCustomerIsRemovedAndSaved() {
        // Arrange
        Customer customer = new Customer();
        customer.setId("id1");
        customer.setName("Name1");
        customer.setStreet("Street1");
        customer.setHouseNo("1");
        customer.setPostalCode("1111");
        customer.setTown("Town1");
        customer.setCountryCode("CH");

        Mockito.when(mockFileManager.readCustomers()).thenReturn(new ArrayList<>(Arrays.asList(customer)));
        customerService = new CustomerService(mockFileManager);

        // Act
        boolean removed = customerService.deleteCustomer("id1");

        // Assert
        Assertions.assertTrue(removed);
        Assertions.assertEquals(0, customerService.getAllCustomers().size());
        Mockito.verify(mockFileManager, Mockito.times(1)).writeCustomers(Mockito.anyList());
    }

    @Test
    @DisplayName("Given a non-existing customer ID, when deleteCustomer is called, then no customer is removed")
    void givenNonExistingCustomerId_whenDeleteCustomerIsCalled_thenNoCustomerIsRemoved() {
        // Arrange
        Customer customer = new Customer();
        customer.setId("id1");
        customer.setName("Name1");
        customer.setStreet("Street1");
        customer.setHouseNo("1");
        customer.setPostalCode("1111");
        customer.setTown("Town1");
        customer.setCountryCode("CH");

        Mockito.when(mockFileManager.readCustomers()).thenReturn(new ArrayList<>(Arrays.asList(customer)));
        customerService = new CustomerService(mockFileManager);

        // Act
        boolean removed = customerService.deleteCustomer("nonExistentId");

        // Assert
        Assertions.assertFalse(removed);
        Assertions.assertEquals(1, customerService.getAllCustomers().size());
        Mockito.verify(mockFileManager, Mockito.never()).writeCustomers(Mockito.anyList());
    }
}