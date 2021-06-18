package com.nined.esportsota.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name="hotel_message")
public class HotelMessage implements Serializable  {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //商户id
    private Integer merchantId;

    //酒店id
    private Integer shopid;

    //内容
    private String content;

    //是否已读
    private Integer isRead;

    //创建人
    private String createUser;

    //创建人id
    private Integer createUserId;

    //创建时间
    private Timestamp createTime;
}
