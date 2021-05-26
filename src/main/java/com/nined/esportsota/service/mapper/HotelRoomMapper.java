package com.nined.esportsota.service.mapper;

import com.nined.esportsota.domain.HotelRoom;
import com.nined.esportsota.service.dto.HotelRoomDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelRoomMapper extends BaseMapper<HotelRoomDTO, HotelRoom> {

}