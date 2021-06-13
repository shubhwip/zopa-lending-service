package com.zopa.quote;

import com.zopa.exception.QuoteNotPossibleException;
import com.zopa.model.Lender;
import com.zopa.model.Quote;

import java.math.BigDecimal;
import java.util.List;

public interface IQuoteCalculator<Q extends Quote, L extends Lender> {
    public abstract Q getQuote(List<L> lenders, long loanAmount) throws QuoteNotPossibleException;

    public abstract List<L> selectLenders(List<L> lenders, BigDecimal loanAmount);
}
