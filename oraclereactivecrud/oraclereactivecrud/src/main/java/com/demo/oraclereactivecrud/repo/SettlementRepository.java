package com.demo.oraclereactivecrud.repo;

import com.demo.oraclereactivecrud.entity.Settlement;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SettlementRepository extends ReactiveCrudRepository<Settlement,String> {


}
