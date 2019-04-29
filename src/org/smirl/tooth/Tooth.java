/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smirl.tooth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import static java.net.HttpURLConnection.HTTP_OK;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.smirl.tooth.listener.OnConnectionListener;

/**
 *
 * @author CongoPublishers
 */
public class Tooth {
    
    public enum Method {
    GET("GET"),
    POST("POST");
    /**
     * the String value of the Method
     */
    public String value;

    Method(String method) {
        this.value = method;
    }
}

    private static String USER_AGENT = "Mozilla/5.0";
    private static String ACCEPT_LANGUAGE = "en-US, en;q=0.5";
    private static String CONTENT_TYPE = "application/x-www-form-urlencoded";

    public Tooth(String url, OnConnectionListener listener) {
        this(Method.GET, url, null, listener);
    }

    public Tooth(String url, HashMap<String, String> data, OnConnectionListener listener) {
        this(Method.GET, url, data, listener);
    }

    public Tooth(Method method, String url, OnConnectionListener listener) {
        this(Method.GET, url, null, listener);
    }

    public Tooth(Method method, String url, HashMap<String, String> data, OnConnectionListener listener) {

        send(method, url, data, listener);

    }

    private void send(final Method method, final String url, final HashMap<String, String> data, final OnConnectionListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String params = null;
                String _url = url;
                try {
                    if (data != null) {
                        params = encodeMap(data);
                    }

                    if (method == Method.GET) {
                        if (_url.endsWith("?")) {
                            _url += params;
                        } else {
                            _url += "?" + params;
                        }
                    }
                    URL obj = new URL(_url);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                    //optional default is GET
                    con.setRequestMethod(method.value);

                    // add request header
                    con.setUseCaches(false);
                    con.setRequestProperty("User-Agent", USER_AGENT);
                    con.setRequestProperty("Accept-Language", ACCEPT_LANGUAGE);
                    con.setRequestProperty("Content-Type", CONTENT_TYPE);
                    con.setRequestProperty("charset", "utf-8");

                    if (method == Method.POST && params != null) {
                        byte[] paramsBytes = params.getBytes("UTF-8");
                        con.setDoOutput(true);
                        con.setRequestProperty("Content-Length", String.valueOf(paramsBytes.length));
                        OutputStream out = con.getOutputStream();
                        out.write(paramsBytes);
                        out.flush();
                        out.close();
                    }

                    int responseCode = con.getResponseCode();

                    if (responseCode == HTTP_OK && listener != null) {
                        // read from connection
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(con.getInputStream()));
                        String inputLine;
                        StringBuilder resp = new StringBuilder();

                        while ((inputLine = in.readLine()) != null) {
                            resp.append(inputLine);
                        }

                        in.close();

                        listener.onResponse(resp.toString().trim());
                    } else if (listener != null) {
                        
                            listener.onError(responseCode, con.getResponseMessage());
                        
                    }
                    con.disconnect();
                } catch (Exception ex) {
                    if (listener != null) {
                        listener.onError(500, ex.toString());
                    }

                }
            }
        }).start();
    }

    private String encodeMap(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry< String, String> item : params.entrySet()) {
            String key = URLEncoder.encode(item.getKey(), "UTF-8");
            String value = URLEncoder.encode(item.getValue(), "UTF-8");
            if (sb.length() != 0) {
                sb.append("&");
            }
            sb.append(key).append("=").append(value);
        }
        return sb.toString().trim();
    }
}
