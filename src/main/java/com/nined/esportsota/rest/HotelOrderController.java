package com.nined.esportsota.rest;

import com.nined.esportsota.domain.HotelOrder;
import com.nined.esportsota.exception.BadRequestException;
import com.nined.esportsota.service.HotelOrderService;
import com.nined.esportsota.service.PayService;
import com.nined.esportsota.service.RedisService;
import com.nined.esportsota.service.criteria.HotelOrderQueryCriteria;
import com.nined.esportsota.service.dto.HotelOrderDTO;
import com.nined.esportsota.service.impl.HotelOrderServiceImpl;
import com.nined.esportsota.service.vo.HotelOrderVO;
import com.nined.esportsota.utils.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/hotelOrder")
public class HotelOrderController {

    @Autowired
    private HotelOrderService hotelOrderService;
    @Autowired
    private RedisService redisService;

    @Autowired
    private PayService payService;

    private static Logger logger= LoggerFactory.getLogger(HotelOrderController.class);

    @GetMapping(value = "/page")
    public ResponseEntity<Object> queryPage( @RequestHeader("token") String token,HotelOrderQueryCriteria criteria, Pageable pageable) {
        boolean b =TokenUtils.verifyToken(token);
        if (!b){
            throw new BadRequestException(HttpStatus.valueOf(401),"token失效");
        }
        if (StringUtils.isEmpty(criteria.getUserId())){
            throw new BadRequestException("参数异常");
        }
        Map map=new HashMap();
        criteria.setPayStatus(1);
        map.put("data",hotelOrderService.queryAll(criteria,pageable));
        map.put("status",HttpStatus.OK.value());
        map.put("message","成功");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@Validated @RequestBody HotelOrderVO hotelOrderVO, @RequestHeader("token") String token) throws Exception{
        boolean b =TokenUtils.verifyToken(token);
        if (!b){
            throw new BadRequestException(HttpStatus.valueOf(401),"token失效");
        }
        HotelOrderDTO hotelOrderDTO=hotelOrderService.create(hotelOrderVO);
        Map<String, String> resMap = payService.xcxPayment(hotelOrderDTO.getOrderId(),hotelOrderDTO.getActualAmount(),hotelOrderVO.getOpenid(),hotelOrderVO.getRoomTypeName());
        if("SUCCESS".equals(resMap.get("returnCode")) && "OK".equals(resMap.get("returnMsg"))){
            //统一下单成功
            resMap.remove("returnCode");
            resMap.remove("returnMsg");
            logger.info("【小程序支付服务】支付下单成功！");
        }else{
            logger.info("【小程序支付服务】支付下单失败！原因:"+resMap.get("returnMsg"));
            throw new BadRequestException(resMap.get("returnMsg"));
        }
        Map map=new HashMap();
        map.put("data",resMap);
        map.put("status",HttpStatus.OK.value());
        map.put("message","成功");
        return new ResponseEntity<>(map,HttpStatus.CREATED);
    }

    @GetMapping(value = "/detail")
    public ResponseEntity<Object> detail( @RequestHeader("token") String token,String orderId,Integer userId) {
        boolean b =TokenUtils.verifyToken(token);
        if (!b){
            throw new BadRequestException(HttpStatus.valueOf(401),"token失效");
        }
        if (StringUtils.isEmpty(orderId)||StringUtils.isEmpty(userId)){
            throw new BadRequestException("参数异常");
        }
        Map map=new HashMap();
        map.put("data",hotelOrderService.findByOrderIdAndUserId(orderId,userId));
        map.put("status",HttpStatus.OK.value());
        map.put("message","成功");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping(value = "/cancel")
    public ResponseEntity<Object> cancel( @RequestHeader("token") String token,String orderId,Integer userId) {
        boolean b =TokenUtils.verifyToken(token);
        if (!b){
            throw new BadRequestException(HttpStatus.valueOf(401),"token失效");
        }
        if (StringUtils.isEmpty(orderId)||StringUtils.isEmpty(userId)){
            throw new BadRequestException("参数异常");
        }
        Map map=new HashMap();
        map.put("data",hotelOrderService.cancelOrder(orderId,userId));
        map.put("status",HttpStatus.OK.value());
        map.put("message","成功");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}