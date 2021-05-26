package com.nined.esportsota.service.criteria;

import com.nined.esportsota.annotation.Query;
import lombok.Data;

@Data
public class HotelOrderQueryCriteria {

    @Query
    private String userId;

    @Query
    private String mobile;

    @Query
    private Integer status;

    @Query
    private Integer payStatus;
}
