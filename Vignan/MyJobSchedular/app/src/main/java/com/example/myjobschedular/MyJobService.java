package com.example.myjobschedular;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {
    private static final String TAG="ExampleJobService";
    private boolean jobCancelled=false;

    public MyJobService() {
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG,"job started");
        doBackgroundTask(params);
        return false;

    }

    private void doBackgroundTask(JobParameters params)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<10;i++){
                    Log.d(TAG,"run: "+i);
                        if (jobCancelled){
                            return;
                        }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG,"Job cancelled before completion");
        jobCancelled=false;
        return true;
    }


}
