package com.nined.esportsota.service.impl;

import cn.hutool.http.HttpUtil;
import com.nined.esportsota.domain.*;
import com.nined.esportsota.repository.*;
import com.nined.esportsota.service.HotelOrderService;
import com.nined.esportsota.service.HotelRoomService;
import com.nined.esportsota.service.PayService;
import com.nined.esportsota.service.criteria.HotelRoomQueryCriteria;
import com.nined.esportsota.utils.PayUtil;
import com.nined.esportsota.utils.StringUtil;
import com.nined.esportsota.utils.WXUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PayServiceImpl implements PayService {

    @Autowired
    private HotelOrderService hotelOrderService;
    @Autowired
    private HotelRoomService hotelRoomService;

    @Autowired
    private HotelOrderRepository hotelOrderRepository;

    @Autowired
    private HotelRoomRepository hotelRoomRepository;

    @Autowired
    private HotelOrderUserRepository hotelOrderUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Value("${url}")
    private String url;

    private static Logger LOGGER = LoggerFactory.getLogger(PayServiceImpl.class);

    @Override
    public Map<String,String> xcxPayment(String orderId, double payAmount,String openId,String shopName) throws Exception{
        LOGGER.info("【小程序支付】 统一下单开始, 订单编号="+orderId);
        SortedMap<String, String> resultMap = new TreeMap<>();
        //添加或更新支付记录(参数跟进自己业务需求添加)
        int flag = 1;//this.addOrUpdatePaymentRecord(orderId, payAmount,.....);
        if(flag < 0){
            resultMap.put("returnCode", "FAIL");
            resultMap.put("returnMsg", "此订单已支付！");
            LOGGER.info("【小程序支付】 此订单已支付！");
        }else if(flag == 0){
            resultMap.put("returnCode", "FAIL");
            resultMap.put("returnMsg", "支付记录生成或更新失败！");
            LOGGER.info("【小程序支付】 支付记录生成或更新失败！");
        }else{
            Map<String,String> resMap = this.xcxUnifieldOrder(orderId,payAmount,openId,shopName);
            if("SUCCESS".equals(resMap.get("return_code")) && "SUCCESS".equals(resMap.get("result_code"))){
                resultMap.put("appId", WXUtil.APPID);
                resultMap.put("timeStamp", PayUtil.getCurrentTimeStamp());
                resultMap.put("nonceStr", PayUtil.makeUUID(32));
                resultMap.put("package", "prepay_id="+resMap.get("prepay_id"));
                resultMap.put("signType", "MD5");
                resultMap.put("sign", PayUtil.createSign(resultMap,WXUtil.KEY));
                resultMap.put("returnCode", "SUCCESS");
                resultMap.put("returnMsg", "OK");
                LOGGER.info("【小程序支付】统一下单成功，返回参数:"+resultMap);
            }else{
                resultMap.put("returnCode", resMap.get("return_code"));
                resultMap.put("returnMsg", resMap.get("return_msg"));
                LOGGER.info("【小程序支付】统一下单失败，失败原因:"+resMap.get("return_msg"));
            }
        }
        return resultMap;
    }

    @Override
    //微信支付回调
    public int xcxNotify(Map<String,String> map){
        int flag=1;
        LOGGER.info("【小程序支付】统一下单回调参数:"+map.toString());
        //支付订单编号
        String orderId = map.get("out_trade_no");
        //检验是否需要再次回调刷新数据
        //TODO 微信后台回调，刷新订单支付状态等相关业务
        HotelOrder hotelOrder=hotelOrderService.findByOrderId(orderId);
        hotelOrder.setStatus(2);
        hotelOrder.setPayStatus(1);
        hotelOrder.setPayTime(new Timestamp(new Date().getTime()));

        //查询可预订房间
        HotelRoomQueryCriteria criteria=new HotelRoomQueryCriteria();
        criteria.setRoomTypeId(hotelOrder.getRoomTypeId());
        criteria.setBookInDate(hotelOrder.getBookInDate().getTime());
        criteria.setBookOutDate(hotelOrder.getBookOutDate().getTime());
        List<HotelRoom> roomList=hotelRoomService.roomNum(criteria);

        //修改房间、订单信息
        String roomArr="";
        if (StringUtils.isEmpty(roomList)||roomList.size()<=0){
            flag=0;
            hotelOrderService.cancelOrder(hotelOrder.getOrderId(),hotelOrder.getUserId());
            return flag;
        }else {
            //预定房间数量
            for (int i=0;i<hotelOrder.getRoomNum();i++){
                HotelRoom hotelRoom= roomList.get(i);
                hotelRoom.setRoomStatus(2);
                hotelRoomRepository.save(hotelRoom);
                if (i==hotelOrder.getRoomNum()-1){
                    roomArr+=hotelRoom.getId();
                }else {
                    roomArr+=hotelRoom.getId()+";";
                }
            }
        }
        hotelOrder.setRoomList(roomArr);

        Users user=userRepository.findById(hotelOrder.getUserId()).get();
        Shop shop=shopRepository.findById(hotelOrder.getShopId()).get();

        HotelOrderUser hotelOrderUser=hotelOrderUserRepository.findByNameAndMobile(user.getName(),user.getMobile());
        if (StringUtils.isEmpty(hotelOrderUser)){
            HotelOrderUser orderUser=new HotelOrderUser();
            orderUser.setMerchantId(shop.getMchId());
            orderUser.setShopId(hotelOrder.getShopId());
            orderUser.setCode(getOrderUserCode());
            orderUser.setName(user.getNickName());
            orderUser.setNickName(user.getNickName());
            orderUser.setIdCard("");
            orderUser.setMobile(user.getMobile());
            orderUser.setOrderNum(1);
            orderUser.setPayAmount(hotelOrder.getActualAmount());
            orderUser.setActualPayAmount(hotelOrder.getActualAmount());
            hotelOrderUserRepository.save(orderUser);

            hotelOrder.setOrderUserCode(orderUser.getCode());
        }else {
            hotelOrderUser.setOrderNum(hotelOrderUser.getOrderNum()+1);
            hotelOrderUser.setPayAmount(hotelOrderUser.getPayAmount()+hotelOrder.getActualAmount());
            hotelOrderUser.setActualPayAmount(hotelOrderUser.getActualPayAmount()+hotelOrder.getActualAmount());
            hotelOrderUserRepository.save(hotelOrderUser);

            hotelOrder.setOrderUserCode(hotelOrderUser.getCode());
        }

        hotelOrderRepository.save(hotelOrder);
        return flag;

    }



    private Map<String,String> xcxUnifieldOrder(String orderNum, double payAmount,String openid,String shopName) throws Exception{
        SortedMap<String,String> map = new TreeMap<>();
        map.put("appid", WXUtil.APPID);
        map.put("mch_id",WXUtil.MCHID);
        map.put("nonce_str", PayUtil.makeUUID(32));
        map.put("body",shopName);
        map.put("out_trade_no",orderNum);
        map.put("total_fee", StringUtil.moneyToIntegerStr(payAmount));
        map.put("spbill_create_ip",PayUtil.getLocalIp());
        map.put("notify_url",getNotifyUrl());
        map.put("trade_type","JSAPI");
        map.put("openid",openid);
        map.put("sign",PayUtil.createSign(map,WXUtil.KEY));
        String xmlData = PayUtil.mapToXml(map);
        //返回的结果
        String resXml= HttpUtil.post("https://api.mch.weixin.qq.com/pay/unifiedorder",xmlData);
        LOGGER.info("统一下单请求数据："+xmlData);
        LOGGER.info("统一下单返回数据："+resXml);
        return PayUtil.xmlStrToMap(resXml);
    }

    private String getNotifyUrl(){
        //服务域名
        return url + "/ota/wx/xcxNotify";
    }

    public Integer getOrderUserCode() {
        Date today = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd");
        String prefix=sdf.format(today);
        String lastNo = hotelOrderUserRepository.findByPrefix(prefix);

        if (lastNo == null || lastNo.startsWith(prefix) == false) {
            Integer seq = 1;
            String newNo = prefix + StringUtil.leftPad(seq.toString(), 4, "0");
            while (hotelOrderUserRepository.findByNewNo(newNo) != null) {
                seq += 1;
                newNo = prefix + StringUtil.leftPad(seq.toString(), 4, "0");
            }
            return Integer.parseInt(newNo);
        } else {
            Integer seq = Integer.parseInt(lastNo.substring(prefix.length())) + 1;
            String newNo = prefix + StringUtil.leftPad(seq.toString(), 4, "0");
            while (hotelOrderUserRepository.findByNewNo(newNo) != null) {
                seq += 1;
                newNo = prefix + StringUtil.leftPad(seq.toString(), 4, "0");
            }
            return Integer.parseInt(newNo);
        }
    }
}
