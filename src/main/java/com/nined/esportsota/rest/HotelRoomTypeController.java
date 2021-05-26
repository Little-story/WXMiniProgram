package com.nined.esportsota.rest;

import com.nined.esportsota.service.HotelRoomTypeService;
import com.nined.esportsota.service.ShopService;
import com.nined.esportsota.service.criteria.HotelRoomTypeQueryCriteria;
import com.nined.esportsota.service.criteria.ShopQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/hotelRoomType")
public class HotelRoomTypeController {

    @Autowired
    private HotelRoomTypeService hotelRoomTypeService;


    @GetMapping(value = "/page")
    public ResponseEntity<Object> query(HotelRoomTypeQueryCriteria criteria, Pageable pageable) {
        Map map=new HashMap();
        map.put("data",hotelRoomTypeService.queryAll(criteria,pageable));
        map.put("status",HttpStatus.OK.value());
        map.put("message","成功");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}