package com.oraclecrudreactive.oraclereactivecruddbclient.controller;

import com.oraclecrudreactive.oraclereactivecruddbclient.entity.Settlement;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;

@RestController
@Slf4j
@RequestMapping("settlement")
public class SettlementController {


    @Autowired
    private R2dbcEntityTemplate template;

    public static final BiFunction<Row, RowMetadata, Settlement> SETTLEMENT_MAPPING_FUNCTION = (row, rowMetaData) -> Settlement.builder()
            .referenceId(row.get("reference_id", String.class))
            .merchantId(row.get("merchant_id", String.class))
            .amount(row.get("amount", BigDecimal.class))
            .build();

    @GetMapping(path = "/merchant/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<Settlement> getSettlementsByMerchant(@PathVariable("id") String merchantId){
      return template.getDatabaseClient()
               .sql("select * from settlement where merchant_id =:merchantId")
               .bind("merchantId", merchantId)
               .map(SETTLEMENT_MAPPING_FUNCTION).all();

    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Settlement> createSettlement(@RequestBody Settlement receivedSettlement){

        return template.getDatabaseClient()
                .sql("insert into settlement (reference_id, merchant_id, amount) values (:refId, :merchId, :amount)")
                .filter( (statement, executeFunction) -> statement.returnGeneratedValues("reference_id","merchant_id","amount").execute())
                .bind("refId", receivedSettlement.getReferenceId())
                .bind("merchId", receivedSettlement.getMerchantId())
                .bind("amount", receivedSettlement.getAmount())
                .fetch()
                .first()
                .map(r -> new Settlement(String.valueOf(r.get("reference_id")), String.valueOf(r.get("merchant_id")), new BigDecimal(String.valueOf(r.get("amount")))));
                //.map(r -> (Settlement)r); it doesn't work as 'r' is a linked map , it won't be casted to settlement


    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Settlement> updateSettlementAmount(@RequestBody Settlement updateSettlement){


        Mono<Integer> noOfRcdsUpdated = this.template.getDatabaseClient().sql("update settlement set amount=:amount where reference_id =:referenceId")
                                        .bind("amount", updateSettlement.getAmount())
                                        .bind("referenceId", updateSettlement.getReferenceId())
                                        .fetch()
                                        .rowsUpdated();

        noOfRcdsUpdated.subscribe(System.out::println);


        return Flux.fromIterable(List.of(updateSettlement));
    }



}
