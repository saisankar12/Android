package app.balotsav.com.balotsavslider;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisteredEventsFragment extends Fragment {


    RecyclerView recyclerView;
    ArrayList event=new ArrayList<String>();
    ArrayList<RegisterDetailActivityModel> registerDetailActivityModels=new ArrayList<>();
    View rootview;ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        progressDialog=new ProgressDialog(this.getContext());
        progressDialog.setMessage("Loading");progressDialog.show();

        ActionBar actionBar = ((DashBoard)getActivity()).getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.registered_events));
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        FirebaseDatabase.getInstance().getReference("Events").child(this.getActivity().getSharedPreferences("Balotsav", Context.MODE_PRIVATE).getString("scode","")).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot d:dataSnapshot.getChildren()){
                            Event e=d.getValue(Event.class);
                            RegisterDetailActivityModel registerDetailActivityModel=new RegisterDetailActivityModel();
                            if(e.isRegistered()) {
                                if (e.getTeam() == 0) {
                                    if (e.getSj() == -1 && e.getJ() != -1)
                                        registerDetailActivityModel = new RegisterDetailActivityModel(e.getName(), e.getJ(), e.getS());
                                    if (e.getSj() == -1 && e.getJ() == -1)
                                        registerDetailActivityModel = new RegisterDetailActivityModel(e.getName(), e.getS());
                                    if (e.getJ() == -1 && e.getS() == -1)
                                        registerDetailActivityModel = new RegisterDetailActivityModel(e.getName(), e.getSj(),0,0,0,0);
                                    if (e.getJ() != -1 && e.getSj() != -1)
                                        registerDetailActivityModel = new RegisterDetailActivityModel(e.getName(), e.getJ(), e.getSj(), e.getS());
                                }
                                if (e.getTeam() != 0)
                                    registerDetailActivityModel = new RegisterDetailActivityModel(e.getName(), 0, 0, 0, e.getTeam());


                                //Log.i("display",e.getTeam()+String.valueOf(e.getJ()));

                                registerDetailActivityModels.add(registerDetailActivityModel);
                            }
                        }
                        progressDialog.dismiss();
                        recyclerView.setAdapter(new EventAdapter(rootview,registerDetailActivityModels,rootview.getContext()));

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        progressDialog.dismiss();
                    }
                });
        //
        //RegisterDetailActivityModel registerDetailActivityModel1=new RegisterDetailActivityModel("Singing",0,2,3,1,2,2);
        //RegisterDetailActivityModel registerDetailActivityModel2=new RegisterDetailActivityModel("Painting",0,0,1,1,2,2);
        //registerDetailActivityModels.add(registerDetailActivityModel);
        //registerDetailActivityModels.add(registerDetailActivityModel1);
        //registerDetailActivityModels.add(registerDetailActivityModel2);
        rootview=inflater.inflate(R.layout.fragment_registered_events, container,false);
        // Inflate the layout for this fragment
        recyclerView=rootview.findViewById(R.id.recycler1);


        //Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
        LinearLayoutManager linearLayout= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayout);
        return rootview;
    }

}
