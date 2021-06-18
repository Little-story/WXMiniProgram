package com.nined.esportsota.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="hotel_order")
public class HotelOrder implements Serializable  {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //订单id
    private String orderId;

    //商户id
    private Integer merchantId;

    //门店id
    private Integer shopId;

    //房型id
    private Integer roomTypeId;

    //房间id
    private Integer roomId;

    //总房费
    private Double amount;

    //支付金额
    private Double payAmount;

    //优惠金额
    private Double discountAmount;

    //实际支付
    private Double actualAmount;

    //预定入住日期
    private Timestamp bookInDate;

    //预定离店日期
    private Timestamp bookOutDate;

    //订单来源
    private Integer source;

    //入住日期
    private Timestamp checkInDate;

    //离店日期
    private Timestamp checkOutDate;

    //来源订单编号
    private String sourceOrderId;

    //客户用户编号
    private Integer orderUserCode;

    //预订人姓名
    private String orderUserName;

    //预订人身份证
    private String orderUserIdCard;

    //预订人手机号
    private String orderUserMobile;

    //备注
    private String remark;

    //订单支付状态(1已支付，0未支付)
    private Integer payStatus;

    //支付方式(1现金，2微信，3支付宝，4线下支付)
    private Integer payMethod;

    //支付凭证号
    private String payReceipts;

    //支付时间
    private Timestamp payTime;

    //入住房间id
    private Integer checkInRoomId;

    //入住人数
    private Integer checkInNum;

    //状态(1待付款，2预定中，3入住中，50已完成，99已取消)
    private Integer status;

    //创建人
    private String createUser;

    //创建时间
    private Timestamp createTime;

    @Transient
    private HotelRoomType hotelRoomType;

    @Transient
    private String shopName;

    //房间数量
    private Integer roomNum;

    //用户id
    private Integer userId;

    //房间号列表
    private String roomList;

    //是否退款
    private Boolean isRefund;
}
