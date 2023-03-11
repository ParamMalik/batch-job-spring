package com.batchjob.practice;

import com.batchjob.practice.dto.ReaderDto;
import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class BillMapper implements RowMapper<ReaderDto> {
    @Override
    public ReaderDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ReaderDto.builder()
                .id(rs.getLong("id"))
                .loanId(rs.getLong("loan_id"))
                .build();
    }
}
