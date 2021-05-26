package com.nined.esportsota.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name="shop")
public class Shop implements Serializable  {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //类型(1VR门店，2电竞酒店)
    private Integer typeId;

    //门店编号
    private String code;

    //商户id
    private Integer mchId;

    //门店名称
    private String name;

    //门店图片
    private String image;

    //门店图片2
    private String image2;

    //门店图片3
    private String image3;

    //门店图片4
    private String image4;

    //门店图片5
    private String image5;

    //联系人
    private String contacts;

    //手机号
    private String mobile;

    //省
    private String province;

    //市
    private String city;

    //区
    private String district;

    //地址
    private String address;

    //营业时间
    private String hours;

    //标签
    private String tag;

    //座位数
    private Integer seatNum;

    //竞技点
    private Integer rxb;

    //状态(1正常，0异常)
    private Integer status;

    //创建时间
    private Timestamp createTime;

    //面积
    private String area;

    //门店电话
    private String tel;

    //排序
    private Integer sort;

    //认证类型1娱乐赛点2vbox赛点认证
    private Integer approveType;

    //赛事级别
    private Integer matchLevel;

    //营业执照
    private String businessImage;

    //负责人
    private String manager;

    //负责人电话
    private String managerTel;

    //办赛信息描述
    private String matchRemark;

    //vbox设备数量
    private Integer vboxNum;

    //认证图片1
    private String approveImage1;

    //认证图片2
    private String approveImage2;

    //认证图片3
    private String approveImage3;

    //认证图片4
    private String approveImage4;

    //认证图片5
    private String approveImage5;

    //认证状态1认证中2通过99不通过
    private Integer approveStatus;

    //星级品质
    private Integer star;

    //装修年份
    private Integer fitupYear;

    //开业年份
    private Integer openYear;

    //一句话简介
    private String minDescribe;

    //介绍
    private String describe;

    //房型数量
    private Integer roomTypeNum;

    //房间数量
    private Integer roomNum;

    //审核状态1待审核2审核通过99不通过
    private Integer reviewStatus;

    //经度
    private String longitude;

    //纬度
    private String latitude;

    //首页补充信息
    @Transient
    private Double amount;

    @Transient
    private String roomTypeName;

    @Transient
    private String bedTypeName;
}
