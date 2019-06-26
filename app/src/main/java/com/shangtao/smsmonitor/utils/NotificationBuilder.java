package com.shangtao.smsmonitor.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import com.shangtao.smsmonitor.R;


public class NotificationBuilder {
    private static final String ForegroundServiceChannelId = "Channel id";
    private static final String ForegroundServiceChannelName = "Channel driver";

    public static class Id {
        public static final int FOREGROUND_SERVICE_NOTIFICATION_ID = 10000;
    }

    public static void setForegroundService(Service service) {
        Context context = service.getApplication();
        String text = context.getString(R.string.app_is_running);
        String title = context.getString(R.string.app_name);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(service, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(ForegroundServiceChannelId, ForegroundServiceChannelName, NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(mChannel);
            notification = new Notification.Builder(context, ForegroundServiceChannelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setContentIntent(pi)
                    .build();
        } else {
            notification = new Notification.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setContentIntent(pi)
                    .build();
        }
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        notification.flags |= Notification.FLAG_NO_CLEAR;
        notification.flags |= Notification.FLAG_FOREGROUND_SERVICE;
        service.startForeground(NotificationBuilder.Id.FOREGROUND_SERVICE_NOTIFICATION_ID, notification);
    }

}
