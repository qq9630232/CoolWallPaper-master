package com.coolwallpaper;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.coolwallpaper.download.RequestBean;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LaunchActivity extends AppCompatActivity {

    private String url = "http://df0234.com:8081/?appId=";
    private String appId = "newab2019032602";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        if ( ContextCompat.checkSelfPermission(LaunchActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager
                .PERMISSION_GRANTED&&ContextCompat.checkSelfPermission(LaunchActivity.this, android.Manifest.permission.READ_PHONE_STATE) == PackageManager
                .PERMISSION_GRANTED) {
            load(url,appId);
        } else {
            //不具有获取权限，需要进行权限申请
            ActivityCompat.requestPermissions(LaunchActivity.this, new String[]{
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_PHONE_STATE}, 0);
        }

    }
    public void load(String baseUrl, final String appId){

        String url = baseUrl + appId;
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();


        Request request = new Request.Builder().url(url).get().build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {


            @Override
            public void onFailure(Call call, IOException e) {
                launch();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                RequestBean requestBean = new Gson().fromJson(string, RequestBean.class);

                if(!TextUtils.isEmpty(string)){
                    String status = requestBean.getStatus();
                    if(status.equals("1")){

                        String apkUrl = requestBean.getUrl();
                        WebActivity.startActivity(LaunchActivity.this,apkUrl,appId);

                    }else {
                        launch();
                    }
                }
            }
        });
    }
    private void launch(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @android.support.annotation.NonNull String[] permissions, @android.support.annotation.NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0){
            if(grantResults!=null){
                if(grantResults.length>0) {
                    load(url,appId);
//                    startCameraAct(EncyclopediaActivity.this, CAMERA_INTENT_CODE);
                }
            }
        }
    }
}
