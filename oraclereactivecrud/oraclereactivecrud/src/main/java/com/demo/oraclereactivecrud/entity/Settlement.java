package com.demo.oraclereactivecrud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@Getter
public class Settlement {

    @Id
    private String referenceId;

    private String merchantId;
    private BigDecimal amount;



}
