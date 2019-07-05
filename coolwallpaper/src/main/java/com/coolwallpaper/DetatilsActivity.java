package com.coolwallpaper;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.coolwallpaper.utils.CommonDialogUtils;
import com.coolwallpaper.utils.FileUtil;

import java.io.IOException;

public class DetatilsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mDetailImg;
    private ImageView mBackBtn;
    private String url;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //设置壁纸时下载成功的消息
                case 0:
                    String filePath = (String) msg.obj;
                    //设置壁纸
                    Bitmap bitmap = android.graphics.BitmapFactory.decodeFile(filePath);
                    try {
                        setWallpaper(bitmap);
                        Toast.makeText(DetatilsActivity.this, "设置壁纸成功", Toast.LENGTH_SHORT).show();
                        //更新bmob图片信息
//                        updateBmobPicture(getCurrentPicture(), 0, 1, 0);
                    } catch (IOException e) {
                        Toast.makeText(DetatilsActivity.this, "设置壁纸失败", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    break;
                //设置壁纸时下载失败的消息
                case 1:
                    String reason = (String) msg.obj;
                    Toast.makeText(DetatilsActivity.this, "设置壁纸失败:" + reason, Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detatils);
        initView();
    }

    private void initView() {
        String img = getIntent().getStringExtra("img");
        url = getIntent().getStringExtra("url");
        Log.e("zxz", "initView: " + url);
        mDetailImg = (ImageView) findViewById(R.id.detail_img);
        mDetailImg.setOnClickListener(this);
        Glide.with(this).load(url).into(mDetailImg);
        mBackBtn = (ImageView) findViewById(R.id.back_btn);
        mBackBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.detail_img:
                CommonDialogUtils.createCornerDialog(this, "设置壁纸", "是否保存成壁纸？", "取消", "确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommonDialogUtils.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //下载图片
                        FileUtil.getInstance().downloadFile(url, FileUtil.DIRECTORY_DOWNLOAD, new FileUtil.DownloadCallback() {

                            @Override
                            public void onSuccess(String filePath) {
                                //发送设置壁纸的消息
                                Message msg = Message.obtain();
                                msg.what = 0;
                                msg.obj = filePath;
                                handler.sendMessage(msg);
                            }

                            @Override
                            public void onError(String reason) {
                                //发送下载壁纸失败的消息
                                Message msg = Message.obtain();
                                msg.what = 1;
                                msg.obj = reason;
                                handler.sendMessage(msg);
                            }
                        });
                        CommonDialogUtils.dismiss();

                    }
                });
                break;
            case R.id.back_btn:
                finish();
                break;
        }
    }
}
