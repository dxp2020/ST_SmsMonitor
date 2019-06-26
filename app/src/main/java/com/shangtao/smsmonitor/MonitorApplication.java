package com.shangtao.smsmonitor;

import com.mula.base.BaseApplication;
import com.mula.base.bean.ThemeColor;
import com.mula.retrofit.RetrofitConfig;
import com.shangtao.smsmonitor.retrofit.RetrofitCallback;

public class MonitorApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        setThemeColor(ThemeColor.BLUE);
        RetrofitConfig.getInstance().setCallBack(new RetrofitCallback());
    }

    @Override
    public String getAppFlavor() {
        return BuildConfig.FLAVOR;
    }

    @Override
    public int getLevel() {
        return LEVEL_APP;
    }

    @Override
    public Class[] subDelegates() {
        return new Class[0];
    }

    @Override
    public void onCreateDelegate() {

    }
}
