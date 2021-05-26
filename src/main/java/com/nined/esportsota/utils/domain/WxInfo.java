package com.nined.esportsota.utils.domain;

import lombok.Data;

@Data
public class WxInfo {
    private String encryptedData;

    private String iv;

    private String code;
}
