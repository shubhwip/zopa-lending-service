package com.zopa.quote;

import com.zopa.exception.QuoteNotPossibleException;

import java.util.List;

@FunctionalInterface
public interface ILoanValidator<L> {
    abstract boolean validateLoanProvision(List<L> lenders, long loanAmount) throws QuoteNotPossibleException;
}
