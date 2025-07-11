package ch.simonegli.billy.bill;

import net.codecrete.qrbill.generator.Address;
import net.codecrete.qrbill.generator.Bill;
import net.codecrete.qrbill.generator.BillFormat;
import net.codecrete.qrbill.generator.GraphicsFormat;
import net.codecrete.qrbill.generator.Language;
import net.codecrete.qrbill.generator.OutputSize;
import net.codecrete.qrbill.generator.QRBill;

public class QrBillService {

    public byte[] generateQrBillPng(ch.simonegli.billy.customer.Customer customer, String account, String reference, String unstructuredMessage, double amount) {
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

        if (reference != null && !reference.isEmpty()) {
            bill.setReference(reference);
        }
        if (unstructuredMessage != null && !unstructuredMessage.isEmpty()) {
            bill.setUnstructuredMessage(unstructuredMessage);
        }

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
