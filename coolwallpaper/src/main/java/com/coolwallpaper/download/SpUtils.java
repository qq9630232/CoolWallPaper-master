package com.coolwallpaper.download;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.coolwallpaper.MyApplication;


/**
 * Created by SDC on 2019/5/9.
 */

public class SpUtils {
    private static SpUtils mInstance;
    private SharedPreferences sharedPreferences;


    public static SpUtils getInstance() {
        if (mInstance == null) {
            synchronized (SpUtils.class) {
                if (mInstance == null) {
                    mInstance = new SpUtils();
                }
            }
        }
        return mInstance;
    }

    private SpUtils() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
    }

    public void putInt(String key, int value) {
        sharedPreferences.edit().putInt(key,value).apply();
    }

    public int getInt(String key, int def) {
        return sharedPreferences.getInt(key,def);
    }

    public void putLong(String key, long value) {
        sharedPreferences.edit().putLong(key,value).apply();
    }

    public long getLong(String key, long def) {
        return sharedPreferences.getLong(key, def);
    }

    public void putString(String key, String value) {
        sharedPreferences.edit().putString(key,value).apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void putBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key,value).apply();
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }
}
