package com.zopa.quote;

import com.zopa.utility.PropertyLoader;

public class QuoteConstants {


    public final static int DEFAULT_LOAN_PERIOD = PropertyLoader.getIntValue("loan.period");
    public final static int DEFAULT_NUMBER_SCALE = PropertyLoader.getIntValue("default.numbers.scale");
    public final static String DEFAULT_LENDERS_FILE_PATH = String.valueOf(PropertyLoader.getStringValue("default.lenders.csv.file"));
    public final static String CURRENCY = PropertyLoader.getStringValue("currency.code");
    public final static String LOAN_AMORTIZATION_PERIOD = PropertyLoader.getStringValue("loan.amortization.period");
    public final static String LOAN_COMPOUNDING_TYPE = PropertyLoader.getStringValue("loan.compounding.type");
    public final static long LOAN_LOWER_REQUEST_LIMIT = PropertyLoader.getLongValue("loan.lower.request.limits");
    public final static long LOAN_UPPER_REQUEST_LIMIT = PropertyLoader.getLongValue("loan.upper.request.limits");
    public final static long LOAN_REQUEST_MULTIPLES = PropertyLoader.getLongValue("loan.request.multiples");
}
