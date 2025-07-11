package ch.simonegli.billy.bill;

import ch.simonegli.billy.customer.Customer;
import ch.simonegli.billy.customer.CustomerService;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bills")
public class BillController {

    private final BillService billService;
    private final CustomerService customerService;
    private final QrBillService qrBillService;

    @Value("${billy.iban}")
    private String iban;

    @Value("${billy.bill-directory}")
    private String billDirectory;

    public BillController(BillService billService, CustomerService customerService, QrBillService qrBillService) {
        this.billService = billService;
        this.customerService = customerService;
        this.qrBillService = qrBillService;
    }

    @GetMapping("/create")
    public String createBillForm(Model model) {
        BillForm billForm = new BillForm();
        // Initialize with one empty ride for better UX
        billForm.getRides().add(new Ride(null, null, null));

        model.addAttribute("billForm", billForm);
        model.addAttribute("customers", customerService.getCustomers());
        return "create-bill";
    }

    @PostMapping
    public String createBill(@ModelAttribute BillForm billForm) throws IOException {
        // Convert form to Bill entity
        Customer customer = customerService.getCustomerById(billForm.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Customer ID:" + billForm.getCustomerId()));

        Bill bill = new Bill(customer, billForm.getRides());

        // Calculate total amount for QR bill
        double totalAmount = bill.getTotal().doubleValue();

        // Generate QR bill PNG
        byte[] qrBillPng = qrBillService.generateQrBillPng(customer, iban, totalAmount);
        bill.setQrBillPng(qrBillPng);

        // Generate main bill PDF
        billService.createPdf(bill, getAbsoluteFilename(customer.getName()));
        return "redirect:/customers";
    }

    private Path getAbsoluteFilename(String customername) {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = date.format(formatter);
        return Path.of(billDirectory, customername + "_" + formattedDate + ".pdf").toAbsolutePath();
    }
}
