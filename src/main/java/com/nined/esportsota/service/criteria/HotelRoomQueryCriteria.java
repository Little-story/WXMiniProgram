package com.nined.esportsota.service.criteria;

import com.nined.esportsota.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class HotelRoomQueryCriteria {

    @Query
    private Integer roomTypeId;

    @Query
    private Integer status;

    @Query
    private Integer roomStatus;

    private Long bookInDate;

    private Long bookOutDate;

}
