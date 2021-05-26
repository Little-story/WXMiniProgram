package com.nined.esportsota.utils;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class SmsUtil {
	public static String URL = "http://gw.api.taobao.com/router/rest";
	public static String accessKeyId = "23435318";
	public static String accessKeySecret = "d6b23451e5f11ca03568620a03b48f38";
	public static String CODE_SIGN = "玖的";
	public static String CODE_TEMPLATE = "SMS_13315382";

	public static boolean sendCode(String mobileNo, String code) {
		System.out.println(code);
		TaobaoClient client = new DefaultTaobaoClient(URL, accessKeyId, accessKeySecret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("");
		req.setSmsType("normal");
		req.setSmsFreeSignName(CODE_SIGN);
		req.setSmsParamString("{\"coke\":\"" + code + "\"}");
		req.setRecNum(mobileNo);
		req.setSmsTemplateCode(CODE_TEMPLATE);
		AlibabaAliqinFcSmsNumSendResponse rsp;
		try {
			rsp = client.execute(req);
			System.out.println(rsp.getBody());
			return rsp.getResult()!=null && rsp.getResult().getSuccess();
		} catch (ApiException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean sendCodeByReg(String mobileNo, String code) {
		TaobaoClient client = new DefaultTaobaoClient(URL, accessKeyId, accessKeySecret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("");
		req.setSmsType("normal");
		req.setSmsFreeSignName(CODE_SIGN);
		req.setSmsParamString("{\"code\":\"" + code + "\"}");
		req.setRecNum(mobileNo);
		req.setSmsTemplateCode("SMS_151995254");
		AlibabaAliqinFcSmsNumSendResponse rsp;
		try {
			rsp = client.execute(req);
			System.out.println(rsp.getBody());
			return rsp.getResult()!=null && rsp.getResult().getSuccess();
		} catch (ApiException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean sendMsg(String mobileNo, String templateId, String messageString) {
		TaobaoClient client = new DefaultTaobaoClient(URL, accessKeyId, accessKeySecret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("");
		req.setSmsType("normal");
		req.setSmsFreeSignName(CODE_SIGN);
		req.setSmsParamString(messageString);
		req.setRecNum(mobileNo);
		req.setSmsTemplateCode(templateId);
		AlibabaAliqinFcSmsNumSendResponse rsp;
		try {
			rsp = client.execute(req);
			System.out.println(rsp.getBody());
			return rsp.getResult()!=null && rsp.getResult().getSuccess();
		} catch (ApiException e) {
			e.printStackTrace();
			return false;
		}
	}
}
