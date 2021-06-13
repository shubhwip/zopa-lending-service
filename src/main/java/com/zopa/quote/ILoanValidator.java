package com.zopa.quote;

import com.zopa.exception.QuoteNotPossibleException;

import java.math.BigDecimal;
import java.util.List;

@FunctionalInterface
public interface ILoanValidator<L> {
    abstract boolean validateLoanProvision(List<L> lenders, BigDecimal loanAmount) throws QuoteNotPossibleException;
}
