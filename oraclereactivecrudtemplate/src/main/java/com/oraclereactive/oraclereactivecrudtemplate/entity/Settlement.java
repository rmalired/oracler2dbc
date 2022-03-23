package com.oraclereactive.oraclereactivecrudtemplate.entity;

import lombok.*;

@AllArgsConstructor
@Getter
@Data
@ToString
@NoArgsConstructor
public class Settlement {

    private String referenceId;
    private String merchantId;
    private String amount;
}
