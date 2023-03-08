package com.trantor.bill.mapper;

import com.trantor.bill.dto.BillDto;
import com.trantor.bill.model.Bill;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BillMapper {
    Bill toBill(BillDto billDto);
    BillDto toBillDto(Bill bill);
}
