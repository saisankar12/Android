package com.example.saisankar.jobschedular;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyService extends JobService {
    public MyService() {
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Toast.makeText(this, "Job Schedule ON", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Toast.makeText(this, "Job Schedule off", Toast.LENGTH_SHORT).show();
        return false;
    }

}
