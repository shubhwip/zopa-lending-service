package com.zopa.model;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Lender {
    @NonNull
    private String name;
    @NonNull
    private BigDecimal rate;
    @NonNull
    private BigDecimal available;
}
