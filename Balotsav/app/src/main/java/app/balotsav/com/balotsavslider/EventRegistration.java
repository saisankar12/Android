package app.balotsav.com.balotsavslider;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventRegistration extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner spinnerSenior;
    Spinner spinnerSUBJUN;
    Spinner spinnerJUN;
    TextView team,teamno,junior,subjunior,senior,evevtname,rules;
    String[] maxnumber;Event e,e1,event;int max;ProgressDialog p;
    Button b1;FirebaseDatabase firebaseDatabase;DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.registration);
        p=new ProgressDialog(this);
        p.setMessage("Loading");
        p.show();
        spinnerJUN = findViewById(R.id.id_SpinnerJun);
        rules = findViewById(R.id.id_rules_text);
        spinnerSUBJUN = findViewById(R.id.id_SUBJUN);
        spinnerSenior = findViewById(R.id.id_Spinnersenior);
        team=findViewById(R.id.team);
        teamno=findViewById(R.id.teamno);
        junior=findViewById(R.id.junior);
        subjunior=findViewById(R.id.subjunior);
        senior=findViewById(R.id.senior);
        evevtname=findViewById(R.id.ename);
        e= (Event) getIntent().getParcelableExtra("event");max=e.max;
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Events").child(getSharedPreferences("Balotsav", Context.MODE_PRIVATE).getString("scode","")).child(e.getName());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                e1=dataSnapshot.getValue(Event.class);
                p.dismiss();
                junior.setVisibility(View.GONE);
                subjunior.setVisibility(View.GONE);
                senior.setVisibility(View.GONE);
                spinnerJUN.setVisibility(View.GONE);
                spinnerSUBJUN.setVisibility(View.GONE);
                spinnerSenior.setVisibility(View.GONE);
                team.setVisibility(View.GONE);
                teamno.setVisibility(View.GONE);
                maxnumber=new String[max+1];
                for(int i=0;i<=max;i++)
                    maxnumber[i]=String.valueOf(i);
                ArrayAdapter<String> obj = new ArrayAdapter<String>(EventRegistration.this,android.R.layout.simple_spinner_dropdown_item,maxnumber);
                b1=findViewById(R.id.id_eventregister);
                spinnerJUN.setOnItemSelectedListener(EventRegistration.this);
                spinnerJUN.setAdapter(obj);
                spinnerSUBJUN.setOnItemSelectedListener(EventRegistration.this);
                spinnerSUBJUN.setAdapter(obj);
                spinnerSenior.setOnItemSelectedListener(EventRegistration.this);
                spinnerSenior.setAdapter(obj);

                if(e1==null){
                    databaseReference.setValue(e);
                    evevtname.setText(e.getName());
                    event = e;
                    if(e.getTeam()!=0){
                        team.setVisibility(View.VISIBLE);
                        teamno.setVisibility(View.VISIBLE);
                        teamno.setText(Integer.toString(e.getTeam()));
                    }
                    else {
                        if(e.getJ()==-1 && e.getSj()==-1) {
                            senior.setVisibility(View.VISIBLE);
                            spinnerSenior.setVisibility(View.VISIBLE);
                        }
                        if(e.getJ()==-1 && e.getS()==-1) {
                            subjunior.setVisibility(View.VISIBLE);
                            spinnerSUBJUN.setVisibility(View.VISIBLE);
                        }
                        if(e.getSj()==-1 && e.getJ()!=-1) {
                            junior.setVisibility(View.VISIBLE);
                            spinnerJUN.setVisibility(View.VISIBLE);
                            senior.setVisibility(View.VISIBLE);
                            spinnerSenior.setVisibility(View.VISIBLE);
                        }
                        if(e.getJ()!=-1 && e.getS()!=-1 && e.getSj()!=-1){
                            junior.setVisibility(View.VISIBLE);
                            spinnerJUN.setVisibility(View.VISIBLE);
                            senior.setVisibility(View.VISIBLE);
                            spinnerSenior.setVisibility(View.VISIBLE);
                            subjunior.setVisibility(View.VISIBLE);
                            spinnerSUBJUN.setVisibility(View.VISIBLE);
                        }

                        spinnerJUN.setSelection(e.getJ());
                        spinnerSUBJUN.setSelection(e.getSj());
                        spinnerSenior.setSelection(e.getS());
                    }
                }
                else{
                    evevtname.setText(e1.getName());
                    event = e1;
                    if(e1.getTeam()!=0){
                        team.setVisibility(View.VISIBLE);
                        teamno.setVisibility(View.VISIBLE);
                        teamno.setText(Integer.toString(e1.getTeam()));
                    }
                    else {
                        if(e1.getJ()==-1 && e1.getSj()==-1) {
                            senior.setVisibility(View.VISIBLE);
                            spinnerSenior.setVisibility(View.VISIBLE);
                        }
                        if(e1.getJ()==-1 && e1.getS()==-1) {
                            subjunior.setVisibility(View.VISIBLE);
                            spinnerSUBJUN.setVisibility(View.VISIBLE);
                        }
                        if(e1.getSj()==-1 && e1.getJ()!=-1) {
                            junior.setVisibility(View.VISIBLE);
                            spinnerJUN.setVisibility(View.VISIBLE);
                            senior.setVisibility(View.VISIBLE);
                            spinnerSenior.setVisibility(View.VISIBLE);
                        }
                        if(e1.getJ()!=-1 && e1.getS()!=-1 && e1.getSj()!=-1){
                            junior.setVisibility(View.VISIBLE);
                            spinnerJUN.setVisibility(View.VISIBLE);
                            senior.setVisibility(View.VISIBLE);
                            spinnerSenior.setVisibility(View.VISIBLE);
                            subjunior.setVisibility(View.VISIBLE);
                            spinnerSUBJUN.setVisibility(View.VISIBLE);
                        }

                        spinnerJUN.setSelection(e1.getJ());
                        spinnerSUBJUN.setSelection(e1.getSj());
                        spinnerSenior.setSelection(e1.getS());
                    }
                    if(e1.isRegistered())
                        b1.setText(R.string.edit);
                }
                rules.setTextSize(18);

                switch(event.getName()){
                    case "చిత్రలేఖనం":
                       rules.setText(R.string.chitralekanam);

                        break;
                    case "వక్తృత్వం":
                        rules.setText(R.string.vaktrutvam);
                        break;
                    case "ఏకపాత్రాభినయం":
                        rules.setText(R.string.ekapatrabhinayem);
                        break;
                    case "శాస్త్రీయ నృత్యం":
                        rules.setText(R.string.sastriyanrutyam);
                        break;
                    case "సాంప్రదాయ వేషధారణ":
                        rules.setText(R.string.samprathayaveshadarana);
                        break;
                    case "తెలుగులోనే మాట్లాడడం":
                        rules.setText(R.string.telugulonematladudham);
                        break;
                    case "శాస్త్రీయ సంగీతం (గాత్రం)":
                        rules.setText(R.string.sastriyasangeetam);
                        break;
                    case "జనరల్ క్విజ్":
                        rules.setText(R.string.generalquiz);
                        break;
                    case "డిజిటల్ చిత్రలేఖనం":
                        rules.setText(R.string.digitalchitralekhanam);
                        break;
                    case "తెలుగు పద్యం":
                        rules.setText(R.string.telugupadhyam);
                        break;
                    case "సినీ,లలిత,జానపద గీతాలు":
                        rules.setText(R.string.sinigeethalu);
                        break;
                    case "ముఖాభినయం":
                        rules.setText(R.string.mukhabinayem);
                        break;
                    case "వక్తృత్వం (ఇంగ్లీష్)":
                        rules.setText(R.string.vaktrutvam);
                        break;
                    case "సంస్కృత శ్లోకం":
                        rules.setText(R.string.samskruthaslokam);
                        break;
                    case "జానపద నృత్యం":
                        rules.setText(R.string.janapadhanruthyam);
                        break;
                    case "కవిత రచన (తెలుగు)":
                        rules.setText(R.string.kavitharachana);
                        break;
                    case "నాటికలు":
                        rules.setText(R.string.natikalu);
                        break;
                    case "వాద్య సంగీతం (రాగ ప్రధానం)":
                        rules.setText(R.string.vadhyasangeetam);
                        break;
                    case "కథ రచన (తెలుగు)":
                        rules.setText(R.string.kadharachana);
                        break;
                    case "స్పెల్ బీ":
                        rules.setText(R.string.speelbee);
                        break;
                    case "మట్టితో బొమ్మ చేద్దాం":
                        rules.setText(R.string.mattilobommaluchedham);
                        break;
                    case "లేఖా రచన":
                        rules.setText(R.string.lekharachana);
                        break;
                    case "కథావిశ్లేషణ":
                        rules.setText(R.string.kadhavisleshana);
                        break;
                    case "వాద్య సంగీతం (తాళ ప్రధానం)":
                        rules.setText(R.string.vadhyasangeetham);
                        break;
                    case "లఘు చిత్ర విశ్లేషణ":
                        rules.setText(R.string.laghuchitravisleshana);
                        break;
                    case "జానపద నృత్యం-బృంద ప్రదర్శన":
                        rules.setText(R.string.janapadhanrutyam);
                        break;
                    case "శాస్త్రీయ నృత్యం-బృంద ప్రదర్శన":
                        rules.setText(R.string.sastriyanrutyam);
                        break;
                    case "విచిత్ర (ఫాన్సీ) వేషధారణ (సెట్టింగ్స్ లేకుండా)":
                        rules.setText(R.string.vichitraveshadarana);
                        break;
                    case "విచిత్ర (ఫాన్సీ) వేషధారణ (సెట్టింగ్స్)":
                        rules.setText(R.string.vichitraveshadarana_withsettings);
                        break;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    public void clickReset(View view) {
        if(e.getTeam()==0) {
            spinnerSenior.setSelection(0);
            spinnerSUBJUN.setSelection(0);
            spinnerJUN.setSelection(0);
            e.setSj(0);
            e.setS(0);
            e.setJ(0)
            ;e.setRegistered(false);
            databaseReference.setValue(e);
        }
        b1.setText(R.string.registration);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void clickRegister(View view) {
        b1.setText(R.string.edit);
        e.setRegistered(true);
        if(e.getTeam()==0) {
            e.setJ(spinnerJUN.getSelectedItemPosition());
            e.setSj(spinnerSUBJUN.getSelectedItemPosition());
            e.setS(spinnerSenior.getSelectedItemPosition());
        }
        Log.i("e.team", String.valueOf(e.getTeam()));
        String s=getSharedPreferences("Balotsav",MODE_PRIVATE).getString("scode","");
        if(new CheckNetwork(this).isNetworkAvailable()) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("Events").child(s).child(e.getName());
            databaseReference.setValue(e);
            Toast.makeText(this,"Successfully Registered",Toast.LENGTH_SHORT);
            finish();
        }
        else
            Toast.makeText(this,"unable to update",Toast.LENGTH_LONG).show();
    }

}
