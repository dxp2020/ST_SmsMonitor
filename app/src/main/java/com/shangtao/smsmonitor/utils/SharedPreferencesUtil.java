package com.shangtao.smsmonitor.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.mula.base.BaseApplication;

public class SharedPreferencesUtil {

    public static SharedPreferences getSharePreferences(Context context) {
        return context.getSharedPreferences(context.getPackageName() + "_preferences", Context.MODE_MULTI_PROCESS);
    }

    public static void putContent(String key,String content) {
        SharedPreferences preferences = getSharePreferences(BaseApplication.getInstance());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, content);
        editor.apply();
    }

    public static String getContent(String key) {
        return getSharePreferences(BaseApplication.getInstance()).getString(key, "");
    }

}
