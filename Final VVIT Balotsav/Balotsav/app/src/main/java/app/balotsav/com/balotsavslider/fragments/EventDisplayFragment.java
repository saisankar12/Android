package app.balotsav.com.balotsavslider.fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import app.balotsav.com.balotsavslider.R;
import app.balotsav.com.balotsavslider.activities.DashBoardActivity;
import app.balotsav.com.balotsavslider.model.Event;
import app.balotsav.com.balotsavslider.model.Initialise;
import app.balotsav.com.balotsavslider.utils.EventAdapter;
import app.balotsav.com.balotsavslider.utils.GrantPermissions;


public class EventDisplayFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Event> event = new ArrayList<>();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private View rootview;
    String result;
    private SharedPreferences pref;

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    Boolean value;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ActionBar actionBar = ((DashBoardActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.registration));
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);

        pref = getActivity().getSharedPreferences("Balotsav", 0);

        rootview = inflater.inflate(R.layout.fragment_event_display, container, false);
        // Inflate the layout for this fragment
        recyclerView = rootview.findViewById(R.id.recycler);
        Initialise i = new Initialise();
        event = i.getEvent();
        getTime();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootview;
    }

    public void getTime() {
        try {
            if (new GrantPermissions(getActivity()).checkAndRequestPermissions()) {
                LocationManager locMan = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                    return;
                }
                long time = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getTime();
                Log.i("Time", String.valueOf(time));
                final Date today = new Date(time);
                result = pref.getString("expiryDay", "");
                String dte[] = result.split("/");
                int d1 = Integer.parseInt(dte[0]);
                int d2 = Integer.parseInt(dte[1]);
                int d3 = Integer.parseInt(dte[2]);
                if (today.getDate() <= d1 && today.getMonth() <= d2 && today.getYear() <= d3) {
                    setValue(false);
                } else {
                    Toast.makeText(getContext(), getString(R.string.time_exceeded), Toast.LENGTH_LONG).show();
                    setValue(true);
                }
                recyclerView.setAdapter(new EventAdapter(rootview, event, getValue(), rootview.getContext()));
            } else {
                Toast.makeText(getContext(), "Enable GPS location access", Toast.LENGTH_SHORT).show();

            }
        }
        catch(Exception e){
            Toast.makeText(getContext(), "Enable GPS location", Toast.LENGTH_SHORT).show(); Toast.makeText(getContext(), "Enable GPS location access", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
            alertDialogBuilder.setMessage("GPS is disabled in your device");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("Goto Settings to Enable",new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id)
                {
                    Intent callGPSSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    getContext().startActivity(callGPSSettingIntent);
                }
            });
            alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = alertDialogBuilder.create();
            alert.getWindow().setBackgroundDrawableResource(R.color.cardview_light_background);
            alert.show();
            //recyclerView.setAdapter(new EventAdapter(rootview, event, getValue(), rootview.getContext()));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(false);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
