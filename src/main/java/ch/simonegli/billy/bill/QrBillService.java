package ch.simonegli.billy.bill;

import org.springframework.stereotype.Service;

import ch.simonegli.billy.customer.Customer;
import net.codecrete.qrbill.generator.Address;
import net.codecrete.qrbill.generator.Bill;
import net.codecrete.qrbill.generator.BillFormat;
import net.codecrete.qrbill.generator.GraphicsFormat;
import net.codecrete.qrbill.generator.Language;
import net.codecrete.qrbill.generator.OutputSize;
import net.codecrete.qrbill.generator.QRBill;

@Service
public class QrBillService {

    public byte[] generateQrBillPng(Customer customer, String account, double amount) {
        Bill bill = new Bill();
        bill.setAccount(account);
        bill.setAmountFromDouble(amount);
        bill.setCurrency("CHF");

        Address creditor = new Address();
        creditor.setName("Simon Egli");
        creditor.setStreet("Blastrasse");
        creditor.setHouseNo("6");
        creditor.setPostalCode("8000");
        creditor.setTown("Zürich");
        creditor.setCountryCode("CH");
        bill.setCreditor(creditor);

        Address debtor = new Address();
        debtor.setName(customer.getName());
        debtor.setStreet(customer.getStreet());
        debtor.setHouseNo(customer.getHouseNo());
        debtor.setPostalCode(customer.getPostalCode());
        debtor.setTown(customer.getTown());
        debtor.setCountryCode(customer.getCountryCode());
        bill.setDebtor(debtor);

        BillFormat format = new BillFormat();
        format.setGraphicsFormat(GraphicsFormat.PNG);
        format.setOutputSize(OutputSize.QR_BILL_ONLY);
        format.setLanguage(Language.DE);
        bill.setFormat(format);

        return QRBill.generate(bill);
    }
}
