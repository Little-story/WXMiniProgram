package com.nined.esportsota.service.dto;

import com.nined.esportsota.domain.HotelRoomComputer;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class HotelRoomTypeDTO implements Serializable {

    private Integer id;

    private Integer shopId;
    private String shopName;

    private String name;

    private String image;

    private String roomSize;

    private String facilities;

    private String bedType;

    private Integer maxNum;

    private Integer windowType;

    private Double unitPrice;

    private Integer laveRoom;

    private String label;

    private String mobile;

    private HotelRoomComputer hotelRoomComputer;

    private List<String> imageList;

    private Double originalPrice;

    public Map<String,List<String>> getFacilitiesList() {
        Map<String, List<String>> map = new HashMap<>();
        List<String> amenities = new ArrayList<>();
        List<String> vr = new ArrayList<>();
        List<String> vrGame = new ArrayList<>();
        List<String> showerRoom= new ArrayList<>();
        if (!StringUtils.isEmpty(this.facilities)) {
            String[] facilities = this.facilities.split(",");
            for (String str : facilities) {
                switch (str) {
                    //便利设施
                    case "房间内高速上网":
                        amenities.add(str);
                        break;
                    case "空调":
                        amenities.add(str);
                        break;
                    case "电竞椅":
                        amenities.add(str);
                        break;
                    case "洗衣机":
                        amenities.add(str);
                        break;
                    case "冰箱":
                        amenities.add(str);
                        break;
                    case "电视":
                        amenities.add(str);
                        break;
                    case "有线网络":
                        amenities.add(str);
                        break;
                    case "电脑":
                        amenities.add(str);
                        break;
                    case "茶几":
                        amenities.add(str);
                        break;
                    case "打扫工具":
                        amenities.add(str);
                        break;
                    case "挂烫机":
                        amenities.add(str);
                        break;
                    case "空气净化器":
                        amenities.add(str);
                        break;
                    case "地毯":
                        amenities.add(str);
                        break;
                    //VR设备
                    case "4K高清头显":
                        vr.add(str);
                        break;
                    case "无线蓝牙体感手柄":
                        vr.add(str);
                        break;
                    //VR游戏
                    case "元力崛起":
                        vrGame.add(str);
                        break;
                    case "哨兵·黎明方尖":
                        vrGame.add(str);
                        break;
                    //浴室配套
                    case "独立卫生间":
                        showerRoom.add(str);
                        break;
                    case "24小时热水":
                        showerRoom.add(str);
                        break;
                    case "吹风机":
                        showerRoom.add(str);
                        break;
                    case "洗发水":
                        showerRoom.add(str);
                        break;
                    case "沐浴露":
                        showerRoom.add(str);
                        break;
                    case "毛巾":
                        showerRoom.add(str);
                        break;
                    case "洗漱用品":
                        showerRoom.add(str);
                        break;
                }
            }
        }
        map.put("amenities",amenities);
        map.put("vr",vr);
        map.put("vrGame",vrGame);
        map.put("showerRoom",showerRoom);
        return map;
    }


}
