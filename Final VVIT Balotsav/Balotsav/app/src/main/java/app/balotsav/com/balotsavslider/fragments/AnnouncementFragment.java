package app.balotsav.com.balotsavslider.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import app.balotsav.com.balotsavslider.activities.DashBoardActivity;
import app.balotsav.com.balotsavslider.R;
import app.balotsav.com.balotsavslider.model.Announcement;
import app.balotsav.com.balotsavslider.utils.AnnouncementAdapter;
import app.balotsav.com.balotsavslider.utils.CheckNetwork;


public class AnnouncementFragment extends Fragment {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<Announcement> arrayList;
    RecyclerView recyclerView;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ActionBar actionBar = ((DashBoardActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.announcements));
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        final View rootview = inflater.inflate(R.layout.fragment_announcement, container, false);
        arrayList = new ArrayList<>();
        context = getActivity();
        recyclerView = rootview.findViewById(R.id.announce);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        if(new CheckNetwork(getContext()).isNetworkAvailable()) {
            databaseReference.child("Announcements").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        if (dataSnapshot1.getChildrenCount() != 0)
                            arrayList.add(dataSnapshot1.getValue(Announcement.class));
                    }
                    Collections.reverse(arrayList);
                    recyclerView.setAdapter(new AnnouncementAdapter(arrayList));
                    recyclerView.smoothScrollToPosition(0);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else
        {
            Toast.makeText(getContext(),R.string.check_connection,Toast.LENGTH_SHORT).show();
        }
        return rootview;
    }
}
