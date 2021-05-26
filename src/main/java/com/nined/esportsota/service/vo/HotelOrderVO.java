package com.nined.esportsota.service.vo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class HotelOrderVO {
    private Integer userId;

    private Integer shopId;

    private Integer roomTypeId;

    private Timestamp bookInDate;

    private Timestamp bookOutDate;

    private Double actualAmount;

    private Integer payMethod;

    private Integer source;

    private String orderUserMobile;

    private List<String> orderUserList;

    private Integer roomNum;

    private String openid;

    private String roomTypeName;
}
