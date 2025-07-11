package ch.simonegli.billy;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.HashSet;
import java.util.Set;

public class SwissNumberFormattingDialect extends AbstractProcessorDialect {

    public SwissNumberFormattingDialect() {
        super("SwissNumberFormattingDialect", "swiss", StandardDialect.PROCESSOR_PRECEDENCE);
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        Set<IProcessor> processors = new HashSet<>();
        processors.add(new SwissNumberFormatAttributeTagProcessor(dialectPrefix));
        return processors;
    }
}
