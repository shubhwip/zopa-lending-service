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

import java.util.List;

@AllArgsConstructor
@Slf4j
public class DefaultQuoteRunner implements IQuoteRunner {

    private String fileName;
    private Long loanAmount;

    @Override
    public void run() {
        try {
            IParser parser = new CSVParser();
            List<Lender> lenderList = parser.getLenders(fileName);
            ILoanValidator loanValidator = new DefaultLoanValidator();
            IQuoteCalculator quoteCalculator = new DefaultQuoteCalculator(loanValidator);
            Quote quote = quoteCalculator.getQuote(lenderList, loanAmount);
            log.info("{}", quote.toString());
        } catch (InputFileParseException e) {
            log.error("Error occured in parsing file, {}", e.getStackTrace());
        } catch (QuoteNotPossibleException e) {
            log.error("QuoteNotPossibleException, {}", e.getMessage());
        }
    }
}
