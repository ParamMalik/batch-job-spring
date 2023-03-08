package com.trantor.bill.dao;

import com.trantor.bill.model.Bill;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BillRepository extends ReactiveCrudRepository<Bill, Long> {
    Flux<Bill> findAllByLoanId(Long loanId);
}
