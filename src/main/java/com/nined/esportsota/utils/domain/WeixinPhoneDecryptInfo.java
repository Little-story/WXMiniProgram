package com.nined.esportsota.utils.domain;

import lombok.Data;

@Data
public class WeixinPhoneDecryptInfo {
    private String phoneNumber;
    private String purePhoneNumber;
    private int countryCode;
    private String weixinWaterMark;
    private WaterMark watermark;
}
