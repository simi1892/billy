package ch.simonegli.billy.bill;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class BillService {

    public void createPdf(Bill bill, Path path) throws IOException {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

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
