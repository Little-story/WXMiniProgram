package com.nined.esportsota.service.mapper;

import com.nined.esportsota.domain.Users;
import com.nined.esportsota.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends BaseMapper<UserDTO, Users> {

}