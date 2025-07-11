package ch.simonegli.billy.bill;

import ch.simonegli.billy.customer.Customer;
import ch.simonegli.billy.customer.CustomerService;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    public String createBill(@ModelAttribute BillForm billForm) {
        // Convert form to Bill entity
        Customer customer = customerService.getCustomerById(billForm.getCustomerId());

        Bill bill = new Bill(customer, billForm.getRides());

        // Calculate total amount for QR bill
        double totalAmount = bill.getTotal().doubleValue();

        // Generate QR bill PNG
        byte[] qrBillPng = qrBillService.generateQrBillPng(customer, "bla", totalAmount);
        bill.setQrBillPng(qrBillPng);

        // Generate main bill PDF
        try {
        billService.createPdf(bill, getAbsoluteFilename(customer.getName()));
        } catch (IOException e) {
            System.out.println("e");
        }
        return "redirect:/customers";
    }

    private Path getAbsoluteFilename(String customername) {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = date.format(formatter); 
        return Path.of("/Users/simi/Documents/Bus/Rechnungen/" + customername + "_" + formattedDate + ".pdf").toAbsolutePath();
    }
}
