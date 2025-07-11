package ch.simonegli.billy.bill;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class BillService {
    
    private final TemplateEngine templateEngine;
    
    public BillService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }
    
    public void createPdf(Bill bill, Path path) throws IOException {
        Context context = new Context();
        context.setVariable("bill", bill);
        
        if (bill.getQrBillPng() != null) {
            String base64QrCode = java.util.Base64.getEncoder().encodeToString(bill.getQrBillPng());
            context.setVariable("qrBillBase64", base64QrCode);
        }
        
        String html = templateEngine.process("bill-template", context);
        
        try (OutputStream os = Files.newOutputStream(path)) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(html, path.toUri().toString());
            builder.toStream(os);
            builder.run();
        }
    }
}