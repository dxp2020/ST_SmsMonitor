package com.shangtao.smsmonitor.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.google.gson.JsonObject;
import com.mula.base.BaseApplication;
import com.mula.base.tools.L;
import com.mula.base.tools.display.ToastUtil;
import com.mula.retrofit.AppClient;
import com.mula.retrofit.SimpleSubscriber;
import com.shangtao.smsmonitor.SMSManager;
import com.shangtao.smsmonitor.model.MessageEvent;
import com.shangtao.smsmonitor.retrofit.ApiStores;
import com.shangtao.smsmonitor.utils.NotificationBuilder;

import java.util.Map;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ForegroundService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //注册EventBus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        SMSManager.getInstance().registerReceiver(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        NotificationBuilder.setForegroundService(ForegroundService.this);
        return START_STICKY;
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void handleEvent(MessageEvent event) {
        switch (event.getEventType()){
            case UPLOAD_SMS:
                Map<String,String> data = event.getData();
                uploadSms(data.get("sendPhone"),data.get("content"));
                break;
        }
    }

    public void uploadSms(String sendPhone,String content){
        Map<String,String> userParam = SMSManager.getInstance().getUserParam();
        if (userParam!=null) {
            userParam.put("sendPhone",sendPhone);
            userParam.put("content",content);
        }else{
            ToastUtil.show("请求失败，缺少配置参数");
            return;
        }
        AppClient.retrofitOther()
                .create(ApiStores.class)
                .uploadSms(userParam)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleSubscriber<JsonObject>() {
                    @Override
                    public void onSuccess(JsonObject result) {
                        boolean type = result.get("type").getAsBoolean();
                        if(type){
                            ToastUtil.show("短信上传成功！");
                        }else{
                            ToastUtil.show("短信上传失败！");
                        }
                    }
                    @Override
                    public void onFailure(Throwable e) {
                        ToastUtil.show("接口调用异常！");
                        L.e(e);
                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        SMSManager.getInstance().unRegisterReceiver(getApplicationContext());
        stopForeground(true);
        //注销EventBus
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public static void start(){
        try {
            Context context = BaseApplication.getInstance();
            context.startService(new Intent(context, ForegroundService.class));
        } catch (Exception e) {
            L.e(e);
        }
    }

    public static void stop(){
        try {
            Context context = BaseApplication.getInstance();
            context.stopService(new Intent(context, ForegroundService.class));
        } catch (Exception e) {
            L.e(e);
        }
    }

}
