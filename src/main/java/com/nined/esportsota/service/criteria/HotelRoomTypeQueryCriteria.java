package com.nined.esportsota.service.criteria;

import com.nined.esportsota.annotation.Query;
import lombok.Data;

@Data
public class HotelRoomTypeQueryCriteria {

    @Query
    private Integer shopId;

    @Query
    private Integer status;

    private Integer roomTypeId;
}
