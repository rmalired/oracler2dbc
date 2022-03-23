package com.oraclecrudreactive.oraclereactivecruddbclient.entity;

import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Settlement {

    private String referenceId;
    private String merchantId;
    private BigDecimal amount;
}
