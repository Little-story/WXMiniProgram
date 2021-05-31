package com.nined.esportsota.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nined.esportsota.domain.*;
import com.nined.esportsota.exception.BadRequestException;
import com.nined.esportsota.repository.*;
import com.nined.esportsota.rest.HotelOrderController;
import com.nined.esportsota.rest.WXController;
import com.nined.esportsota.service.HotelOrderService;
import com.nined.esportsota.service.PayService;
import com.nined.esportsota.service.criteria.HotelOrderQueryCriteria;
import com.nined.esportsota.service.dto.HotelOrderDTO;
import com.nined.esportsota.service.mapper.HotelOrderMapper;
import com.nined.esportsota.service.mapper.HotelRoomTypeMapper;
import com.nined.esportsota.service.vo.HotelOrderVO;
import com.nined.esportsota.utils.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class HotelOrderServiceImpl implements HotelOrderService {

    @Autowired
    private HotelOrderRepository hotelOrderRepository;
    @Autowired
    private HotelRoomTypeRepository hotelRoomTypeRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HotelRoomRepository hotelRoomRepository;
    @Autowired
    private HotelOrderUserRepository hotelOrderUserRepository;

    private static Logger logger= LoggerFactory.getLogger(HotelOrderServiceImpl.class);

    @Autowired
    private HotelOrderMapper hotelOrderMapper;
    @Autowired
    private HotelRoomTypeMapper hotelRoomTypeMapper;


    @Override
    public Object queryAll(HotelOrderQueryCriteria criteria, Pageable pageable) {
        /*List<JSONObject> list=hotelOrderRepository.findByGroup(criteria.getUserId(),pageable.getPageNumber()* pageable.getPageSize(),pageable.getPageSize());
        List<HotelOrder> orders=new ArrayList<>();
        for (JSONObject object:list){
            HotelOrder hotelOrder=object.toJavaObject(HotelOrder.class);
            HotelOrder detailOrder=getHotelOrder(hotelOrder);
            orders.add(detailOrder);
        }
        Page<HotelOrder> page=new PageImpl<>(orders,pageable,hotelOrderRepository.count());*/
        Page<HotelOrder> page=hotelOrderRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        List<HotelOrder> list=page.getContent();
        if (!StringUtils.isEmpty(list)&&list.size()>0){
            for (HotelOrder hotelOrder:page.getContent()){
                HotelRoomType hotelRoomType=hotelRoomTypeRepository.findById(hotelOrder.getRoomTypeId()).orElse(new HotelRoomType());
                if (!StringUtils.isEmpty(hotelRoomType)){
                    hotelOrder.setHotelRoomType(hotelRoomType);
                }
            }
        }

        Map<String,Object> map= PageUtil.toPage(page.map(hotelOrderMapper::toDto));
        return map;
    }


    /**
     * 补充订单信息
     * @param
     * @return
     */
    /*public HotelOrder getHotelOrder(HotelOrder hotelOrder){
        HotelRoomType hotelRoomType=hotelRoomTypeRepository.findById(hotelOrder.getRoomTypeId()).orElse(new HotelRoomType());
        hotelOrder.setHotelRoomType(hotelRoomType);
        Shop shop=shopRepository.findById(hotelOrder.getShopId()).orElse(new Shop());
        hotelOrder.setShopName(shop.getName());

        List<HotelOrder> list=hotelOrderRepository.findByRoomTypeIdAndOrderId(hotelOrder.getRoomTypeId(),hotelOrder.getOrderId());
        if (!StringUtils.isEmpty(list)&&list.size()>0){
            Double amount=0d;
            List<String> orderUserList=new ArrayList<>();
            for (HotelOrder ho:list){
                orderUserList.add(ho.getOrderUserName());
                if (!StringUtils.isEmpty(ho.getActualAmount())){
                    amount+=ho.getActualAmount();
                }
            }
            hotelOrder.setOrderUserList(orderUserList);
            hotelOrder.setRoomNum(orderUserList.size());
            hotelOrder.setActualAmount(amount);
        }
        return hotelOrder;
    }*/

    @Override
    public HotelOrderDTO create(HotelOrderVO hotelOrderVO){
        logger.info("【创建订单：】"+hotelOrderVO.toString());
        if (StringUtils.isEmpty(hotelOrderVO)){
            throw new BadRequestException("参数异常!");
        }
        if (StringUtils.isEmpty(hotelOrderVO.getShopId())||StringUtils.isEmpty(hotelOrderVO.getRoomTypeId())||StringUtils.isEmpty(hotelOrderVO.getBookInDate())
        ||StringUtils.isEmpty(hotelOrderVO.getBookOutDate())||StringUtils.isEmpty(hotelOrderVO.getOrderUserList())||StringUtils.isEmpty(hotelOrderVO.getActualAmount())
                ||StringUtils.isEmpty(hotelOrderVO.getPayMethod())||StringUtils.isEmpty(hotelOrderVO.getUserId())||StringUtils.isEmpty(hotelOrderVO.getRoomNum())){
            throw new BadRequestException("参数异常!");
        }

        //查询剩余房间
        int roomNum=hotelRoomRepository.countByRoomTypeId(hotelOrderVO.getRoomTypeId());
        if (roomNum==0){
            throw new BadRequestException("已经没有房间可以预定！");
        }else if (hotelOrderVO.getRoomNum()>roomNum){
            throw new BadRequestException("所剩房间已不足以预定！");
        }

        Shop shop=shopRepository.findById(hotelOrderVO.getShopId()).orElse(new Shop());
        Users user=userRepository.findById(hotelOrderVO.getUserId()).orElse(new Users());
        String orderId=getOrderId();
        logger.info("------orderId-------"+orderId);
        if (StringUtils.isEmpty(shop.getId())||StringUtils.isEmpty(user.getId())){
            throw new BadRequestException("参数异常!");
        }
        StringBuilder orderUserList=new StringBuilder();
        if (hotelOrderVO.getOrderUserList().size()>0) {
            List<String> list=hotelOrderVO.getOrderUserList();
            for (int i=0;i<list.size();i++) {
                if (i==list.size()-1){
                    orderUserList.append(list.get(i));
                }else {
                    orderUserList.append(list.get(i)+";");
                }
            }
        }
            //订单信息
            HotelOrder hotelOrder=new HotelOrder();
            hotelOrder.setMerchantId(shop.getMchId());
            hotelOrder.setOrderId(orderId);
            hotelOrder.setShopId(hotelOrderVO.getShopId());
            hotelOrder.setRoomTypeId(hotelOrderVO.getRoomTypeId());
            hotelOrder.setBookInDate(hotelOrderVO.getBookInDate());
            hotelOrder.setBookOutDate(hotelOrderVO.getBookOutDate());
            hotelOrder.setSource(hotelOrderVO.getSource());
            hotelOrder.setOrderUserName(orderUserList.toString());
            hotelOrder.setOrderUserMobile(hotelOrderVO.getOrderUserMobile());
            hotelOrder.setRoomNum(hotelOrderVO.getRoomNum());
            hotelOrder.setPayStatus(0);
            hotelOrder.setPayMethod(hotelOrderVO.getPayMethod());
            hotelOrder.setStatus(0);
            hotelOrder.setAmount(hotelOrderVO.getActualAmount());
            hotelOrder.setPayAmount(hotelOrderVO.getActualAmount());
            hotelOrder.setDiscountAmount(0d);
            hotelOrder.setActualAmount(hotelOrderVO.getActualAmount());
            hotelOrder.setUserId(hotelOrderVO.getUserId());
            hotelOrder.setOrderUserCode(hotelOrderVO.getUserId());
            hotelOrder.setCreateUser(user.getName());
            hotelOrder.setCreateTime(new Timestamp(new Date().getTime()));
            HotelOrder newOrder=hotelOrderRepository.save(hotelOrder);

        //转换成DTO
        return hotelOrderMapper.toDto(newOrder);
    }

    @Override
    public HotelOrder findByOrderId(String orderId){
        return hotelOrderRepository.findByOrderId(orderId);
    }

    @Override
    public HotelOrderDTO findByOrderIdAndUserId(String orderId,Integer userId){
        HotelOrder hotelOrder= hotelOrderRepository.findByOrderIdAAndUserId(orderId,userId);
        if (StringUtils.isEmpty(hotelOrder)){
            throw new BadRequestException("没有找到此订单！");
        }else {
            HotelOrderDTO hotelOrderDTO= hotelOrderMapper.toDto(hotelOrder);
            if (!StringUtils.isEmpty(hotelOrderDTO.getShopId())){
                Shop shop=shopRepository.findById(hotelOrderDTO.getShopId()).orElse(new Shop());
                HotelRoomType hotelRoomType=hotelRoomTypeRepository.findById(hotelOrder.getRoomTypeId()).orElse(new HotelRoomType());
                if (!StringUtils.isEmpty(shop)){
                    hotelOrderDTO.setShopName(shop.getName());
                    hotelOrderDTO.setHotelRoomType(hotelRoomTypeMapper.toDto(hotelRoomType));
                }
            }
            return hotelOrderDTO;
        }
    }

    @Override
    public boolean cancelOrder(String orderId,Integer userId){
        HotelOrder hotelOrder= hotelOrderRepository.findByOrderIdAAndUserId(orderId,userId);
        boolean b=false;
        if (StringUtils.isEmpty(hotelOrder)){
            throw new BadRequestException("没有找到此订单！");
        }else {
            Map<String,String> res=new HashMap<>();
            try {
                res=refund(orderId,hotelOrder.getActualAmount());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if("SUCCESS".equals(res.get("return_code")) && "SUCCESS".equals(res.get("result_code"))){
                systemCancelOrder(hotelOrder);
                b=true;
            }else{
                logger.info("【小程序支付】统一下单失败，失败原因:"+res.get("return_msg"));
            }
            return b;
        }
    }

    @Override
    public void systemCancelOrder(HotelOrder hotelOrder){
        hotelOrder.setStatus(99);
        hotelOrderRepository.save(hotelOrder);
        String[] roomList=hotelOrder.getRoomList().split(",");
        if(!StringUtils.isEmpty(roomList)){
            for (String str:roomList){
                Integer roomId=Integer.parseInt(str);
                HotelRoom hotelRoom=hotelRoomRepository.findById(roomId).get();
                hotelRoom.setRoomStatus(1);
                hotelRoomRepository.save(hotelRoom);
            }
        }

    }

    private String getOrderId() {
        Date today = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd");
        String prefix=sdf.format(today);
        String lastNo=hotelOrderRepository.findByPrefix(prefix);
        if (lastNo == null || lastNo.startsWith(prefix) == false) {
            Integer seq = 1;
            String newNo = prefix + StringUtil.leftPad(seq.toString(), 5, "0");

            while (hotelOrderRepository.findByNewNo(newNo) != null) {
                seq += 1;
                newNo = prefix + StringUtil.leftPad(seq.toString(), 5, "0");
            }
            return newNo;
        } else {
            Integer seq = Integer.parseInt(lastNo.substring(prefix.length())) + 1;
            String newNo = prefix + StringUtil.leftPad(seq.toString(), 5, "0");
            while (hotelOrderRepository.findByNewNo(newNo) != null) {
                seq += 1;
                newNo = prefix + StringUtil.leftPad(seq.toString(), 5, "0");
            }
            return newNo;
        }
    }


    /**
     * 退款
     * @param orderId
     * @param payAmount
     * @return
     * @throws Exception
     */
    private Map<String,String> refund(String orderId, Double payAmount) throws Exception{
        SortedMap<String,String> map = new TreeMap<>();
        map.put("appid",WXUtil.APPID);
        map.put("mch_id",WXUtil.MCHID);
        map.put("nonce_str",PayUtil.makeUUID(32));
        map.put("out_trade_no",orderId);
        map.put("out_refund_no",orderId);
        map.put("total_fee",PayUtil.moneyToIntegerStr(payAmount));
        map.put("refund_fee",PayUtil.moneyToIntegerStr(payAmount));
        map.put("sign",PayUtil.createSign(map,WXUtil.KEY));
        String xmlData = PayUtil.mapToXml(map);
        logger.info("微信退款请求数据："+xmlData);
        //返回的结果
        String resXml= WXUtil.doWXRefundPost("https://api.mch.weixin.qq.com/secapi/pay/refund",WXUtil.MCHID,xmlData);
        logger.info("微信退款返回数据："+resXml);
        return PayUtil.xmlStrToMap(resXml);
    }
}
