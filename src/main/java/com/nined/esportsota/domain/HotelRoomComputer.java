package com.nined.esportsota.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 酒店电脑信息
 */
@Entity
@Getter
@Setter
@Table(name="hotel_room_computer")
public class HotelRoomComputer {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //酒店id
    private Integer shopId;

    //房型id
    private Integer roomTypeId;

    //cpu
    private String cpu;

    //显卡
    private String graphics;

    //内存
    private String memory;

    //显示器
    private String monitor;

    //配件
    private String accessories;
}
