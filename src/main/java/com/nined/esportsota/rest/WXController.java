package com.nined.esportsota.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nined.esportsota.domain.HotelOrder;
import com.nined.esportsota.domain.Login;
import com.nined.esportsota.domain.WXpay;
import com.nined.esportsota.exception.BadRequestException;
import com.nined.esportsota.repository.HotelOrderRepository;
import com.nined.esportsota.service.HotelOrderService;
import com.nined.esportsota.service.PayService;
import com.nined.esportsota.utils.AESForWeixinGetPhoneNumber;
import com.nined.esportsota.utils.PayUtil;
import com.nined.esportsota.utils.WXUtil;
import com.nined.esportsota.utils.domain.WeixinPhoneDecryptInfo;
import com.nined.esportsota.utils.domain.WxInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/wx")
public class WXController {

    @Autowired
    private HotelOrderRepository hotelOrderRepository;

    private static Logger logger= LoggerFactory.getLogger(WXController.class);

    @Autowired
    private PayService payService;

    @Autowired
    private HotelOrderService hotelOrderService;

    @PostMapping(value = "/toPay")
    public ResponseEntity<Object> toPay(@Validated @RequestBody WXpay wXpay) throws Exception{
        if (StringUtils.isEmpty(wXpay)) {
            throw new BadRequestException(HttpStatus.valueOf(400),"参数异常");
        }
        if (StringUtils.isEmpty(wXpay.getOpenid())||StringUtils.isEmpty(wXpay.getOrderId())||StringUtils.isEmpty(wXpay.getAmount())){
            throw new BadRequestException(HttpStatus.valueOf(400),"参数异常");
        }
        return null;
    }


    @PostMapping(value="xcxNotify")
    public void xcxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        InputStream inputStream =  request.getInputStream();
        //获取请求输入流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len=inputStream.read(buffer))!=-1){
            outputStream.write(buffer,0,len);
        }
        outputStream.close();
        inputStream.close();
        Map<String,String> map = PayUtil.xmlStrToMap(new String(outputStream.toByteArray()));
                //BeanToMap.getMapFromXML(new String(outputStream.toByteArray(),"utf-8"));
        logger.info("【小程序支付回调】 回调数据： \n"+map);
        String resXml = "";
        String returnCode = (String) map.get("return_code");
        if ("SUCCESS".equalsIgnoreCase(returnCode)) {
            String returnmsg = (String) map.get("result_code");
            if("SUCCESS".equals(returnmsg)){
                //更新数据
                int result = payService.xcxNotify(map);
                if(result > 0){
                    //支付成功
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>"+"</xml>";
                }else if (result==0){
                    //没有房间了 退款流程
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                            + "<return_msg><![CDATA[预定的酒店暂无房间,退款将在三个工作日内到达]]></return_msg>"+"</xml>";
                }
            }else{
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]></return_msg>" + "</xml>";
                logger.info("支付失败:"+resXml);
            }
        }else{
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]></return_msg>" + "</xml>";
            logger.info("【订单支付失败】");
        }

        logger.info("【小程序支付回调响应】 响应内容：\n"+resXml);
        response.getWriter().print(resXml);
    }


    @GetMapping(value = "/getUserInfo")
    public ResponseEntity<Object> queryPage(WxInfo wxInfo) {
        String encryptedData=wxInfo.getEncryptedData();
        String iv=wxInfo.getIv();
        String code=wxInfo.getCode();
        JSONObject jsonObject=WXUtil.getSessionKey(code);
        String sessionKey=jsonObject.getString("session_key");
        if (StringUtils.isEmpty(sessionKey)){
            throw new BadRequestException("解密失败");
        }
        AESForWeixinGetPhoneNumber aes = new AESForWeixinGetPhoneNumber(encryptedData,sessionKey,iv);
        WeixinPhoneDecryptInfo info=aes.decrypt();
        if (StringUtils.isEmpty(info)){
            throw new BadRequestException("解密失败");
        }
        Map map=new HashMap();
        map.put("data",info);
        map.put("status",HttpStatus.OK.value());
        map.put("message","成功");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}