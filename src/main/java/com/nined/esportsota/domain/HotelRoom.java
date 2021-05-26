package com.nined.esportsota.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name="hotel_room")
public class HotelRoom implements Serializable  {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //商户id
    private Integer merchantId;

    //门店id
    private Integer shopId;

    //房间编号
    private String code;

    //楼层
    private Integer floor;

    //房型id
    private Integer roomTypeId;

    //备注
    private String remark;

    //排序
    private Integer sort;

    //房间状态(1待预订，2已预订，3入住中，4待退房，5待打扫，99有故障)
    private Integer roomStatus;

    //故障前房间状态
    private Integer faultRoomStatus;

    //状态(0停用，1启用)
    private Integer status;

    //创建时间
    private Timestamp createTime;
}
