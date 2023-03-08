package com.trantor.bill.model;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Builder
@Table("bill_table")
public class Bill {
    @Id
    private Long id;
    private Long loanId;
    private Double amount;
    private LocalDate dueDate;
    private String status;
}
