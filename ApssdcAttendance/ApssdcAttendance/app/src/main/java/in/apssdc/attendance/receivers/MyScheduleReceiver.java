package in.apssdc.attendance.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyScheduleReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context,"Session Timeout",Toast.LENGTH_SHORT).show();
        //after 10 seconds from phone time this intent is going to fired.
        Intent i=new Intent();
        i.setAction("siva.ACTION_FINISH");
        context.sendBroadcast(i);
    }
}

//Using the <action> tag inside <activity> tag to set an action in your Manifest.xml file
//is same as setting it programmatically using intent.setAction inside the java file.

// Manifest.xml file
        /*<receiver android:name="MyReceiver" >
           <intent-filter>
                <action android:name="com.example.SendBroadcast"></action>
                <action android:name="android.intent.action.ACTION_POWER_DISCONNECTED"></action>
           </intent-filter>
        </receiver>*/

//java file

        /*Intent intent = new Intent();
        intent.setAction("com.example.SendBroadcast");
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);*/