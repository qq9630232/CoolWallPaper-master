package com.coolwallpaper.utils;

import android.os.Handler;
import android.text.TextUtils;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by SDC on 2019/6/13.
 */

public class HttpUtils {
    private static Handler handler = new Handler();
    private static volatile HttpUtils instance;
    private HttpUtils(){

    }
    public static HttpUtils getInstance(){
        if (null==instance){
            synchronized (HttpUtils.class){
                if (instance == null){
                    instance = new HttpUtils();
                }
            }
        }
        return instance;
    }
    public void get(String url, Map<String,String> map, final CallBack callBack, final Class cls, final String tag){
        if (TextUtils.isEmpty(url)){
            return;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(url);
        if (url.contains("?")){
            if (url.indexOf("?")==url.length()-1){

            }else {
                sb.append("&");
            }
        }else {
            sb.append("?");
        }
        for (Map.Entry<String,String> entry:map.entrySet()){
            sb.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }
        if (sb.indexOf("&")!=-1){
            sb.deleteCharAt(sb.lastIndexOf("&"));
        }
        OkHttpClient client = new OkHttpClient
                .Builder()
                .build();
        Request request = new Request.Builder()
                .get()
                .url(sb.toString())
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.failed(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                //请求成功后做解析，通过自己的回调接口将数据返回去
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Object o;
                        if (TextUtils.isEmpty(result)){
                            o = null;
                        }else {
                            o = GsonUtils.getInstance().fromJson(result, cls);
                        }
                        callBack.success(o);
                    }
                });
            }
        });


    }

}
