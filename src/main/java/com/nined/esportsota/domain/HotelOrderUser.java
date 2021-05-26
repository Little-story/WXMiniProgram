package com.nined.esportsota.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name="hotel_order_user")
public class HotelOrderUser implements Serializable  {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //商户id
    private Integer merchantId;

    //门店id
    private Integer shopId;

    //用户编号
    private Integer code;

    //用户姓名
    private String name;

    //用户昵称
    private String nickName;

    //用户身份证
    private String idCard;

    //用户手机
    private String mobile;

    //订单数量
    private Integer orderNum;

    //支付金额
    private Double payAmount;

    //实际支付金额
    private Double actualPayAmount;
}
