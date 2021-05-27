package com.nined.esportsota.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class BaiduMapUtil {

    public static final String AK="umCHDUVT55ItrObIOKkzgXUO2OUSxmBD";

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

}
