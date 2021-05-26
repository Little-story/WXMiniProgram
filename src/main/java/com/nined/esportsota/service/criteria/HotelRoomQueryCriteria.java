package com.nined.esportsota.service.criteria;

import com.nined.esportsota.annotation.Query;
import lombok.Data;

@Data
public class HotelRoomQueryCriteria {

    @Query
    private Integer roomTypeId;
}
