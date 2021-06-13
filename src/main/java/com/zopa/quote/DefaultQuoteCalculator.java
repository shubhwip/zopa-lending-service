package com.zopa.quote;

import com.zopa.exception.QuoteNotPossibleException;
import com.zopa.model.Lender;
import com.zopa.model.Quote;
import com.zopa.rate.LoanCalculator;
import com.zopa.utility.LenderHelper;
import lombok.extern.slf4j.Slf4j;
import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class DefaultQuoteCalculator implements IQuoteCalculator<Quote, Lender> {

    private ILoanValidator loanValidator;

    public DefaultQuoteCalculator(ILoanValidator loanValidator) {
        this.loanValidator = loanValidator;
    }

    @Override
    public Quote getQuote(List<Lender> lenders, long loanAmount) throws QuoteNotPossibleException {
        if (lenders == null || lenders.isEmpty()) {
            log.error("Either lenders are not available or requested loan amount is less than equal to zero");
            throw new QuoteNotPossibleException("Either lenders are not available or requested loan amount is less than equal to zero. It is not possible to provide a quote.");
        }
        loanValidator.validateLoanProvision(lenders, loanAmount);
        BigDecimal loanAmountBd = new BigDecimal(loanAmount);
        List<Lender> selectedLenders = selectLenders(lenders, loanAmountBd);
        BigDecimal avgInterestRate = LenderHelper.getAverageRate(selectedLenders);
        log.debug("Average Interest Rate is {}", avgInterestRate);
        BigDecimal effectiveInterestRate = LoanCalculator.calculate(avgInterestRate, QuoteConstants.DEFAULT_LOAN_PERIOD);
        MonetaryAmount amount = LoanCalculator.calculate(Money.of(loanAmountBd, QuoteConstants.CURRENCY),
                effectiveInterestRate.divide(new BigDecimal(QuoteConstants.LOAN_AMORTIZATION_PERIOD), MathContext.DECIMAL32), QuoteConstants.DEFAULT_LOAN_PERIOD);
        BigDecimal monthlyAmount = new BigDecimal(amount.getNumber().toString()).setScale(QuoteConstants.DEFAULT_NUMBER_SCALE, BigDecimal.ROUND_HALF_EVEN);
        return new Quote(loanAmountBd,
                effectiveInterestRate.multiply(new BigDecimal("100")).setScale(QuoteConstants.DEFAULT_NUMBER_SCALE, BigDecimal.ROUND_HALF_EVEN),
                monthlyAmount,
                monthlyAmount.multiply(BigDecimal.valueOf(QuoteConstants.DEFAULT_LOAN_PERIOD)));
    }

    @Override
    public List<Lender> selectLenders(List<Lender> lenders, BigDecimal loanAmount) {
        // Sorting lenders from lowest to higest rates
        lenders.sort(Comparator.comparing(Lender::getRate));
        List<Lender> selectedLenders = new ArrayList<>(0);
        for (Lender lender : lenders) {
            if (loanAmount.compareTo(lender.getAvailable()) == -1) {
                selectedLenders.add(lender);
                break;
            } else {
                loanAmount = loanAmount.subtract(lender.getAvailable());
                selectedLenders.add(lender);
            }
        }
        return selectedLenders;
    }
}