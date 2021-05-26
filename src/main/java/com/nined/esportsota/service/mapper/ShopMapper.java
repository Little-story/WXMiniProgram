package com.nined.esportsota.service.mapper;

import com.nined.esportsota.domain.Shop;
import com.nined.esportsota.service.dto.ShopDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShopMapper extends BaseMapper<ShopDTO, Shop> {

}