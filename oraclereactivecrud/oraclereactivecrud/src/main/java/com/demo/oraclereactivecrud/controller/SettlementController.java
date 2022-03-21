package com.demo.oraclereactivecrud.controller;

import com.demo.oraclereactivecrud.entity.Settlement;
import com.demo.oraclereactivecrud.repo.SettlementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("settlement")
@Slf4j
public class SettlementController {


    //no svc class, just for demo purpose repo is injected directly into controller

    private SettlementRepository settlementRepository;

    @Autowired
    private R2dbcEntityTemplate template;

    public SettlementController(SettlementRepository settlementRepository) {
        this.settlementRepository = settlementRepository;
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<Settlement>> getSettlement(@PathVariable("id") String referenceId){
        return settlementRepository
                .findById(referenceId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Settlement>> createSettlement(@RequestBody Settlement receivedSettlement){
        log.info("received input "+ receivedSettlement.toString());
         return template.insert(new Settlement(receivedSettlement.getReferenceId(),
                         receivedSettlement.getMerchantId(), receivedSettlement.getAmount()))
                 .map(ResponseEntity::ok);
    }

}
