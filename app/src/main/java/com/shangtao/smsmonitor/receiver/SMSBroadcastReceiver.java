package com.shangtao.smsmonitor.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

import com.mula.base.tools.L;
import com.shangtao.smsmonitor.model.EventType;
import com.shangtao.smsmonitor.model.MessageEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

public class SMSBroadcastReceiver  extends BroadcastReceiver {

    public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    public static final String OPPO_SMS_RECEIVED_ACTION = "android.provider.OppoSpeechAssist.SMS_RECEIVE";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMS_RECEIVED_ACTION)||intent.getAction().equals(OPPO_SMS_RECEIVED_ACTION)) {
            Object[] object=(Object[]) intent.getExtras().get("pdus");
            for (Object pdus : object) {
                byte[] pdusMsg=(byte[]) pdus;
                SmsMessage sms=SmsMessage.createFromPdu(pdusMsg);
                String mobile=sms.getOriginatingAddress();//发送短信的手机号
                String content=sms.getMessageBody();//短信内容
                String time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(sms.getTimestampMillis())); //短信的发送时间

                L.e(toString()+"intent.getAction()--->"+intent.getAction()+"--->"+mobile+"--->"+content+"--->"+time);

                Map<String,String> userParam = new HashMap<>();
                userParam.put("sendPhone",mobile);
                userParam.put("content",content);
                EventBus.getDefault().post(new MessageEvent(EventType.UPLOAD_SMS,userParam));
            }
        }
    }

}
