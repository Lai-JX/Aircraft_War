package com.aircraftWar.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

//访问servlet
public class PostUtil {


    //访问的serlver不一样
    //传递的参数不一样

    public static String Post(String url,String data)
    {
        String msg = "";
        try{
            //http://ms-yffprtappszi:8080/AndroidWeb/LoginServlet
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            //设置请求方式,请求超时信息
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            //设置运行输入,输出:
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //Post方式不能缓存,需手动设置为false
            conn.setUseCaches(false);
            //我们请求的数据:

            System.out.println("Post!!");

            //获取输出流
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            if (conn.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuffer response = new StringBuffer();

                String line=null;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                msg=response.toString();


            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return msg;
    }
}
