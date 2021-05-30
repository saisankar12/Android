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
        String ex_day = pref.getString("expiryDay","");
        String today = pref.getString("today","");
        Log.i("Test - Frag",today);
        Log.i("Test - Frag",ex_day);
        String dte[] = ex_day.split("/");
        int d1 = Integer.parseInt(dte[0]);
        int d2 = Integer.parseInt(dte[1]);
        int d3 = Integer.parseInt(dte[2]);
        String dt[] = today.split("/");
        int d11 = Integer.parseInt(dt[0]);
        int d21 = Integer.parseInt(dt[1]);
        int d31 = Integer.parseInt(dt[2]);
        if (d11 <= d1 && d21 <= d2 && d31 <= d3) {
            setValue(true);
        } else {
            Toast.makeText(getContext(), getString(R.string.time_exceeded), Toast.LENGTH_LONG).show();
            setValue(false);
        }
        recyclerView.setAdapter(new EventAdapter(rootview, event, getValue(), rootview.getContext()));
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
