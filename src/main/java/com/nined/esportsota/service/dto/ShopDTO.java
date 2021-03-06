package com.nined.esportsota.service.dto;

import com.nined.esportsota.utils.LocalUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
public class ShopDTO implements Serializable {

    private Integer id;

    private String name;

    private String province;

    private String city;

    private String district;

    private String address;

    private String tel;

    private String tag;

    private Integer openYear;

    private Integer fitupYear;

    private String minDescribe;

    private String describe;

    private String longitude;

    private String latitude;

    private String image;

    //门店图片2
    private String image2;

    //门店图片3
    private String image3;

    //门店图片4
    private String image4;

    //门店图片5
    private String image5;

    private Double amount;

    private String roomTypeName;

    private String bedTypeName;

    private Integer roomNum;

    private Double originalPrice;

    public List<String> getImageList(){
        List<String> imageList=new ArrayList<>();
        if (!StringUtils.isEmpty(this.image)){
            imageList.add(LocalUtil.getLocalImage(this.image));
        }
        if (!StringUtils.isEmpty(this.image2)){
            imageList.add(LocalUtil.getLocalImage(this.image2));
        }
        if (!StringUtils.isEmpty(this.image3)){
            imageList.add(LocalUtil.getLocalImage(this.image3));
        }
        if (!StringUtils.isEmpty(this.image4)){
            imageList.add(LocalUtil.getLocalImage(this.image4));
        }
        if (!StringUtils.isEmpty(this.image5)){
            imageList.add(LocalUtil.getLocalImage(this.image5));
        }
        return imageList;
    }

    public List<String> getTagList(){
        List<String> list=new ArrayList<>();
        if (!StringUtils.isEmpty(this.tag)){
            for (String str:this.tag.split(",")){
                list.add(str);
            }
        }
        return list;
    }
}
