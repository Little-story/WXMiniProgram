package com.nined.esportsota.rest;

import com.nined.esportsota.exception.BadRequestException;
import com.nined.esportsota.service.HotelRoomService;
import com.nined.esportsota.service.criteria.HotelRoomQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/hotelRoom")
@CrossOrigin
public class HotelRoomController {

    @Autowired
    private HotelRoomService hotelRoomService;


    @GetMapping(value = "/page")
    public ResponseEntity<Object> query(HotelRoomQueryCriteria criteria, Pageable pageable) {
        Map map=new HashMap();
        map.put("data",hotelRoomService.queryAll(criteria,pageable));
        map.put("status",HttpStatus.OK.value());
        map.put("message","成功");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    @GetMapping(value = "/roomNum")
    public ResponseEntity<Object> query(HotelRoomQueryCriteria criteria) {

        if(StringUtils.isEmpty(criteria.getRoomTypeId())||StringUtils.isEmpty(criteria.getBookInDate())||StringUtils.isEmpty(criteria.getBookOutDate())){
            throw new BadRequestException("参数异常");
        }
        Map map=new HashMap();
        map.put("data",hotelRoomService.roomNum(criteria).size());
        map.put("status",HttpStatus.OK.value());
        map.put("message","成功");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}