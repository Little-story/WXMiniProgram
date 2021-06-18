package com.nined.esportsota.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name="hotel_room_type_image")
public class HotelRoomTypeImage implements Serializable  {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //房型id
    private Integer roomTypeId;

    //类型id
    private Integer typeId;

    //url
    private String fileName;
}
