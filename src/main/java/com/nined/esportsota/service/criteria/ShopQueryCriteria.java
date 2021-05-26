package com.nined.esportsota.service.criteria;

import com.nined.esportsota.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class ShopQueryCriteria {

    @Query
    private String id;

    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    //经度
    private String longitude;

    //纬度
    private String latitude;


}
