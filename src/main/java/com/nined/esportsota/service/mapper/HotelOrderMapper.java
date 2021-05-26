package com.nined.esportsota.service.mapper;

import com.nined.esportsota.domain.HotelOrder;
import com.nined.esportsota.service.dto.HotelOrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelOrderMapper extends BaseMapper<HotelOrderDTO, HotelOrder> {

}