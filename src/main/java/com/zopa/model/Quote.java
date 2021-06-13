package com.zopa.model;

import com.zopa.quote.QuoteConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Setter
@Getter
public class Quote {
    @NonNull
    private BigDecimal loanAmount;
    @NonNull
    private BigDecimal annualInterestRate;
    @NonNull
    private BigDecimal monthlyRepayment;
    @NonNull
    private BigDecimal totalRepayment;

    @Override
    public String toString() {
        return "\nRequested Amount: " + loanAmount + " " + QuoteConstants.CURRENCY + "\n" +
                "Annual Interest Rate: " + annualInterestRate + "%\n" +
                QuoteConstants.LOAN_COMPOUNDING_TYPE + " Repayment: " + monthlyRepayment + " " + QuoteConstants.CURRENCY + "\n" +
                "Total Repayment: " + totalRepayment + " " + QuoteConstants.CURRENCY;
    }
}
