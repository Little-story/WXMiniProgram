package com.nined.esportsota.utils;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nined.esportsota.exception.BadRequestException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;
import sun.misc.IOUtils;

import javax.net.ssl.SSLContext;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.text.MessageFormat;

public class WXUtil {

    public static final String APPID="wx9abd58a1178c8bd1";
    public static final String SECRET="e45978d19ab8ffd4f95ca392597148ac";
    public static final String MCHID="1523146981";
    public static final String KEY="Ksdfu1afe5v1w534ra1f35efa1sa3f4e";

    public static JSONObject getSessionKey(String code){
        String url= MessageFormat.format("https://api.weixin.qq.com/sns/jscode2session?appid={0}&secret={1}&js_code={2}&grant_type=authorization_code",
                APPID,SECRET,code);
        String res=HttpUtil.httpRequest(url,"GET");
        JSONObject object= JSON.parseObject(res);
        return object;
    }

    /**
     * 微信退款请求（要求有证书校验）
     * @param url   微信请求url
     * @param mchId 商户号id
     * @param content 请求参数
     */
    public static String doWXRefundPost(String url, String mchId, String content) throws Exception {
        InputStream certStream=getCertStream();
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(certStream, mchId.toCharArray());

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

        try {
            HttpPost httpost = new HttpPost(url); // 设置响应头信息
            httpost.addHeader("Connection", "keep-alive");
            httpost.addHeader("Accept", "*/*");
            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpost.addHeader("Host", "api.mch.weixin.qq.com");
            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpost.addHeader("Cache-Control", "max-age=0");
            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
            httpost.setEntity(new StringEntity(content, "UTF-8"));
            CloseableHttpResponse response = httpclient.execute(httpost);
            try {
                HttpEntity entity = response.getEntity();

                String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                EntityUtils.consume(entity);
                return jsonStr;
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }


    /**
     * 加载指定商户号对应的证书
     */
    public static InputStream getCertStream() throws IOException {
        byte[] certData = null;
        File directory=new File("233app_weixin_pay.p12");
        String filePath=directory.getCanonicalPath();
        certData = FileUtil.readBytes(filePath);
        ByteArrayInputStream certBis = new ByteArrayInputStream(certData);
        return certBis;
    }
}
