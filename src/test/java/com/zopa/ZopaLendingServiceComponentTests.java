package com.zopa;

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
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class ZopaLendingServiceComponentTests {

    IParser<Lender> parser;
    ILoanValidator<Lender> loanValidator;
    IQuoteCalculator<Quote, Lender> quoteCalculator;

    @BeforeEach
    public void setup() {
        parser = new CSVParser();
        loanValidator = new DefaultLoanValidator();
        quoteCalculator = new DefaultQuoteCalculator(loanValidator);
    }

    @Test
    public void givenLendersAndRequestedLoan_WhenLoanCanBeProvided_ReturnQuote() throws InputFileParseException, QuoteNotPossibleException {
        // Arrange
        List<Lender> lenders = parser.getLenders("src/test/resources/input/lenders-test1.csv");
        // Act
        Quote quote = quoteCalculator.getQuote(lenders, 1000L);
        //log.info("Quote {}", quote.toString());
        // Assert
        assertThat(new BigDecimal("1115.64").setScale(2), Matchers.comparesEqualTo(quote.getTotalRepayment()));
    }

    @Test
    public void givenLendersAndRequestedLoan_WhenLoanCanNotBeProvided_ThrowsQuoteNotPossibleExcetion() throws InputFileParseException {
        // Act
        List<Lender> lenders = parser.getLenders("src/test/resources/input/lenders-test1.csv");
        // Assert
        assertThrows(QuoteNotPossibleException.class, () -> quoteCalculator.getQuote(lenders, 1700L));
    }

    @Test
    public void givenBigLendersListAndRequestedLoan_WhenLoanCanBeProvided_ReturnsQuote() throws InputFileParseException,QuoteNotPossibleException {
        // Act
        List<Lender> lenders = parser.getLenders("src/test/resources/input/lenders-test2.csv");
        Quote quote = quoteCalculator.getQuote(lenders, 1700L);
        //log.info("Quote {}", quote.toString());
        // Assert
        assertThat(new BigDecimal("1902.60").setScale(2), Matchers.comparesEqualTo(quote.getTotalRepayment()));
    }


    // Negative Test cases

    @Test
    public void givenNullLendersAndGivenRequestedLendingAmount_WhenRequestedLendingAmountCanNotBeProvided_ReturnsQuoteNotPossibleException() {
        // Act
        QuoteNotPossibleException quoteNotPossibleExceptionThrown = Assertions
                .assertThrows(QuoteNotPossibleException.class, () -> quoteCalculator.getQuote(null, 1000L));
        // Assert
        Assertions.assertTrue(quoteNotPossibleExceptionThrown.getMessage().contains("There are no Lenders available. It is not Possible to provide a quote"));
    }

    @Test
    public void givenNullLendersAndGivenRequestedLendingAmountToZero_WhenRequestedLendingAmountCanBeProvided_ReturnsQuoteNotPossibleException() {
        // Act
        QuoteNotPossibleException quoteNotPossibleExceptionThrown = Assertions
                .assertThrows(QuoteNotPossibleException.class, () -> quoteCalculator.getQuote(null, 0L));
        // Assert
        Assertions.assertTrue(quoteNotPossibleExceptionThrown.getMessage().contains("There are no Lenders available. It is not Possible to provide a quote"));
    }

    @Test
    public void givenListOfLendersAndGivenRequestedLendingAmountAsNegative_WhenRequestedLendingAmountCanNotBeProvided_ReturnsQuoteNotPossibleException() throws InputFileParseException {
        // Act
        List<Lender> lenders = parser.getLenders("src/test/resources/input/lenders-test2.csv");
        QuoteNotPossibleException quoteNotPossibleExceptionThrown = Assertions
                .assertThrows(QuoteNotPossibleException.class, () -> quoteCalculator.getQuote(lenders, -3L));
        // Assert
        Assertions.assertTrue(quoteNotPossibleExceptionThrown.getMessage().contains("A quote may be requested in any GBP 100 increment between GBP 1000 and GBP 15000 inclusive"));
    }

    @Test
    public void givenListOfLendersEmptyAndGivenRequestedLendingAmount_WhenRequestedLendingAmountCanNotBeProvided_ReturnsQuoteNotPossibleException() {
        // Act
        QuoteNotPossibleException quoteNotPossibleExceptionThrown = Assertions
                .assertThrows(QuoteNotPossibleException.class, () -> quoteCalculator.getQuote(new ArrayList<>(), 1000L));
        // Assert
        Assertions.assertTrue(quoteNotPossibleExceptionThrown.getMessage().contains("There are no Lenders available. It is not Possible to provide a quote"));
    }

}
