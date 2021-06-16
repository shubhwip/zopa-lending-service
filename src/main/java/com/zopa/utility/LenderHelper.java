package com.zopa.utility;

import com.zopa.model.Lender;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

@Slf4j
public final class LenderHelper {

    public static BigDecimal getAverageRate(final List<Lender> lenders) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Lender lender : lenders) {
            log.debug("Lender Rate inside getAverageRate API, {}", lender.getRate());
            sum = sum.add(lender.getRate(), new MathContext(4));
        }
        return sum.divide(new BigDecimal(lenders.size()), BigDecimal.ROUND_HALF_EVEN);
    }
}
