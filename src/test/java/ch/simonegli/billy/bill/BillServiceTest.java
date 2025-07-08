package ch.simonegli.billy.bill;

import ch.simonegli.billy.customer.Customer;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;

class BillServiceTest {

    @Test
    void givenBill_whenCreatePdf_thenPdfContainsCorrectData() throws IOException {
        // Arrange
        Customer customer = new Customer();
        customer.setName("Test Customer");
        customer.setStreet("Test Street");
        customer.setHouseNo("123");
        customer.setPostalCode("12345");
        customer.setTown("Test Town");
        Ride ride = new Ride(LocalDate.now(), new BigDecimal("100"), new BigDecimal("1.5"));
        Bill bill = new Bill(customer, List.of(ride));
        Path outputPath = Files.createTempFile("bill", ".pdf");
        BillService billService = new BillService();

        // Act
        billService.createPdf(bill, outputPath);

        // Assert
        try (PDDocument document = Loader.loadPDF(outputPath.toFile())) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            Assertions.assertTrue(text.contains("Test Customer"));
            Assertions.assertTrue(text.contains("Test Street 123"));
            Assertions.assertTrue(text.contains("12345 Test Town"));
            Assertions.assertTrue(text.contains("100,00"));
            Assertions.assertTrue(text.contains("1,50"));
            Assertions.assertTrue(text.contains("150,00"));
            Assertions.assertTrue(text.contains("Gesamttotal: 150,00"));
        }
    }
}
