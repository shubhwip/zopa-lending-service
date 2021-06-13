package com.zopa.quote;

import com.zopa.exception.QuoteNotPossibleException;
import com.zopa.model.Lender;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
public class DefaultLoanValidator implements ILoanValidator<Lender> {
    @Override
    public boolean validateLoanProvision(List<Lender> lenders, long loanAmount) throws QuoteNotPossibleException {
        if (lenders == null || lenders.isEmpty()) {
            log.error("There are no Lenders available. It is not Possible to provide a quote");
            throw new QuoteNotPossibleException("There are no Lenders available. It is not Possible to provide a quote");
        }
        BigDecimal amount = lenders.stream().map(Lender::getAvailable).reduce(BigDecimal::add)
                .orElseThrow(() -> new QuoteNotPossibleException("Exception Occured in calculating sum for Lenders available amount. It is not possible to provide a quote."));
        if ((loanAmount < QuoteConstants.LOAN_LOWER_REQUEST_LIMIT || loanAmount > QuoteConstants.LOAN_UPPER_REQUEST_LIMIT) || (loanAmount % QuoteConstants.LOAN_REQUEST_MULTIPLES != 0)) {
            log.error("A quote may be requested in any GBP 100 increment between GBP 1000 and GBP 15000 inclusive.");
            log.error("It is not possible to provide a quote.");
            throw new QuoteNotPossibleException("A quote may be requested in any GBP 100 increment between GBP 1000 and GBP 15000 inclusive. It is not possible to provide a quote.");
        } else if (amount.compareTo(new BigDecimal(loanAmount)) == -1) {
            log.error("There are no lenders available currently to serve requested loan amount. It is not possible to provide a quote.");
            throw new QuoteNotPossibleException("There are no lenders available currently to serve requested loan amount. It is not possible to provide a quote.");
        }
        return true;
    }
}
