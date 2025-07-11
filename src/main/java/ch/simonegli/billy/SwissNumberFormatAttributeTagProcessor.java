package ch.simonegli.billy;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class SwissNumberFormatAttributeTagProcessor extends AbstractAttributeTagProcessor {

    private static final String ATTR_NAME = "format";
    private static final int PRECEDENCE = 10000;

    public SwissNumberFormatAttributeTagProcessor(final String dialectPrefix) {
        super(
            TemplateMode.HTML,
            dialectPrefix,
            null,
            false,
            ATTR_NAME,
            true,
            PRECEDENCE,
            true);
    }

    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName, String attributeValue, IElementTagStructureHandler structureHandler) {
        final Object result = org.thymeleaf.standard.expression.StandardExpressions.getExpressionParser(context.getConfiguration())
                .parseExpression(context, attributeValue).execute(context);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("de", "CH"));
        symbols.setGroupingSeparator('\'');
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);

        structureHandler.setBody(df.format(result), false);
    }
}
