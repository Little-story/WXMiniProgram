package com.nined.esportsota;

import com.nined.esportsota.repository.ShopRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EsportsOtaApplicationTests {

    @Autowired
    private ShopRepository shopRepository;

    @Test
    void contextLoads(){
        for(int i=0;i<1000;i++){
            String code= RandomStringUtils.random(4,"0123456789");
            System.out.println(code);
        }

        /*String address="广东省广州市海珠区体育中心地铁站A出口";
        JSONObject object=BaiduMapUtil.getLocation(address);
        System.out.println(object.getString("lng"));
        System.out.println(object.getString("lat"));*/
    }

}
