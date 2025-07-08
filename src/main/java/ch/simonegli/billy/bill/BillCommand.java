package ch.simonegli.billy.bill;

import ch.simonegli.billy.customer.Customer;
import ch.simonegli.billy.customer.CustomerService;
import picocli.CommandLine;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@CommandLine.Command(name = "bill", description = "Creates a new bill")
public class BillCommand implements Callable<Integer> {

    @CommandLine.Option(names = {"-c", "--customer"}, description = "Customer ID", required = true)
    private UUID customerId;

    @CommandLine.Option(names = {"-r", "--ride"}, description = "Ride details in format 'date,distance,pricePerKm'", required = true)
    private List<String> rides;

    @CommandLine.Option(names = {"-o", "--output"}, description = "Output file path", required = true)
    private Path outputPath;

    private final CustomerService customerService;
    private final BillService billService;

    public BillCommand(CustomerService customerService, BillService billService) {
        this.customerService = customerService;
        this.billService = billService;
    }

    @Override
    public Integer call() throws IOException {
        Optional<Customer> customer = customerService.getCustomerById(customerId.toString());

        if (customer.isEmpty()) {
            System.err.println("Customer not found");
            return 1;
        }

        List<Ride> rideList = rides.stream()
                .map(rideString -> {
                    String[] parts = rideString.split(",");
                    LocalDate date = LocalDate.parse(parts[0]);
                    BigDecimal distance = new BigDecimal(parts[1]);
                    BigDecimal pricePerKm = new BigDecimal(parts[2]);
                    return new Ride(date, distance, pricePerKm);
                })
                .collect(Collectors.toList());

        Bill bill = new Bill(customer.get(), rideList);

        billService.createPdf(bill, outputPath);

        System.out.println("Rechnung erfolgreich erstellt unter " + outputPath);

        return 0;
    }
}
