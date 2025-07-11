package ch.simonegli.billy.bill;

import ch.simonegli.billy.customer.Customer;
import ch.simonegli.billy.customer.CustomerService;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
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

    @CommandLine.Option(names = {"-acc", "--account"}, description = "Account number for QR bill", required = true)
    private String account;

    @CommandLine.Option(names = {"-ref", "--reference"}, description = "Reference number for QR bill", required = false)
    private String reference;

    @CommandLine.Option(names = {"-msg", "--message"}, description = "Unstructured message for QR bill", required = false)
    private String unstructuredMessage;

    private final CustomerService customerService;
    private final BillService billService;
    private final QrBillService qrBillService;

    public BillCommand(CustomerService customerService, BillService billService, QrBillService qrBillService) {
        this.customerService = customerService;
        this.billService = billService;
        this.qrBillService = qrBillService;
    }

    @Override
    public Integer call() throws IOException {
        Optional<Customer> customer = customerService.getCustomerById(customerId.toString());

        if (customer.isEmpty()) {
            System.err.println("Kunde nicht gefunden: " + customerId);
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
                .toList();

        Bill bill = new Bill(customer.get(), rideList);

        // Calculate total amount for QR bill
        double totalAmount = bill.getTotal().doubleValue();

        // Generate QR bill PNG
        byte[] qrBillPng = qrBillService.generateQrBillPng(customer.get(), account, reference, unstructuredMessage, totalAmount);
        bill.setQrBillPng(qrBillPng);

        // Generate main bill PDF
        billService.createPdf(bill, outputPath);

        System.out.println("Rechnung erfolgreich erstellt unter " + outputPath);

        return 0;
    }
}
