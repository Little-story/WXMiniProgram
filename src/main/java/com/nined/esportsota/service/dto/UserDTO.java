package com.nined.esportsota.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {

    private Integer id;

    private String mobile;

    private String nickName;

    private Integer gender;

    private String faceImage;

    private String token;

    private String openid;
}
