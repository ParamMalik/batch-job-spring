package com.trantor.bill.service.impl;

import com.trantor.bill.dao.BillRepository;
import com.trantor.bill.dto.BillDto;
import com.trantor.bill.mapper.BillMapper;
import com.trantor.bill.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final BillMapper billMapper;

    @Override
    public Mono<Map<String, Long>> createBill(BillDto billDto) {
        return billRepository.save(billMapper.toBill(billDto))
                .map(bill -> Collections.singletonMap("id", bill.getId()));
    }

    @Override
    public Flux<BillDto> getAllBillsForLoan(Long loanId) {
        return billRepository.findAllByLoanId(loanId)
                .map(billMapper::toBillDto);
    }
}
