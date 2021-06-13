package com.zopa.parser;

import com.zopa.exception.InputFileParseException;
import com.zopa.model.Lender;

import java.util.List;

public interface IParser<L extends Lender> {
    abstract List<L> getLenders(String filename) throws InputFileParseException;
}
