package com.nined.esportsota.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class BaiduMapUtil {

    public static final String AK="umCHDUVT55ItrObIOKkzgXUO2OUSxmBD";

    public static List<String> getLocation(String address){
        String url= MessageFormat.format("http://api.map.baidu.com/geocoding/v3/?address={0}&output=json&ak={1}&callback=showLocation",
                address,AK);
        String res=HttpUtil.httpRequest(url,"GET");
        JSONObject object= JSON.parseObject(res);
        List<String> list=new ArrayList<>();
        list.add(object.getJSONObject("result").getJSONObject("location").getString("lng"));
        list.add(object.getJSONObject("result").getJSONObject("location").getString("lat"));
        return list;
    }




}
