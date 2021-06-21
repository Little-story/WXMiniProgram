package com.nined.esportsota.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class HotelRoomDTO implements Serializable {

    private Integer id;

    private Integer shopId;
    private String shopName;

    private Integer roomTypeId;

    private String code;
}
