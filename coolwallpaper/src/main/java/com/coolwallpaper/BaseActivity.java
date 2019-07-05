package com.coolwallpaper;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.coolwallpaper.constant.AppBus;
import com.lidroid.xutils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 基类Activity
 * Created by fuchao on 2015/7/9.
 */
public class BaseActivity extends AppCompatActivity {

    protected List<Activity> activityList = new ArrayList<>();
    protected String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityList.add(this);
        //注册事件总线otto
        AppBus.getInstance().register(this);
        setStatusBar();
        this.TAG = String.format("[%s]", this.getClass().getName());
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        //注册xUtils的view注解
        ViewUtils.inject(this);
        //注册ButterKnife
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        //注册xUtils的view注解
        ViewUtils.inject(this);
        //注册ButterKnife
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        //注册xUtils的view注解
        ViewUtils.inject(this);
        //注册ButterKnife
        ButterKnife.bind(this);
    }
    public void setStatusBar() {
//        int color = getResources().getColor(R.color.elita_color_white);
//        StatusBarUtil.setColor(mElitaBaseActivity, color, 0);
//        StatusBarUtil.setTranslucent(this,50);
//        StatusBarUtil.setLightMode(mElitaBaseActivity);
    }
    @Override
    protected void onDestroy() {
        //取消otto注册
        AppBus.getInstance().unregister(this);
        activityList.remove(this);
        super.onDestroy();
    }

    public Activity getActivity() {
        return this;
    }
}
