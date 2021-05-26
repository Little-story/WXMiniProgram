package com.nined.esportsota.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nined.esportsota.service.ShopService;
import com.nined.esportsota.service.criteria.ShopQueryCriteria;
import com.nined.esportsota.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/home")
public class HomeController {



    @GetMapping(value = "/getAddress")
    public ResponseEntity<Object> getAddress(String location) {
        String ak="umCHDUVT55ItrObIOKkzgXUO2OUSxmBD";
        String url= MessageFormat.format("http://api.map.baidu.com/reverse_geocoding/v3/?ak={0}&output=json&location={1}&extensions_town=true",
                ak,location);
        String res=HttpUtil.httpRequest(url,"GET");
        Map map=new HashMap();
        map.put("data", JSON.parseObject(res));
        map.put("status",HttpStatus.OK.value());
        map.put("message","成功");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}