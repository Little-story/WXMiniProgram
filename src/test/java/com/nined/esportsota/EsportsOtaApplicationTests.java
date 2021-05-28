package com.nined.esportsota;

import com.alibaba.fastjson.JSONObject;
import com.nined.esportsota.repository.ShopRepository;
import com.nined.esportsota.utils.BaiduMapUtil;
import com.nined.esportsota.utils.LocalUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EsportsOtaApplicationTests {

    @Autowired
    private ShopRepository shopRepository;

    @Test
    void contextLoads(){
        String origin="113.323552,23.105838";
        String destination="113.377626,23.118942";
        JSONObject object=BaiduMapUtil.driving(origin,destination);
        System.out.println(object.toJSONString());

        /*String address="广东省广州市海珠区体育中心地铁站A出口";
        JSONObject object=BaiduMapUtil.getLocation(address);
        System.out.println(object.getString("lng"));
        System.out.println(object.getString("lat"));*/
    }

}
