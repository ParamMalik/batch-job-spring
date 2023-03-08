package com.trantor.bill.controller;

import com.trantor.bill.dto.BillDto;
import com.trantor.bill.service.BillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class BillController {
    private final BillService billService;

    @PostMapping("/bill")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Map<String , Long>> createBill(@RequestBody BillDto billDto){
        return billService.createBill(billDto);
    }

    @GetMapping("/{loanId}/bills")
    @ResponseStatus(HttpStatus.OK)
    public Flux<BillDto> getAllBillsForLoan(@PathVariable Long loanId){
        return billService.getAllBillsForLoan(loanId);
    }
}
