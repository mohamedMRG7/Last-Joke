package com.example.moham.lastjoke.jokeNotification;

import android.content.Context;
import android.util.Log;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * Created by moham on 2/4/2018.
 */

public class JokeServiceUtilities {



    public static  int NOTIFY_TIME=1;
    public static  int NOTIFY_TIME_INSECONDS= (int) TimeUnit.MINUTES.toSeconds(NOTIFY_TIME);
    public static final String JOB_TAGE="notification_service_tag";



    private static FirebaseJobDispatcher jobDispatcher;
    synchronized public static void schedulNotificationTime(Context context,int notifytime,boolean isnotifyOn)
    {

        if (isnotifyOn) {
            int notifytime_insec = (int) TimeUnit.MINUTES.toSeconds(notifytime);

            GooglePlayDriver driver = new GooglePlayDriver(context);
            jobDispatcher = new FirebaseJobDispatcher(driver);

            Job myJob = jobDispatcher.newJobBuilder()

                    .setService(JokeNotifyService.class)

                    .setTag(JOB_TAGE)

                    .setRecurring(true)

                    .setLifetime(Lifetime.FOREVER)

                    .setTrigger(Trigger.executionWindow(notifytime_insec, notifytime_insec))

                    .setReplaceCurrent(true)

                    .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)

                    .build();

            jobDispatcher.schedule(myJob);

        }else
            {
                if (jobDispatcher!=null)
                {
                    jobDispatcher.cancelAll();
                    jobDispatcher=null;
                }
            }
    }


}
