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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

@ExtendWith(MockitoExtension.class)
class BillCommandTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private BillService billService;

    @Mock
    private QrBillService qrBillService;

    @InjectMocks
    private BillCommand billCommand;

    void givenValidInput_whenCall_thenServicesAreCalled(String reference, String expectedReferenceInServiceCall) throws IOException {
        // Arrange
        UUID customerId = UUID.randomUUID();

        Customer customer = new Customer();
        customer.setId(customerId.toString());

        Mockito.when(customerService.getCustomerById(customerId.toString())).thenReturn(Optional.of(customer));
        Mockito.when(qrBillService.generateQrBillPng(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.anyDouble())).thenReturn(new byte[0]);

        billCommand = new BillCommand(customerService, billService, qrBillService);

        List<String> args = new ArrayList<>();
        args.add("-c");
        args.add(customerId.toString());
        args.add("-r");
        args.add("2025-07-08,100,1.5");
        args.add("-o");
        args.add("bill.pdf");
        args.add("-acc");
        args.add("CH12345678901234567890");
        if (reference != null) {
            args.add("-ref");
            args.add(reference);
        }
        args.add("-msg");
        args.add("Test Message");

        new CommandLine(billCommand).parseArgs(args.toArray(new String[0]));

        // Act
        billCommand.call();

        // Assert
        Mockito.verify(customerService).getCustomerById(customerId.toString());
        Mockito.verify(qrBillService).generateQrBillPng(ArgumentMatchers.any(), ArgumentMatchers.any(), expectedReferenceInServiceCall, ArgumentMatchers.any(), ArgumentMatchers.anyDouble());
        Mockito.verify(billService).createPdf(ArgumentMatchers.any(Bill.class), ArgumentMatchers.any(Path.class));
    }
}
