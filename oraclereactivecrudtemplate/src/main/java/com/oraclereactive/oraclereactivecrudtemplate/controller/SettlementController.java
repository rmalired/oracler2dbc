package com.oraclereactive.oraclereactivecrudtemplate.controller;

import com.oraclereactive.oraclereactivecrudtemplate.entity.Settlement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Update;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.data.relational.core.query.Criteria.where;

@RestController
@Slf4j
@RequestMapping("settlement")
public class SettlementController {

    @Autowired
    private R2dbcEntityTemplate template;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Settlement>> createSettlement(@RequestBody Settlement receivedSettlement){
        return template.insert(new Settlement(receivedSettlement.getReferenceId(),
                        receivedSettlement.getMerchantId(), receivedSettlement.getAmount()))
                .map(ResponseEntity::ok);
    }


    @GetMapping(path = "/merchant/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<Settlement> getSettlementsByMerchant(@PathVariable("id") String merchantId){
        return template.select(Settlement.class).matching(Query.query(where("merchant_id").is(merchantId))).all();

    }


    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Settlement> updateSettlementAmount(@RequestBody Settlement updateSettlement){
       Mono<Integer> recordsUpdated =  template.
               update(Query.query(where("reference_id").is(updateSettlement.getReferenceId())),
                       Update.update("amount",updateSettlement.getAmount()),Settlement.class);

      // log.info("records updated "+ recordsUpdated.block());

       //return the input object
        return Flux.fromIterable(List.of(updateSettlement));
    }
}
