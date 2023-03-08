package com.trantor.bill.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BillDto {
    private Long loanId;
    private Double amount;
    private LocalDate dueDate;

    private String status;

}
