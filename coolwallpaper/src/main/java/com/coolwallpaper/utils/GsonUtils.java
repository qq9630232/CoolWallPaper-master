package com.coolwallpaper.utils;

import com.google.gson.Gson;

/**
 * Created by SDC on 2019/6/13.
 */

public class GsonUtils {
    private static Gson instance;
    private GsonUtils(){

    }
    public static Gson getInstance(){
        if (instance==null){
            instance = new Gson();
        }
        return instance;
    }

}
