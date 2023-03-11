package com.batchjob.practice.writer;

import com.batchjob.practice.dto.Bill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

@Slf4j
public class BillGenerator implements ItemWriter<Bill> {
    @Override
    public void write(Chunk<? extends Bill> chunk) throws Exception {
        chunk.getItems()
                .forEach(bill -> {
                    bill.setStatus("Cleared");
                    System.out.println(bill.getId());
                    log.info("Bill {}", bill);
                });

    }
}
