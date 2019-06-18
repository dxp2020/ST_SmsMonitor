package com.shangtao.smsmonitor;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.IntentFilter;

import com.shangtao.smsmonitor.receiver.SMSBroadcastReceiver;

import java.util.HashMap;
import java.util.Map;

public class SMSManager {

    private static SMSManager mSMSManager;
    private Map<String,String> userParam = new HashMap<>();

    private SMSBroadcastReceiver smsBroadcastReceiver;
    private IntentFilter intentFilter;

    public Map<String, String> getUserParam() {
        return userParam;
    }

    public void setUserParam(Map<String, String> userParam) {
        this.userParam = userParam;
    }

    public void registerReceiver(Context context){
        if (smsBroadcastReceiver==null) {
            smsBroadcastReceiver = new SMSBroadcastReceiver();
            intentFilter=new IntentFilter();
            intentFilter.addAction(SMSBroadcastReceiver.SMS_RECEIVED_ACTION);
            intentFilter.addAction(SMSBroadcastReceiver.OPPO_SMS_RECEIVED_ACTION);
        }
        context.registerReceiver(smsBroadcastReceiver,intentFilter);
    }

    public void unRegisterReceiver(Context context){
        context.unregisterReceiver(smsBroadcastReceiver);
        smsBroadcastReceiver = null;
        intentFilter = null;
    }

    public static synchronized SMSManager getInstance() {
        if (mSMSManager == null) {
            synchronized (AccountManager.class) {
                if (mSMSManager == null) {
                    mSMSManager = new SMSManager();
                }
            }
        }
        return mSMSManager;
    }

}
