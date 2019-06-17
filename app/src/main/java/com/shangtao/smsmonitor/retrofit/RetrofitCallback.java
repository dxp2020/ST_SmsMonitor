package com.shangtao.smsmonitor.retrofit;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;

import com.mula.base.BaseApplication;
import com.mula.base.manager.AppManager;
import com.mula.base.tools.MulaUtils;
import com.mula.base.tools.display.ToastUtil;
import com.mula.retrofit.ApiResult;
import com.mula.retrofit.RetrofitConfig;
import com.shangtao.smsmonitor.model.CommonValues;

/**
 * Retrofit配置
 * Created by leichao on 2017/4/15.
 */

public class RetrofitCallback implements RetrofitConfig.CallBack {

    @Override
    public boolean isDebug() {
        return true;
    }

    @Override
    public Application getApplication() {
        return BaseApplication.getSelf();
    }

    @Override
    public Dialog getLoadingDialog(Context context, String message, boolean cancelable) {
        return null;
    }

    @Override
    public String getBaseUrl() {
        return CommonValues.SERVICE_HOST;
    }

    @Override
    public String getUserId() {
        return "";
    }

    @Override
    public String getUserIdKey() {
        return "userId";
    }

    @Override
    public String getSecret() {
        //return AccountManager.getInstance().getUser().getSecret();
        return "";
    }

    @Override
    public String getSecretKey() {
        //return AccountManager.getInstance().getUser().getId() + "_MEMBER";
        return "" + "_MEMBER";
    }

    @Override
    public String getLanguage() {
        return AppManager.getLanguage();
    }

    @Override
    public String getVersion() {
        return MulaUtils.getVersionName(BaseApplication.getSelf());
    }

    @Override
    public void conflictLogin() {
    }

    @Override
    public void loginOut() {
    }

    @Override
    public <T> void toast(ApiResult<T> apiResult) {
       /* if (!AppStatus.isBackground) {
            if (!TextUtils.isEmpty(apiResult.getMessage())) {
                ToastUtil.show(apiResult.getMessage());
            }
        }*/
    }

}
