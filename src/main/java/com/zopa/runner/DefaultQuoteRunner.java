package com.zopa.runner;

import com.zopa.exception.InputFileParseException;
import com.zopa.exception.QuoteNotPossibleException;
import com.zopa.model.Lender;
import com.zopa.model.Quote;
import com.zopa.parser.CSVParser;
import com.zopa.parser.IParser;
import com.zopa.quote.DefaultLoanValidator;
import com.zopa.quote.DefaultQuoteCalculator;
import com.zopa.quote.ILoanValidator;
import com.zopa.quote.IQuoteCalculator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Slf4j
public class DefaultQuoteRunner implements IQuoteRunner {

    private final String fileName;
    private final BigDecimal loanAmount;

    @Override
    public void run() {
        try {
            IParser<Lender> parser = new CSVParser();
            List<Lender> lenderList = parser.getLenders(fileName);
            ILoanValidator<Lender> loanValidator = new DefaultLoanValidator();
            IQuoteCalculator<Quote, Lender> quoteCalculator = new DefaultQuoteCalculator(loanValidator);
            Quote quote = quoteCalculator.getQuote(lenderList, loanAmount);
            log.info("{}", quote.toString());
        } catch (InputFileParseException e) {
            log.error("Error occurred in parsing file, {}", e.getMessage());
        } catch (QuoteNotPossibleException e) {
            log.error("QuoteNotPossibleException, {}", e.getMessage());
        }
    }
}
