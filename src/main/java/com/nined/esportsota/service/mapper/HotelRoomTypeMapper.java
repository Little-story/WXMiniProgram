package com.nined.esportsota.service.mapper;

import com.nined.esportsota.domain.HotelRoomType;
import com.nined.esportsota.service.dto.HotelRoomTypeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelRoomTypeMapper extends BaseMapper<HotelRoomTypeDTO, HotelRoomType> {

}