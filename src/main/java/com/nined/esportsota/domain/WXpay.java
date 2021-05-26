package com.nined.esportsota.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class WXpay{

    private String openid;

    private String orderId;

    private Double amount;
}
