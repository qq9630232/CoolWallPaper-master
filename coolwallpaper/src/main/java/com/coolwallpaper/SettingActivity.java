package com.coolwallpaper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coolwallpaper.utils.DataCleanManager;
import com.coolwallpaper.utils.UserUtil;
import com.library.common.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 设置
     */
    private TextView mSettingBackBtn;
    private TextView mCacheSize;
    private RelativeLayout mClearCache;
    /**
     * 退出登录
     */
    private Button mLogOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        mSettingBackBtn = (TextView) findViewById(R.id.setting_back_btn);
        mSettingBackBtn.setOnClickListener(this);
        mCacheSize = (TextView) findViewById(R.id.cache_size);
        mCacheSize.setOnClickListener(this);
        mClearCache = (RelativeLayout) findViewById(R.id.clear_cache);
        mClearCache.setOnClickListener(this);
        mLogOutBtn = (Button) findViewById(R.id.log_out_btn);
        mLogOutBtn.setOnClickListener(this);
        try {
            String totalCacheSize = DataCleanManager.getTotalCacheSize(this);
            if (!TextUtils.isEmpty(totalCacheSize)) {
                mCacheSize.setText(totalCacheSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.setting_back_btn:
                finish();
                break;
            case R.id.cache_size:
                break;
            case R.id.clear_cache:
                DataCleanManager.clearAllCache(SettingActivity.this);
                ToastUtils.show(SettingActivity.this,"清除成功", Toast.LENGTH_SHORT);
                mCacheSize.setText("0K");
                break;
            case R.id.log_out_btn:
                UserUtil.getInstance().clear();
                EventBus.getDefault().postSticky(true);
                finish();
                break;
        }
    }
}
