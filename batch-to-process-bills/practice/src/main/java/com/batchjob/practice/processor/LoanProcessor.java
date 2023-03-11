package com.batchjob.practice.processor;

import com.batchjob.practice.dto.Bill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class LoanProcessor implements ItemProcessor<String, Bill> {
//    @Override
//    public String process(ReaderDto readerDto) throws Exception {
//        return Bill.builder()
//                .loanId(123465l)
//                .build();
//    }

    @Override
    public Bill process(String item) throws Exception {
        return Bill.builder()
                .status(item)
                .build();
    }

//    @Override
//    public Bill process(ReaderDto item) throws Exception {
//        log.info("Inside Process: {}", item);
//        return Bill.builder()
//                .loanId(item.getLoanId())
//                .id(item.getId())
//                .build();
//    }
}
