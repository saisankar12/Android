package com.example.saisankar.jobschedular;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
 JobScheduler jobScheduler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jobScheduler= (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
    }

    public void doJob(View view)
    {
        JobInfo.Builder builder= new JobInfo.Builder(2,new ComponentName(this,MyService.class.getName()));
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setRequiresCharging(true);
        jobScheduler.schedule(builder.build());
    }
}
