package app.balotsav.com.balotsavslider;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventDisplayFragment extends Fragment {

    RecyclerView recyclerView;
    //ArrayList event=new ArrayList<String>();
    ArrayList<Event> event=new ArrayList<>();Event e;FirebaseDatabase firebaseDatabase;DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ActionBar actionBar = ((DashBoard)getActivity()).getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.registration));
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        View rootview=inflater.inflate(R.layout.fragment_event_display, container,false);
        // Inflate the layout for this fragment
        recyclerView=rootview.findViewById(R.id.recycler);
        firebaseDatabase=FirebaseDatabase.getInstance();
        Initialise i=new Initialise();
        event=i.getEvent();


        /*event.add("chitralekhanam");event.add("vaktrutvam");event.add("ekapatrabhinayam");event.add("nrutyam");event.add("veshadarana");
        event.add("telugu");event.add("sangeetham");event.add("quiz");event.add("digital_chita");event.add("padyam,geethalu");event.add("mookabhinayam");event.add("eng_vaktrutvam");
        event.add("slokam");event.add("janapada");event.add("rachana");event.add("natikalu");event.add("vadya_sang");event.add("katha_rachana");event.add("spellb");
        event.add("mattitho");event.add("lekha_rachana");event.add("visleshana");event.add("thala_vadya");event.add("lagu_chitra");*/
        //Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new EventAdapter(rootview,event,1,this.getActivity().getApplicationContext()));
        return rootview;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(false);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
