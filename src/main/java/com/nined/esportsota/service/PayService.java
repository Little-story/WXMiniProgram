package com.nined.esportsota.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface PayService {

    /**
     * 微信支付回调
     * @param map
     * @return
     */
    int xcxNotify(Map<String,String> map);

    /**
     * 统一下单接口
     * @param orderId
     * @param money
     * @param openId
     * @return
     * @throws Exception
     */
    Map<String,String> xcxPayment(String orderId, double money,String openId,String shopName) throws Exception;
}
