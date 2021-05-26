package com.nined.esportsota.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;

/**
 * HTTP工具类
 */
public class HttpUtil {
	// 普通访问
	public static String httpRequest(String requestUrl, String requestMethod) {
		StringBuffer buffer = new StringBuffer();
		try {

			URL url = new URL(requestUrl);
			// http协议传输
			HttpURLConnection httpUrlConn = (HttpURLConnection) url
					.openConnection();

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	private static final int TIME_OUT = 1000 * 10000000; // 超时时间
	private static final String CHARSET = "utf-8"; // 设置编码

	// 文件上传
	public static String uploadFile(MultipartFile file,String requestURL,Map<String,String> paramMap) {
		String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
		String PREFIX = "--", LINE_END = "\r\n";
		String CONTENT_TYPE = "multipart/form-data"; // 内容类型
		String param="";//参数
		if(paramMap != null && !paramMap.isEmpty()){
			int i = 0;
			for(Entry<String , String> entry : paramMap.entrySet()){
				if(i==0){
					param +="?"+entry.getKey()+"="+entry.getValue();
				}else {
					param +="&"+entry.getKey()+"="+entry.getValue();
				}
				++i;
		    }
		}
		StringBuffer buffer = new StringBuffer();
		OutputStream outputSteam = null;
		DataOutputStream dos = null;
		InputStream is = null, inputStream = null;
		InputStreamReader inputStreamReader = null;
		try {
			URL url = new URL(requestURL+param);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoInput(true); // 允许输入流
			conn.setDoOutput(true); // 允许输出流
			conn.setUseCaches(false); // 不允许使用缓存
			conn.setRequestMethod("POST"); // 请求方式
			conn.setRequestProperty("Charset", CHARSET); // 设置编码
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
					+ BOUNDARY);
			if (file != null) {
				/**
				 * 当文件不为空，把文件包装并且上传
				 */
				outputSteam = conn.getOutputStream();

				dos = new DataOutputStream(outputSteam);
				StringBuffer sb = new StringBuffer();
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				/**
				 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
				 * filename是文件的名字，包含后缀名的 比如:abc.png
				 */

				sb.append("Content-Disposition:form-data; name=\"file\"; filename=\""
						+ file.getOriginalFilename() + "\"" + LINE_END);
				sb.append(LINE_END);
				dos.write(sb.toString().getBytes());
				is = file.getInputStream();
				byte[] bytes = new byte[1024];
				int len = 0;
				while ((len = is.read(bytes)) != -1) {
					dos.write(bytes, 0, len);
				}
				dos.write(LINE_END.getBytes());
				byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
						.getBytes();
				dos.write(end_data);
				dos.flush();
				/**
				 * 获取响应码 200=成功 当响应成功，获取响应的流
				 */
				int res = conn.getResponseCode();
				if (res == 200) {
					// 将返回的输入流转换成字符串
					inputStream = conn.getInputStream();
					inputStreamReader = new InputStreamReader(
							inputStream, "utf-8");
					BufferedReader bufferedReader = new BufferedReader(
							inputStreamReader);

					String str = null;
					while ((str = bufferedReader.readLine()) != null) {
						buffer.append(str);
					}
					conn.disconnect();
					return buffer.toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			// 释放资源
			if(outputSteam!=null)
				try {
					outputSteam.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(dos!=null)
				try {
					dos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(is!=null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(inputStream!=null)
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(inputStreamReader!=null)
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return "";//失败返回
	}
	
	public static String post(String url, Map<String,Object> map){
	    CloseableHttpClient httpClient = null;
	    HttpPost httpPost = null;
	    String result = null;
	    try{
	        httpClient = HttpClients.createDefault();
	        httpPost = new HttpPost(url);
	        //设置参数
	        List<NameValuePair> list = new ArrayList<NameValuePair>();
	        Iterator iterator = map.entrySet().iterator();
	        while(iterator.hasNext()){
	            Entry<String,Object> elem = (Entry<String, Object>) iterator.next();
	            list.add(new BasicNameValuePair(elem.getKey(), String.valueOf(elem.getValue())));
	        }
	        if(list.size() > 0){
	            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,HTTP.UTF_8);
	            httpPost.setEntity(entity);
	        }
	        HttpResponse response = httpClient.execute(httpPost);
	        if(response != null){
	            HttpEntity resEntity = response.getEntity();
	            if(resEntity != null){
	                result = EntityUtils.toString(resEntity,HTTP.UTF_8);
	            }
	        }
	    }catch(Exception ex){
	        ex.printStackTrace();
	    }
	    return result;
	}
	
	public static String post(String url, String name, Object value){
		HttpClient httpClient = null;
	    HttpPost httpPost = null;
	    String result = null;
	    try{
	        httpClient = HttpClients.createDefault();
	        httpPost = new HttpPost(url);
	        //设置参数
	        List<NameValuePair> list = new ArrayList<NameValuePair>();
	        if(!StringUtils.isEmpty(name)) {
	        	list.add(new BasicNameValuePair(name, String.valueOf(value)));
	        }
	        
	        if(list.size() > 0){
	            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,HTTP.UTF_8);
	            httpPost.setEntity(entity);
	        }
	        HttpResponse response = httpClient.execute(httpPost);
	        if(response != null){
	            HttpEntity resEntity = response.getEntity();
	            if(resEntity != null){
	                result = EntityUtils.toString(resEntity,HTTP.UTF_8);
	            }
	        }
	    }catch(Exception ex){
	        ex.printStackTrace();
	    }
	    return result;
	}

	public static String post(String url,String body){
		CloseableHttpClient httpClient=HttpClients.createDefault();
		HttpPost post=new HttpPost(url);
		CloseableHttpResponse response=null;
		String res="";
		try {
			post.setHeader("Content-type","text/xml");
			HttpEntity entity=new StringEntity(body);
			post.setEntity(entity);
			response=httpClient.execute(post);
			if (response.getStatusLine().getStatusCode()==200){
				res=EntityUtils.toString(response.getEntity(),"utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

    public synchronized static final String getMD5(String data) {
        if (data == null) {
            return null;
        }
        return getMD5(data.getBytes());
    }

    public static String getMD5(byte[] source) {
        String s = null;
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            md.update(source);
            byte tmp[] = md.digest();
            char str[] = new char[16 * 2];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            s = new String(str);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
}
