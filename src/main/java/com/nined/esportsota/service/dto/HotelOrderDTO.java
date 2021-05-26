package com.nined.esportsota.service.dto;

import com.nined.esportsota.domain.HotelRoomType;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class HotelOrderDTO implements Serializable {

    private Integer id;

    private String orderId;

    private Integer roomTypeId;

    private Integer shopId;

    private String shopName;

    private Timestamp bookInDate;

    private Timestamp bookOutDate;

    private Timestamp payTime;

    private Double actualAmount;

    private HotelRoomTypeDTO hotelRoomType;

    private String orderUserName;

    private Integer roomNum;

    private Integer userId;

    private String orderUserMobile;

    private Integer payMethod;

    private Timestamp createTime;

    private Integer status;

    public List<String> getOrderUserList() {
        List<String> list=new ArrayList<>();
        if (!StringUtils.isEmpty(list)){
            if (!StringUtils.isEmpty(this.orderUserName)){
                String[] arr=this.orderUserName.split(";");
                list= Arrays.asList(arr);
            }
        }
        return list;
    }

    public Long getBookInTime(){
        long time=0l;
        if (!StringUtils.isEmpty(this.bookInDate)){
            time=this.bookInDate.getTime();
        }
        return time;
    }

    public Long getBookOutTime(){
        long time=0l;
        if (!StringUtils.isEmpty(this.bookOutDate)){
            time=this.bookOutDate.getTime();
        }
        return time;
    }

    public Long getCreateTimes(){
        long time=0l;
        if (!StringUtils.isEmpty(this.createTime)){
            time=this.createTime.getTime();
        }
        return time;
    }
}
