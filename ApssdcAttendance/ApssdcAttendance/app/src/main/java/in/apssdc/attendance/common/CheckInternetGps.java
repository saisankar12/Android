package in.apssdc.attendance.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;

import java.util.List;

import in.apssdc.attendance.R;

public class CheckInternetGps {

    private Context context;

    public CheckInternetGps(Context context)
    {
        this.context=context;
    }

    public void checkDialogForGps()
    {
        try{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setMessage("GPS is disabled in your device");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("Goto Settings to Enable",new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id)
                {
                    Intent callGPSSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(callGPSSettingIntent);
                }
            });
            alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = alertDialogBuilder.create();
            alert.getWindow().setBackgroundDrawableResource(R.color.alert_background);
            alert.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void callInternetAlert()
    {
        try{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setMessage("InternetConnection is disabled in your device.Would you like to enable it?");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("Goto Settings to Enable", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    context.startActivity(new Intent(Settings.ACTION_SETTINGS));
                }
            });
            alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = alertDialogBuilder.create();
            alert.getWindow().setBackgroundDrawableResource(R.color.alert_background);
            alert.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
