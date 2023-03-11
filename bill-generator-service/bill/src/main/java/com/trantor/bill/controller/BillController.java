package com.trantor.bill.controller;

import com.trantor.bill.dto.BillDto;
import com.trantor.bill.service.BillService;
import com.trantor.bill.util.AESUtils;
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

//    public static void main(String[] args) {
//        try {
//            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//            keyGenerator.init(128); // 128 bits
//            SecretKey secretKey = keyGenerator.generateKey();
//            byte[] keyBytes = secretKey.getEncoded();
//            System.out.println("AES Key (Base64 encoded): " + Base64.getEncoder().encodeToString(keyBytes));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    private final BillService billService;

    @PostMapping("/bill")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Map<String , Long>> createBill(@RequestBody BillDto billDto){
        String encrypt = AESUtils.encrypt(billDto.getStatus());
        billDto.setStatus(encrypt);
        return billService.createBill(billDto);
    }

    @GetMapping("/{loanId}/bills")
    @ResponseStatus(HttpStatus.OK)
    public Flux<BillDto> getAllBillsForLoan(@PathVariable Long loanId){
        return billService.getAllBillsForLoan(loanId);
    }
}
