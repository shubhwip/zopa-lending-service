package com.zopa.runner;

import com.zopa.quote.QuoteConstants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Runner {

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            log.error("Please request some loan amount");
            throw new IllegalArgumentException("Please provide correct arguments");
        }
        long loanAmount = 0L;
        String defaultLendersFilePath = QuoteConstants.DEFAULT_LENDERS_FILE_PATH;
        try {
            if (args.length == 1) {
                loanAmount = Long.valueOf(Integer.parseInt(args[0]));
            } else if (args.length == 2) {
                loanAmount = Long.valueOf(Integer.parseInt(args[0]));
                if (args[1] != null && !args[1].isEmpty()) {
                    defaultLendersFilePath = args[1];
                }
            }
        } catch (Exception e) {
            log.error("Exception occured in parsing arguments passed to program {}", e);
        }
        IQuoteRunner quoteRunner = new DefaultQuoteRunner(defaultLendersFilePath, loanAmount);
        quoteRunner.run();
    }
}
