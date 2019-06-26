package com.shangtao.smsmonitor.presenter;

import android.content.Context;

import com.google.gson.JsonObject;
import com.mula.retrofit.ApiResult;
import com.mula.retrofit.SimpleSubscriber;
import com.mula.retrofit.SubscriberCallBack;
import com.shangtao.smsmonitor.R;
import com.shangtao.smsmonitor.model.User;

import java.util.Map;

import rx.Observable;


public class LoginPresenter extends CommonPresenter<LoginPresenter.LoginView> {

    public interface LoginView {
        void getVerifyCodeResult();
        void userLoginResult(User user);
    }


    public LoginPresenter(LoginView view) {
        attachView(view);
    }

    public void login(Map<String, Object> params) {
        addSubscription(apiStores.login(params),
                new SimpleSubscriber<JsonObject>(mActivity) {
                    @Override
                    public void onSuccess(JsonObject jobj) {

                    }
                });
    }


}
