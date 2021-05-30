package app.balotsav.com.balotsavslider.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class GrantPermissions {
    Context c;

    public GrantPermissions(Context c) {
        this.c = c;
    }

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    public boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(c, Manifest.permission.INTERNET);
        int storage = ContextCompat.checkSelfPermission(c, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int loc = ContextCompat.checkSelfPermission(c, Manifest.permission.READ_EXTERNAL_STORAGE);
        int loc2 = ContextCompat.checkSelfPermission(c, Manifest.permission.ACCESS_COARSE_LOCATION);
        int loc3 = ContextCompat.checkSelfPermission(c, Manifest.permission.ACCESS_NETWORK_STATE);
        int loc4 = ContextCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.INTERNET);

        } else
            Log.i("permission", "internet");
        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        } else
            Log.i("permission", "write external");
        if (loc != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);

        } else
            Log.i("permission", "read external");
        if (loc2 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        } else
            Log.i("permission", "read external");
        if (loc3 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_NETWORK_STATE);

        } else
            Log.i("permission", "read external");
        if (loc4 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);

        } else
            Log.i("permission", "phone state");
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) c, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
}