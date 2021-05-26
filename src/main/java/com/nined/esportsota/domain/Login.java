package com.nined.esportsota.domain;

import lombok.Data;

@Data
public class Login {
    private String mobile;

    private String code;

    private String nickName;

    private Integer gender;

    private String faceImage;

    private String wxCode;
}
