package com.nined.esportsota.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="hotel_room_type")
public class HotelRoomType implements Serializable  {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //商户id
    private Integer merchantId;

    //门店id
    private Integer shopId;

    //房型名称
    private String name;

    //价格
    private Double unitPrice;

    //可住人数
    private Integer maxNum;

    //房间总数
    private Integer maxRoom;

    //封面图片
    private String image;

    //床型
    private String bedType;

    //房间面积
    private String roomSize;

    //窗户(0无窗，1有窗)
    private Integer windowType;

    //房型设施
    private String facilities;

    //房型标签
    private String label;

    //排序
    private Integer sort;

    //状态(1上线中，0已下线)
    private Integer status;

    //创建时间
    private Timestamp createTime;

    //剩余房间数
    private Integer laveRoom;

    //审核状态1待审核2审核通过99不通过
    private Integer reviewStatus;

    //电脑id
    private Integer computerId;

    //原价
    private Double originalPrice;

    @Transient
    private HotelRoomComputer hotelRoomComputer;

    @Transient
    private String mobile;

    @Transient
    private List<String> imageList;

    @Transient
    private String shopName;
}
