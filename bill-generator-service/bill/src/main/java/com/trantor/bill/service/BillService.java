package com.trantor.bill.service;

import com.trantor.bill.dto.BillDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface BillService {
    Mono<Map<String , Long>> createBill(BillDto billDto);

    Flux<BillDto> getAllBillsForLoan(Long loanId);

}
