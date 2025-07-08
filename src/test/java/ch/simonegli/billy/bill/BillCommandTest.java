package ch.simonegli.billy.bill;

import ch.simonegli.billy.customer.Customer;
import ch.simonegli.billy.customer.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

@ExtendWith(MockitoExtension.class)
class BillCommandTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private BillService billService;

    @InjectMocks
    private BillCommand billCommand;

    @Test
    void givenValidInput_whenCall_thenServicesAreCalled() throws IOException {
        // Arrange
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer();
        customer.setId(customerId.toString());

        Mockito.when(customerService.getCustomerById(customerId.toString())).thenReturn(Optional.of(customer));

        billCommand = new BillCommand(customerService, billService);
        new CommandLine(billCommand).parseArgs(
                "-c", customerId.toString(),
                "-r", "2025-07-08,100,1.5",
                "-o", "bill.pdf"
        );

        // Act
        billCommand.call();

        // Assert
        Mockito.verify(customerService).getCustomerById(customerId.toString());
        Mockito.verify(billService).createPdf(ArgumentMatchers.any(Bill.class), ArgumentMatchers.any(Path.class));
    }
}
