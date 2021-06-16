package com.zopa.utility;

import com.zopa.model.Lender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LenderHelperTest {

    List<Lender> lenderList = new ArrayList<>();

    @BeforeEach
    public void setup() {
        lenderList.add(new Lender("Jane", new BigDecimal("0.069"), new BigDecimal("480")));
        lenderList.add(new Lender("Fred", new BigDecimal("0.071"), new BigDecimal("520")));
    }

    @Test
    public void givenListOfLendersAndTheirRates_returnAverageOfTheirRates() {
        // Assert
        Assertions.assertEquals(new BigDecimal("0.070").setScale(3, BigDecimal.ROUND_HALF_EVEN), LenderHelper.getAverageRate(lenderList));
    }
}
