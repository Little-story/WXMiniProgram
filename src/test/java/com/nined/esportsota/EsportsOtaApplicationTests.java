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
        shopRepository.updateLocation(24,"111","222");
    }

}
