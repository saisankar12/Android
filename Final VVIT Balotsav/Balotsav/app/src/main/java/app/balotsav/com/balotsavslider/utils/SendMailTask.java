package app.balotsav.com.balotsavslider.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import app.balotsav.com.balotsavslider.R;
import app.balotsav.com.balotsavslider.activities.DashBoardActivity;

public class SendMailTask extends AsyncTask {

    private ProgressDialog statusDialog;
    private Activity sendMailActivity;
    int code;
    int type;

    public SendMailTask(Activity activity, int code, int type) {
        sendMailActivity = activity;
        this.code = code;
        this.type = type;

    }

    protected void onPreExecute() {
        statusDialog =new ProgressDialog(sendMailActivity);
        if(!statusDialog.isShowing())
        statusDialog.show();
    }

    @Override
    protected Object doInBackground(Object... args) {
        try {
            GMail androidEmail = null;
            if (type == 10) {
                androidEmail = new GMail(args[0].toString(),
                        args[1].toString(), (List) args[2], args[3].toString(),
                        args[4].toString());
            } else {
                androidEmail = new GMail(args[0].toString(),
                        args[1].toString(), (List) args[2], args[3].toString(),
                        args[4].toString(), args[5].toString());
                androidEmail.addAttachment(args[5].toString());
            }
            androidEmail.createEmailMessage();
            androidEmail.sendEmail();
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
        return null;
    }

    @Override
    public void onProgressUpdate(Object... values) {
       statusDialog.setMessage("ఇమెయిల్ పంపబడుచున్నది ....");

    }

    @Override
    public void onPostExecute(Object result) {
        if(statusDialog!=null && statusDialog.isShowing())
            statusDialog.dismiss();
            sendMailActivity.finish();
            Intent in = new Intent(sendMailActivity, DashBoardActivity.class);
            sendMailActivity.startActivity(in);


    }

}