package com.nined.esportsota.service.criteria;

import com.nined.esportsota.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class HotelRoomQueryCriteria {

    @Query
    private Integer roomTypeId;

    @Query
    private Integer status;

    @Query(type = Query.Type.NOT_IN)
    private List<Integer> roomStatus;

    private Long bookInDate;

    private Long bookOutDate;

    private Integer roomId;

}
