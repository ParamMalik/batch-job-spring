package com.batchjob.practice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ReaderDto {
    private Long id;
    private Long loanId;
    private Double amount;

    private LocalDate dueDate;
}
