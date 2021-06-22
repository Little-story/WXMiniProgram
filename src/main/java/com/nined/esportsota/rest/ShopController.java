package com.nined.esportsota.rest;

import com.nined.esportsota.service.ShopService;
import com.nined.esportsota.service.criteria.ShopQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/shop")
@CrossOrigin
public class ShopController {

    @Autowired
    private ShopService shopService;


    @GetMapping(value = "/page")
    public ResponseEntity<Object> queryPage(ShopQueryCriteria criteria, Pageable pageable) {
        Map map=new HashMap();
        map.put("data",shopService.queryAll(criteria,pageable));
        map.put("status",HttpStatus.OK.value());
        map.put("message","成功");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping(value = "/detail")
    public ResponseEntity<Object> detail(Integer id) {
        Map map=new HashMap();
        map.put("data",shopService.findById(id));
        map.put("status",HttpStatus.OK.value());
        map.put("message","成功");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}