package com.coolwallpaper.download;

import android.content.Context;
import android.content.Intent;

import com.coolwallpaper.LaunchActivity;

import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

/**
 * Created by SDC on 2019/5/30.
 */

public class JBroadCast extends JPushMessageReceiver {
    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageOpened(context, notificationMessage);
        Intent intent = new Intent(context, LaunchActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        super.onMessage(context, customMessage);
        Intent intent = new Intent(context, LaunchActivity.class);
        context.startActivity(intent);
    }
}
