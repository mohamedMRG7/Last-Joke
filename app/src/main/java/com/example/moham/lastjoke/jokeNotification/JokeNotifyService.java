package com.example.moham.lastjoke.jokeNotification;

import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by moham on 2/4/2018.
 */

public class JokeNotifyService  extends JobService{

    private AsyncTask task;
    @Override
    public boolean onStartJob(final JobParameters job) {

        task=new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {

                JokeNotifyTask.excuteTask(JokeNotifyService.this,JokeNotifyTask.JOKE_NOTIFY_ACTION);

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);

                jobFinished(job, false);

            }
        };

        task.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {

        if (task !=null)task.cancel(true);

        return true;
    }
}
