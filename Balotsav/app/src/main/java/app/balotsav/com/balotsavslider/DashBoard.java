package app.balotsav.com.balotsavslider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class DashBoard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase firebaseDatabase;DatabaseReference databaseReference;
    SharedPreferences pref;
    String scode;
    Event e;
    List<Event> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*loadFragment(new HomeFragment());*/

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new HomeFragment())
                .commit();

        firebaseDatabase=FirebaseDatabase.getInstance();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setTitle(getResources().getString(R.string.vvit_balotsav));

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private void getData(){
        //list = new ArrayList<Integer>(61);
        arrayList = new ArrayList<>();
        SharedPreferences pref = getSharedPreferences("Balotsav",0);
        String scode = pref.getString("scode","");
        System.out.println(" SCode is :"+scode);
        databaseReference=firebaseDatabase.getReference("Events");
        databaseReference.child(scode).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    e =dataSnapshot1.getValue(Event.class);
                    Log.i("event",e.getName());
                    switch (e.getName()){

                        case "చిత్రలేఖనం":
                            Log.i("number","1 2 3");
                            arrayList.add(e);
                            break;
                        case "వక్తృత్వం":Log.i("number","4 5 6");
                            arrayList.add(e);
                            break;
                        case "ఏకపాత్రాభినయం":Log.i("number","7 8");
                            arrayList.add(e);
                            break;
                        case "శాస్త్రీయ నృత్యం":Log.i("number","9 10 11");
                            arrayList.add(e);
                            break;
                        case "సాంప్రదాయ వేషధారణ":Log.i("number","12");
                            arrayList.add(e);
                            break;
                        case "తెలుగులోనే మాట్లాడడం":Log.i("number","13 14 15");
                            arrayList.add(e);
                            break;
                        case "శాస్త్రీయ సంగీతం (గాత్రం)":Log.i("number","16 17");
                            arrayList.add(e);
                            break;
                        case "జనరల్ క్విజ్":Log.i("number","18");
                            arrayList.add(e);
                            break;
                        case "డిజిటల్ చిత్రలేఖనం":Log.i("number","19 20 21");
                            arrayList.add(e);
                            break;
                        case "తెలుగు పద్యం":Log.i("number","22 23 24");
                            arrayList.add(e);
                            break;
                        case "సినీ,లలిత,జానపద గీతాలు":Log.i("number","25 26 27");
                            arrayList.add(e);
                            break;
                        case "ముఖాభినయం":Log.i("number","28 29");
                            arrayList.add(e);
                            break;
                        case "వక్తృత్వం (ఇంగ్లీష్)":Log.i("number","30 31 032");
                            arrayList.add(e);
                            break;
                        case "సంస్కృత శ్లోకం":Log.i("number","33");
                            arrayList.add(e);
                            break;
                        case "జానపద నృత్యం":Log.i("number","34 35 36");
                            arrayList.add(e);
                            break;
                        case "కవిత రచన (తెలుగు)":Log.i("number","37 38");
                            arrayList.add(e);
                            break;
                        case "నాటికలు":Log.i("number","39");
                            arrayList.add(e);
                            break;
                        case "వాద్య సంగీతం (రాగ ప్రధానం)":Log.i("number","40 41 42");
                            arrayList.add(e);
                            break;
                        case "కథ రచన (తెలుగు)":Log.i("number","43 44");
                            arrayList.add(e);
                            break;
                        case "స్పెల్ బీ":Log.i("number","45");
                            arrayList.add(e);
                            break;
                        case "మట్టితో బొమ్మ చేద్దాం":Log.i("number","46");
                            arrayList.add(e);
                            break;
                        case "లేఖా రచన":Log.i("number","47 48 49");
                            arrayList.add(e);
                            break;
                        case "కథావిశ్లేషణ":Log.i("number","50 51");
                            arrayList.add(e);
                            break;
                        case "వాద్య సంగీతం (తాళ ప్రధానం)":Log.i("number","52 53 54");
                            arrayList.add(e);
                            break;
                        case "లఘు చిత్ర విశ్లేషణ":Log.i("number","55 56");
                            arrayList.add(e);
                            break;
                        case "జానపద నృత్యం-బృంద ప్రదర్శన":Log.i("number","57");
                            arrayList.add(e);
                            break;
                        case "శాస్త్రీయ నృత్యం-బృంద ప్రదర్శన":Log.i("number","58");
                            arrayList.add(e);
                            break;
                        case "విచిత్ర (ఫాన్సీ) వేషధారణ (సెట్టింగ్స్ లేకుండా)":Log.i("number","59");
                            arrayList.add(e);
                            break;
                        case "విచిత్ర (ఫాన్సీ) వేషధారణ (సెట్టింగ్స్)":Log.i("number","60");
                            arrayList.add(e);
                            break;

                    }
                }
                generatePDF();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
    private void generatePDF() {
        try{
            PdfReader reader = new PdfReader(getResources().openRawResource(R.raw.balotsav_fill_able));
            AcroFields fields = reader.getAcroFields();

            Set<String> fldNames = fields.getFields().keySet();

            for (String fldName : fldNames) {
                System.out.println( fldName + ": " + fields.getField( fldName ) );
            }
            System.out.println(" SCode is :"+scode);
            if(new GrantPermissions(this).checkAndRequestPermissions()) {
                String path1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Balotsav.pdf";
                Log.i("path",path1);
                File file = new File(path1);
                OutputStream outputStream = new FileOutputStream(file);
                PdfStamper stamper = new PdfStamper(reader, outputStream, '\0');
                final AcroFields acroFields = stamper.getAcroFields();
                acroFields.setField("school_id", scode);
                Gson gson=new Gson();
                String json=getSharedPreferences("Balotsav",MODE_PRIVATE).getString("data","");
                Schools schools=gson.fromJson(json,Schools.class);
                Log.i("json",json);
                acroFields.setField("school_name",schools.getSchool_Name());
                acroFields.setField("school_city",schools.getTown());
                acroFields.setField("school_district",schools.getDistrict());
                acroFields.setField("principal_name",schools.getHeadMaster_Name());
                acroFields.setField("principal_mobile_no",schools.getHeadMaster_PhNo());
                acroFields.setField("principal_email_id",schools.getHeadMaster_Email());
                acroFields.setField("teacher_name",schools.getCoordinator_Name());
                acroFields.setField("teacher_mobile_no",schools.getCoordinator_PhNo());
                for(int i=0;i<arrayList.size();i++){
                    Event e=arrayList.get(i);
                    Log.i("print e",e.getName());
                    switch (e.getName()){

                        case "చిత్రలేఖనం":
                            if(e.getSj()!=0 && e.getJ()!=0 && e.getS()!=0) {
                                acroFields.setField("1", String.valueOf(e.getSj()));
                                acroFields.setField("2", String.valueOf(e.getJ()));
                                acroFields.setField("3", String.valueOf(e.getS()));
                            }
                            break;
                        case "వక్తృత్వం":
                            if(e.getSj()!=0 && e.getJ()!=0 && e.getS()!=0) {
                                acroFields.setField("4", String.valueOf(e.getSj()));
                                acroFields.setField("5", String.valueOf(e.getJ()));
                                acroFields.setField("", String.valueOf(e.getS()));
                            }
                            break;
                        case "ఏకపాత్రాభినయం":
                            if(e.getJ()!=0 && e.getS()!=0 ) {
                                acroFields.setField("7", String.valueOf(e.getJ()));
                                acroFields.setField("8", String.valueOf(e.getS()));
                            }
                            break;
                        case "శాస్త్రీయ నృత్యం":
                            if(e.getSj()!=0 && e.getJ()!=0 && e.getS()!=0) {
                                acroFields.setField("9", String.valueOf(e.getSj()));
                                acroFields.setField("10", String.valueOf(e.getJ()));
                                acroFields.setField("11", String.valueOf(e.getS()));
                            }
                            break;
                        case "సాంప్రదాయ వేషధారణ":
                            if(e.getSj()!=0) {
                                acroFields.setField("12", String.valueOf(e.getSj()));
                            }
                            break;
                        case "తెలుగులోనే మాట్లాడడం":
                            if(e.getSj()!=0 && e.getJ()!=0 && e.getS()!=0) {
                                acroFields.setField("13", String.valueOf(e.getSj()));
                                acroFields.setField("14", String.valueOf(e.getJ()));
                                acroFields.setField("15", String.valueOf(e.getS()));
                            }
                            break;
                        case "శాస్త్రీయ సంగీతం (గాత్రం)":
                            if(e.getJ()!=0 && e.getS()!=0) {
                                acroFields.setField("16", String.valueOf(e.getJ()));
                                acroFields.setField("17", String.valueOf(e.getS()));
                            }
                            break;
                        case "జనరల్ క్విజ్"://Log.i("number","18");
                            acroFields.setField("18"," ");
                            if(e.getTeam()!=0) {
                                acroFields.setField("18", String.valueOf(e.getTeam()));
                            }
                            break;
                        case "డిజిటల్ చిత్రలేఖనం":
                            if(e.getSj()!=0 && e.getJ()!=0 && e.getS()!=0) {
                                acroFields.setField("19", String.valueOf(e.getSj()));
                                acroFields.setField("20", String.valueOf(e.getJ()));
                                acroFields.setField("21", String.valueOf(e.getS()));
                            }
                            break;
                        case "తెలుగు పద్యం":
                            if(e.getSj()!=0 && e.getJ()!=0 && e.getS()!=0) {
                                acroFields.setField("22", String.valueOf(e.getSj()));
                                acroFields.setField("23", String.valueOf(e.getJ()));
                                acroFields.setField("24", String.valueOf(e.getS()));
                            }
                            break;
                        case "సినీ,లలిత,జానపద గీతాలు":
                            if(e.getSj()!=0 && e.getJ()!=0 && e.getS()!=0) {
                                acroFields.setField("25", String.valueOf(e.getSj()));
                                acroFields.setField("26", String.valueOf(e.getJ()));
                                acroFields.setField("27", String.valueOf(e.getS()));
                            }
                            break;
                        case "ముఖాభినయం":
                            if(e.getJ()!=0 && e.getS()!=0) {
                                acroFields.setField("28", String.valueOf(e.getJ()));
                                acroFields.setField("29", String.valueOf(e.getS()));
                            }
                            break;
                        case "వక్తృత్వం (ఇంగ్లీష్)":
                            if(e.getSj()!=0 && e.getJ()!=0 && e.getS()!=0) {
                                acroFields.setField("30", String.valueOf(e.getSj()));
                                acroFields.setField("31", String.valueOf(e.getJ()));
                                acroFields.setField("32", String.valueOf(e.getS()));
                            }
                            break;
                        case "సంస్కృత శ్లోకం":
                            if(e.getSj()!=0) {
                                acroFields.setField("33", String.valueOf(e.getSj()));
                            }
                            break;
                        case "జానపద నృత్యం":
                            if(e.getSj()!=0 && e.getJ()!=0 && e.getS()!=0) {
                                acroFields.setField("34", String.valueOf(e.getSj()));
                                acroFields.setField("35", String.valueOf(e.getJ()));
                                acroFields.setField("36", String.valueOf(e.getS()));
                            }
                            break;
                        case "కవిత రచన (తెలుగు)":
                            if(e.getJ()!=0 && e.getS()!=0) {
                                acroFields.setField("37", String.valueOf(e.getJ()));
                                acroFields.setField("38", String.valueOf(e.getS()));
                            }
                            break;
                        case "నాటికలు":
                            if(e.getTeam()!=0)
                                acroFields.setField("39",String.valueOf(e.getTeam()));
                            break;
                        case "వాద్య సంగీతం (రాగ ప్రధానం)":
                            if(e.getSj()!=0 && e.getJ()!=0 && e.getS()!=0) {
                                acroFields.setField("40", String.valueOf(e.getSj()));
                                acroFields.setField("41", String.valueOf(e.getJ()));
                                acroFields.setField("42", String.valueOf(e.getS()));
                            }
                            break;
                        case "కథ రచన (తెలుగు)":
                            if(e.getJ()!=0 && e.getS()!=0) {
                                acroFields.setField("43", String.valueOf(e.getJ()));
                                acroFields.setField("44", String.valueOf(e.getS()));
                            }
                            break;
                        case "స్పెల్ బీ":
                            if(e.getS()!=0)
                                acroFields.setField("45",String.valueOf(e.getS()));
                            break;
                        case "మట్టితో బొమ్మ చేద్దాం":
                            if(e.getTeam()!=0)
                                acroFields.setField("46",String.valueOf(e.getTeam()));
                            break;
                        case "లేఖా రచన":
                            if(e.getSj()!=0 && e.getJ()!=0 && e.getS()!=0) {
                                acroFields.setField("47", String.valueOf(e.getSj()));
                                acroFields.setField("48", String.valueOf(e.getJ()));
                                acroFields.setField("49", String.valueOf(e.getS()));
                            }
                            break;
                        case "కథావిశ్లేషణ":
                            if(e.getJ()!=0 && e.getS()!=0) {
                                acroFields.setField("50", String.valueOf(e.getJ()));
                                acroFields.setField("51", String.valueOf(e.getS()));
                            }
                            break;
                        case "వాద్య సంగీతం (తాళ ప్రధానం)"://Log.i("number","52 53 54");
                            if(e.getSj()!=0 && e.getJ()!=0 && e.getS()!=0) {
                                acroFields.setField("52", String.valueOf(e.getSj()));
                                acroFields.setField("53", String.valueOf(e.getJ()));
                                acroFields.setField("54", String.valueOf(e.getS()));
                            }
                            break;
                        case "లఘు చిత్ర విశ్లేషణ"://Log.i("number","55 56");
                            if(e.getJ()!=0 && e.getS()!=0) {
                                acroFields.setField("55", String.valueOf(e.getJ()));
                                acroFields.setField("56", String.valueOf(e.getS()));
                            }
                            break;
                        case "జానపద నృత్యం-బృంద ప్రదర్శన"://Log.i("number","57");
                            acroFields.setField("57",String.valueOf(e.getTeam()));
                            break;
                        case "శాస్త్రీయ నృత్యం-బృంద ప్రదర్శన"://Log.i("number","58");
                            acroFields.setField("58",String.valueOf(e.getTeam()));
                            break;
                        case "విచిత్ర (ఫాన్సీ) వేషధారణ (సెట్టింగ్స్ లేకుండా)"://Log.i("number","59");
                            acroFields.setField("59",String.valueOf(e.getTeam()));
                            break;
                        case "విచిత్ర (ఫాన్సీ) వేషధారణ (సెట్టింగ్స్)"://Log.i("number","60");
                            acroFields.setField("60",String.valueOf(e.getTeam()));
                            break;

                    }
                }
                stamper.setFormFlattening(true);
                stamper.close();
                reader.close();
                outputStream.close();

                Intent i = new Intent(DashBoard.this,PdfView.class);
                startActivity(i);
            }
            else
                Log.i("no permissions","no permissions");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(this.getFragmentManager().getBackStackEntryCount()>0){
            this.getFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SharedPreferences.Editor editor=getSharedPreferences("Balotsav",MODE_PRIVATE).edit();
            editor.putInt("registered",0);editor.apply();
            startActivity(new Intent(DashBoard.this,Login.class));
            finish();
        }
        else if(id == R.id.action_print){
            getData();
        }
        else if(id == R.id.action_rules){
            Fragment fragment = new RulesFragment();
            loadFragment(fragment);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment=null;//vs
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        if (id == R.id.nav_edit) {
            // Handle the camera action
            fragment =new RegisteredEventsFragment();//vs
            Toast.makeText(this, "registerd events", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_event) {
            fragment=new EventDisplayFragment();//vs
            Toast.makeText(this, "Total events", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_results) {
            Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
            fragment=new ResultsFragment();
        } else if (id == R.id.nav_notifications) {
            Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
            fragment=new AnnouncementFragment();
        } else if (id == R.id.nav_rules) {
            fragment=new RulesFragment();
        }
        else if (id == R.id.nav_home) {
            fragment=new HomeFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return loadFragment(fragment);
    }
    //vs
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            this.getFragmentManager().popBackStack();
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


}
