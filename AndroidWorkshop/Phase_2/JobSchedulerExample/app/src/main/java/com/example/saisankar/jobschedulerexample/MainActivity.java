package com.example.saisankar.jobschedulerexample;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    JobInfo.Builder jobInfo;
    JobScheduler scheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scheduler= (JobScheduler)
                getSystemService(JOB_SCHEDULER_SERVICE);
    }

    public void doAjob(View view)
    {
        jobInfo=new JobInfo.Builder(3,
                new ComponentName(getPackageName(),
                        MyService.class.getName()));
        jobInfo.setRequiresCharging(true);
        jobInfo.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        scheduler.schedule(jobInfo.build());
    }
}
