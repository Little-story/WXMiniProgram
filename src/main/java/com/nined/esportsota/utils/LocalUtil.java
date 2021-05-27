package com.nined.esportsota.utils;

public class LocalUtil {

    private static final String shopDir="https://hotel.233esports.com/ota/shopDir/";

    public static String getLocalImage(String url){
        if (url.length()<4){
            return url;
        }
        if ("http".equals(url.substring(0,4))){
            return url;
        }else {
            return shopDir+url;
        }
    }
}
