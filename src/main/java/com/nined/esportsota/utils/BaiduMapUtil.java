package com.nined.esportsota.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class BaiduMapUtil {

    public static final String AK="umCHDUVT55ItrObIOKkzgXUO2OUSxmBD";

    /**
     * 正地理编码
     * @param address
     * @return
     */
    public static JSONObject getLocation(String address){
        String url= MessageFormat.format("http://api.map.baidu.com/geocoding/v3/?address={0}&output=json&ak={1}&callback=showLocation",
                address,AK);
        String res=HttpUtil.httpRequest(url,"GET");
        res=res.replace(")","");
        res=res.replace("(","");
        res=res.replaceAll("showLocation&&showLocation","");
        System.out.println(res);
        JSONObject object= JSON.parseObject(res);
        JSONObject resObject=new JSONObject();
        resObject.put("lng",object.getJSONObject("result").getJSONObject("location").getString("lng"));
        resObject.put("lat",object.getJSONObject("result").getJSONObject("location").getString("lat"));
        return resObject;
    }

    /**
     * 轻量级路线规划
     * @param origin    起点经纬度，格式为：纬度,经度；小数点后不超过6位，40.056878,116.30815
     * @param destination   终点经纬度，格式为：纬度,经度；小数点后不超过6位，40.056878,116.30815
     * @return
     */
    public static JSONObject driving(String origin,String destination){
        //origin=subLocation(origin);
        //destination=subLocation(destination);
        String url=MessageFormat.format("http://api.map.baidu.com/directionlite/v1/walking?origin={0}&destination={1}&ak={2}",
                origin,destination,AK);
        String res= cn.hutool.http.HttpUtil.get(url);
        return JSON.parseObject(res);
    }

    /**
     * 把经纬度截取为小数点后六位
     * @param location
     * @return
     */
    public static String subLocation(String location){
        StringBuilder sb=new StringBuilder();
        String[] arr=location.split(",");
        for (int i=0;i<arr.length;i++){
            int point=arr[i].indexOf(".")+1;
            if (i==arr.length-1){
                sb.append(arr[i].substring(0,point+6));
            }else {
                sb.append(arr[i].substring(0,point+6));
                sb.append(",");
            }
        }
        return sb.toString();
    }

}
