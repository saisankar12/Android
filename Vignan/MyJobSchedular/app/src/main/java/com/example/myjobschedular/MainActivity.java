package com.example.myjobschedular;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button start, stop;
    JobScheduler jobScheduler;
    JobInfo jobInfo;
    ComponentName componentName;
    private static final String TAG = "ExampleJobService";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        componentName = new ComponentName(getPackageName(),
                MyJobService.class.getName());
        start = findViewById(R.id.startButton);
        stop = findViewById(R.id.stopButton);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobInfo.Builder builder = new JobInfo.Builder(98, componentName);
                builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
                builder.setRequiresCharging(true);
                builder.setPeriodic(15 * 60 * 1000);
                jobInfo = builder.build();
                int resultCode = jobScheduler.schedule(jobInfo);

                if (resultCode == JobScheduler.RESULT_SUCCESS) {
                    Log.d(TAG, "Job Scheduled");
                } else {
                    Log.d(TAG, "Job Scheduled faild");
                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobScheduler.cancel(98);
                Log.d(TAG, "job cancelled");
            }
        });
    }
}
