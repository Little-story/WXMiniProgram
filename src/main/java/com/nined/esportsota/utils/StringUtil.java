package com.nined.esportsota.utils;

import java.math.BigDecimal;

public class StringUtil {
    public static String leftPad(String str, int size, String delim) {
        size = (size - str.length()) / delim.length();
        if (size > 0) {
            str = repeat(delim, size) + str;
        }
        return str;
    }



    public static String repeat(String str, int repeat) {
        StringBuffer buffer = new StringBuffer(repeat * str.length());
        for (int i = 0; i < repeat; i++) {
            buffer.append(str);
        }
        return buffer.toString();
    }


    /**
     * 转换金额型到整型
     * @param money
     * @return
     */
    public static String moneyToIntegerStr(Double money){
        BigDecimal decimal = new BigDecimal(money);
        int amount = decimal.multiply(new BigDecimal(100))
                .setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        return String.valueOf(amount);
    }


}
