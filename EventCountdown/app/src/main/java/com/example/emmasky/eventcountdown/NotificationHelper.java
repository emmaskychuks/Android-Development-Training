package com.example.emmasky.eventcountdown;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by emmas on 4/6/2018.
 */
public class NotificationHelper {
    private static final String NOTIFICATION_CHANNEL_ID = "com.eventcountdown";
    private static final AtomicInteger c = new AtomicInteger(0);
    private static final int notificationID = 0;
    private static NotificationManager notificationManager;
    private static NotificationCompat.Builder mBuilder;

    public NotificationHelper() {
    }

    public static int getID() {
        return c.incrementAndGet();
    }

    public static void createNotification(String title, String message, Context context)
    {
        mBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(false)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert notificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        assert notificationManager != null;
        notificationManager.notify(notificationID, mBuilder.build());
    }
}
