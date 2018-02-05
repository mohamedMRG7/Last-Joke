package com.example.moham.lastjoke.jokeNotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.example.moham.lastjoke.MainActivity;
import com.example.moham.lastjoke.R;

/**
 * Created by moham on 2/4/2018.
 */

public class NotificationUtilies {


    private static final int SHOW_JOKE_FEEDBACK_PENDINGINTENT_ID = 125;
    private static final String JOKE_NOTIFICATION_CHANNEL_ID = "joke_notification_channel";
    private static final int JOKE_NOTIFICATION_ID = 122;

    public static void notifybyrondomJoke(Context context, String joke ,int positon) {
        // COMPLETED (8) Get the NotificationManager using context.getSystemService
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        // COMPLETED (9) Create a notification channel for Android O devices
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    JOKE_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }
        // COMPLETED (10) In the remindUser method use NotificationCompat.Builder to create a notification
        // that:
        // - has a color of R.colorPrimary - use ContextCompat.getColor to get a compatible color
        // - has ic_drink_notification as the small icon
        // - uses icon returned by the largeIcon helper method as the large icon
        // - sets the title to the charging_reminder_notification_title String resource
        // - sets the text to the charging_reminder_notification_body String resource
        // - sets the style to NotificationCompat.BigTextStyle().bigText(text)
        // - sets the notification defaults to vibrate
        // - uses the content intent returned by the contentIntent helper method for the contentIntent
        // - automatically cancels the notification when the notification is clicked
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,JOKE_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.logonotxt)
                .setSound(uri)
                //.setLargeIcon(largeIcon(context))

                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(joke)

                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        joke))
                .setDefaults(Notification.DEFAULT_VIBRATE)
               // .setContentIntent(contentIntent(context,positon))
                .setFullScreenIntent(contentIntent(context,positon),true)
                .setAutoCancel(true);

        // COMPLETED (11) If the build version is greater than JELLY_BEAN and lower than OREO,
        // set the notification's priority to PRIORITY_HIGH.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        // COMPLETED (12) Trigger the notification by calling notify on the NotificationManager.
        // Pass in a unique ID of your choosing for the notification and notificationBuilder.build()
        notificationManager.notify(JOKE_NOTIFICATION_ID, notificationBuilder.build());
    }




    private static PendingIntent contentIntent(Context context ,int pos) {

        Intent startActivityIntent = new Intent(context, MainActivity.class);
        startActivityIntent.putExtra("position",pos);

        return PendingIntent.getActivity(
                context,
                SHOW_JOKE_FEEDBACK_PENDINGINTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
