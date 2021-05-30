package app.balotsav.com.balotsavslider.fragments;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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
import java.util.Date;
import java.util.List;
import java.util.Set;

import app.balotsav.com.balotsavslider.R;
import app.balotsav.com.balotsavslider.activities.DashBoardActivity;
import app.balotsav.com.balotsavslider.model.Event;
import app.balotsav.com.balotsavslider.model.RegisterDetail;
import app.balotsav.com.balotsavslider.model.Schools;
import app.balotsav.com.balotsavslider.utils.EventAdapter;
import app.balotsav.com.balotsavslider.utils.GrantPermissions;
import app.balotsav.com.balotsavslider.utils.SendMailTask;
import app.balotsav.com.balotsavslider.utils.Timer;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisteredEventsFragment extends Fragment {


    RecyclerView recyclerView;
    ArrayList event = new ArrayList<String>();
    ArrayList<RegisterDetail> registerDetailActivityModels = new ArrayList<>();
    View rootview;
    ProgressDialog progressDialog;
    Button submit;
    TextView noevent;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String scode;
    Event e;
    List<Event> arrayList;
    private Schools schools;
    public static final String TAG_FRAGMENT = "REGISTER_EVENT_FRAGMENT";

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_registered_events, container, false);
        progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Loading");
        progressDialog.show();
        new Timer(progressDialog, getActivity()).count();
        progressDialog.setCancelable(false);
        Gson gson = new Gson();
        noevent = rootview.findViewById(R.id.id_noEvents);
        String json = getActivity().getSharedPreferences("Balotsav", MODE_PRIVATE).getString("data", "");
        schools = gson.fromJson(json, Schools.class);
        submit = rootview.findViewById(R.id.id_submit);
        ActionBar actionBar = ((DashBoardActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.registered_events));

        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        String text = this.getActivity().getSharedPreferences("Balotsav", MODE_PRIVATE).getString("scode", "").replaceAll("/","@");
        Log.i("Test-RegisteredEvents",text);
        FirebaseDatabase.getInstance().getReference("Events").child(text).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            if (d.getChildrenCount() != 0) {
                                Event e = d.getValue(Event.class);
                                RegisterDetail registerDetailActivityModel = new RegisterDetail();
                                if (e.isRegistered()) {
                                    if (e.getTeam() == 0) {
                                        if (e.getSj() == -1 && e.getJ() != -1)
                                            registerDetailActivityModel = new RegisterDetail(e.getName(), e.getJ(), e.getS());
                                        if (e.getSj() == -1 && e.getJ() == -1)
                                            registerDetailActivityModel = new RegisterDetail(e.getName(), e.getS());
                                        if (e.getJ() == -1 && e.getS() == -1)
                                            registerDetailActivityModel = new RegisterDetail(e.getName(), e.getSj(), 0, 0, 0, 0);
                                        if (e.getJ() != -1 && e.getSj() != -1)
                                            registerDetailActivityModel = new RegisterDetail(e.getName(), e.getJ(), e.getSj(), e.getS());
                                    }
                                    if (e.getTeam() != 0)
                                        registerDetailActivityModel = new RegisterDetail(e.getName(), 0, 0, 0, e.getTeam());
                                    registerDetailActivityModels.add(registerDetailActivityModel);
                                }
                            } else
                                rootview = inflater.inflate(R.layout.noevents, container, false);
                        }
                        progressDialog.dismiss();
                        if (registerDetailActivityModels.size() == 0) {
                            noevent.setVisibility(View.VISIBLE);
                            submit.setVisibility(View.GONE);

                        }

                        recyclerView.setAdapter(new EventAdapter(rootview, registerDetailActivityModels, rootview.getContext()));

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        progressDialog.dismiss();
                    }
                });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.registration_completed);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(),R.string.sending_final_email,Toast.LENGTH_LONG).show();
                        getData();
                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().onBackPressed();
                    }
                });
                builder.show();
                builder.setCancelable(false);
            }
        });
        // Inflate the layout for this fragment
        recyclerView = rootview.findViewById(R.id.recycler1);

        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayout);
        return rootview;
    }

    private void getData() {
        arrayList = new ArrayList<>();
        SharedPreferences pref = this.getActivity().getSharedPreferences("Balotsav", 0);
        String scode = pref.getString("scode", "");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Events");
        databaseReference.child(scode.replace("/","@")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    e = dataSnapshot1.getValue(Event.class);

                    if(e.isRegistered()) {
                        switch (e.getName()) {

                            case "చిత్రలేఖనం":
                                Log.i("number", "1 2 3");
                                arrayList.add(e);
                                break;
                            case "వక్తృత్వం":
                                Log.i("number", "4 5 6");
                                arrayList.add(e);
                                break;
                            case "ఏకపాత్రాభినయం":
                                Log.i("number", "7 8");
                                arrayList.add(e);
                                break;
                            case "శాస్త్రీయ నృత్యం":
                                Log.i("number", "9 10 11");
                                arrayList.add(e);
                                break;
                            case "సాంప్రదాయ వేషధారణ":
                                Log.i("number", "12");
                                arrayList.add(e);
                                break;
                            case "తెలుగులోనే మాట్లాడడం":
                                Log.i("number", "13 14 15");
                                arrayList.add(e);
                                break;
                            case "శాస్త్రీయ సంగీతం (గాత్రం)":
                                Log.i("number", "16 17");
                                arrayList.add(e);
                                break;
                            case "జనరల్ క్విజ్":
                                Log.i("number", "18");
                                arrayList.add(e);
                                break;
                            case "డిజిటల్ చిత్రలేఖనం":
                                Log.i("number", "19 20 21");
                                arrayList.add(e);
                                break;
                            case "తెలుగు పద్యం":
                                Log.i("number", "22 23 24");
                                arrayList.add(e);
                                break;
                            case "సినీ,లలిత,జానపద గీతాలు":
                                Log.i("number", "25 26 27");
                                arrayList.add(e);
                                break;
                            case "ముఖాభినయం":
                                Log.i("number", "28 29");
                                arrayList.add(e);
                                break;
                            case "వక్తృత్వం (ఇంగ్లీష్)":
                                Log.i("number", "30 31 032");
                                arrayList.add(e);
                                break;
                            case "సంస్కృత శ్లోకం":
                                Log.i("number", "33");
                                arrayList.add(e);
                                break;
                            case "జానపద నృత్యం":
                                Log.i("number", "34 35 36");
                                arrayList.add(e);
                                break;
                            case "కవిత రచన (తెలుగు)":
                                Log.i("number", "37 38");
                                arrayList.add(e);
                                break;
                            case "నాటికలు":
                                Log.i("number", "39");
                                arrayList.add(e);
                                break;
                            case "వాద్య సంగీతం (రాగ ప్రధానం)":
                                Log.i("number", "40 41 42");
                                arrayList.add(e);
                                break;
                            case "కథ రచన (తెలుగు)":
                                Log.i("number", "43 44");
                                arrayList.add(e);
                                break;
                            case "స్పెల్ బీ":
                                Log.i("number", "45");
                                arrayList.add(e);
                                break;
                            case "మట్టితో బొమ్మ చేద్దాం":
                                Log.i("number", "46");
                                arrayList.add(e);
                                break;
                            case "లేఖా రచన":
                                Log.i("number", "47 48 49");
                                arrayList.add(e);
                                break;
                            case "కథావిశ్లేషణ":
                                Log.i("number", "50 51");
                                arrayList.add(e);
                                break;
                            case "వాద్య సంగీతం (తాళ ప్రధానం)":
                                Log.i("number", "52 53 54");
                                arrayList.add(e);
                                break;
                            case "లఘు చిత్ర విశ్లేషణ":
                                Log.i("number", "55 56");
                                arrayList.add(e);
                                break;
                            case "జానపద నృత్యం-బృంద ప్రదర్శన":
                                Log.i("number", "57");
                                arrayList.add(e);
                                break;
                            case "శాస్త్రీయ నృత్యం-బృంద ప్రదర్శన":
                                Log.i("number", "58");
                                arrayList.add(e);
                                break;
                            case "విచిత్ర (ఫాన్సీ) వేషధారణ (సెట్టింగ్స్ లేకుండా)":
                                Log.i("number", "59");
                                arrayList.add(e);
                                break;
                            case "విచిత్ర (ఫాన్సీ) వేషధారణ (సెట్టింగ్స్)":
                                Log.i("number", "60");
                                arrayList.add(e);
                                break;
                        }
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
        try {
            PdfReader reader = new PdfReader(getResources().openRawResource(R.raw.balotsav_form));
            AcroFields fields = reader.getAcroFields();

            Set<String> fldNames = fields.getFields().keySet();

            if (new GrantPermissions(getActivity()).checkAndRequestPermissions()) {
                String path1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Balotsav.pdf";
                Log.i("path", path1);
                File file = new File(path1);
                OutputStream outputStream = new FileOutputStream(file);
                PdfStamper stamper = new PdfStamper(reader, outputStream, '\0');
                final AcroFields acroFields = stamper.getAcroFields();
                acroFields.setField("school_name", schools.getSchool_Name());
                acroFields.setField("school_id", schools.getSchool_Code());
                acroFields.setField("school_city", schools.getTown());
                acroFields.setField("school_district", schools.getDistrict());
                acroFields.setField("principal_name", schools.getHeadMaster_Name());
                acroFields.setField("principal_mobile_no", schools.getHeadMaster_PhNo());
                acroFields.setField("principal_email_id", schools.getHeadMaster_Email());
                acroFields.setField("teacher_name", schools.getCoordinator_Name());
                acroFields.setField("teacher_mobile_no", schools.getCoordinator_PhNo());
                Log.i("print e", schools.getSchool_Name());
                Log.i("print e", schools.getSchool_Code());
                Log.i("print e", schools.getCoordinator_Name());
                Log.i("print e", schools.getCoordinator_PhNo());
                Log.i("print e", schools.getDistrict());
                Log.i("print e", schools.getHeadMaster_Name());
                Log.i("print e", schools.getHeadMaster_Email());
                Log.i("print e", schools.getHeadMaster_PhNo());
                Log.i("print e", schools.getTown());

                for (int i = 0; i < arrayList.size(); i++) {
                    Event e = arrayList.get(i);
                    Log.i("print e", e.getName());
                    switch (e.getName()) {

                        case "చిత్రలేఖనం":
                            if (e.getSj() != 0 && e.getJ() != 0 && e.getS() != 0) {
                                acroFields.setField("1", String.valueOf(e.getSj()));
                                acroFields.setField("2", String.valueOf(e.getJ()));
                                acroFields.setField("3", String.valueOf(e.getS()));
                            }
                            break;
                        case "వక్తృత్వం":
                            if (e.getSj() != 0 && e.getJ() != 0 && e.getS() != 0) {
                                acroFields.setField("4", String.valueOf(e.getSj()));
                                acroFields.setField("5", String.valueOf(e.getJ()));
                                acroFields.setField("", String.valueOf(e.getS()));
                            }
                            break;
                        case "ఏకపాత్రాభినయం":
                            if (e.getJ() != 0 && e.getS() != 0) {
                                acroFields.setField("7", String.valueOf(e.getJ()));
                                acroFields.setField("8", String.valueOf(e.getS()));
                            }
                            break;
                        case "శాస్త్రీయ నృత్యం":
                            if (e.getSj() != 0 && e.getJ() != 0 && e.getS() != 0) {
                                acroFields.setField("9", String.valueOf(e.getSj()));
                                acroFields.setField("10", String.valueOf(e.getJ()));
                                acroFields.setField("11", String.valueOf(e.getS()));
                            }
                            break;
                        case "సాంప్రదాయ వేషధారణ":
                            if (e.getSj() != 0) {
                                acroFields.setField("12", String.valueOf(e.getSj()));
                            }
                            break;
                        case "తెలుగులోనే మాట్లాడడం":
                            if (e.getSj() != 0 && e.getJ() != 0 && e.getS() != 0) {
                                acroFields.setField("13", String.valueOf(e.getSj()));
                                acroFields.setField("14", String.valueOf(e.getJ()));
                                acroFields.setField("15", String.valueOf(e.getS()));
                            }
                            break;
                        case "శాస్త్రీయ సంగీతం (గాత్రం)":
                            if (e.getJ() != 0 && e.getS() != 0) {
                                acroFields.setField("16", String.valueOf(e.getJ()));
                                acroFields.setField("17", String.valueOf(e.getS()));
                            }
                            break;
                        case "జనరల్ క్విజ్"://Log.i("number","18");
                            acroFields.setField("18", " ");
                            if (e.getTeam() != 0) {
                                acroFields.setField("18", String.valueOf(e.getTeam()));
                            }
                            break;
                        case "డిజిటల్ చిత్రలేఖనం":
                            if (e.getSj() != 0 && e.getJ() != 0 && e.getS() != 0) {
                                acroFields.setField("19", String.valueOf(e.getSj()));
                                acroFields.setField("20", String.valueOf(e.getJ()));
                                acroFields.setField("21", String.valueOf(e.getS()));
                            }
                            break;
                        case "తెలుగు పద్యం":
                            if (e.getSj() != 0 && e.getJ() != 0 && e.getS() != 0) {
                                acroFields.setField("22", String.valueOf(e.getSj()));
                                acroFields.setField("23", String.valueOf(e.getJ()));
                                acroFields.setField("24", String.valueOf(e.getS()));
                            }
                            break;
                        case "సినీ,లలిత,జానపద గీతాలు":
                            if (e.getSj() != 0 && e.getJ() != 0 && e.getS() != 0) {
                                acroFields.setField("25", String.valueOf(e.getSj()));
                                acroFields.setField("26", String.valueOf(e.getJ()));
                                acroFields.setField("27", String.valueOf(e.getS()));
                            }
                            break;
                        case "ముఖాభినయం":
                            if (e.getJ() != 0 && e.getS() != 0) {
                                acroFields.setField("28", String.valueOf(e.getJ()));
                                acroFields.setField("29", String.valueOf(e.getS()));
                            }
                            break;
                        case "వక్తృత్వం (ఇంగ్లీష్)":
                            if (e.getSj() != 0 && e.getJ() != 0 && e.getS() != 0) {
                                acroFields.setField("30", String.valueOf(e.getSj()));
                                acroFields.setField("31", String.valueOf(e.getJ()));
                                acroFields.setField("32", String.valueOf(e.getS()));
                            }
                            break;
                        case "సంస్కృత శ్లోకం":
                            if (e.getSj() != 0) {
                                acroFields.setField("33", String.valueOf(e.getSj()));
                            }
                            break;
                        case "జానపద నృత్యం":
                            if (e.getSj() != 0 && e.getJ() != 0 && e.getS() != 0) {
                                acroFields.setField("34", String.valueOf(e.getSj()));
                                acroFields.setField("35", String.valueOf(e.getJ()));
                                acroFields.setField("36", String.valueOf(e.getS()));
                            }
                            break;
                        case "కవిత రచన (తెలుగు)":
                            if (e.getJ() != 0 && e.getS() != 0) {
                                acroFields.setField("37", String.valueOf(e.getJ()));
                                acroFields.setField("38", String.valueOf(e.getS()));
                            }
                            break;
                        case "నాటికలు":
                            if (e.getTeam() != 0)
                                acroFields.setField("39", String.valueOf(e.getTeam()));
                            break;
                        case "వాద్య సంగీతం (రాగ ప్రధానం)":
                            if (e.getSj() != 0 && e.getJ() != 0 && e.getS() != 0) {
                                acroFields.setField("40", String.valueOf(e.getSj()));
                                acroFields.setField("41", String.valueOf(e.getJ()));
                                acroFields.setField("42", String.valueOf(e.getS()));
                            }
                            break;
                        case "కథ రచన (తెలుగు)":
                            if (e.getJ() != 0 && e.getS() != 0) {
                                acroFields.setField("43", String.valueOf(e.getJ()));
                                acroFields.setField("44", String.valueOf(e.getS()));
                            }
                            break;
                        case "స్పెల్ బీ":
                            if (e.getS() != 0)
                                acroFields.setField("45", String.valueOf(e.getS()));
                            break;
                        case "మట్టితో బొమ్మ చేద్దాం":
                            if (e.getTeam() != 0)
                                acroFields.setField("46", String.valueOf(e.getTeam()));
                            break;
                        case "లేఖా రచన":
                            if (e.getSj() != 0 && e.getJ() != 0 && e.getS() != 0) {
                                acroFields.setField("47", String.valueOf(e.getSj()));
                                acroFields.setField("48", String.valueOf(e.getJ()));
                                acroFields.setField("49", String.valueOf(e.getS()));
                            }
                            break;
                        case "కథావిశ్లేషణ":
                            if (e.getJ() != 0 && e.getS() != 0) {
                                acroFields.setField("50", String.valueOf(e.getJ()));
                                acroFields.setField("51", String.valueOf(e.getS()));
                            }
                            break;
                        case "వాద్య సంగీతం (తాళ ప్రధానం)"://Log.i("number","52 53 54");
                            if (e.getSj() != 0 && e.getJ() != 0 && e.getS() != 0) {
                                acroFields.setField("52", String.valueOf(e.getSj()));
                                acroFields.setField("53", String.valueOf(e.getJ()));
                                acroFields.setField("54", String.valueOf(e.getS()));
                            }
                            break;
                        case "లఘు చిత్ర విశ్లేషణ"://Log.i("number","55 56");
                            if (e.getJ() != 0 && e.getS() != 0) {
                                acroFields.setField("55", String.valueOf(e.getJ()));
                                acroFields.setField("56", String.valueOf(e.getS()));
                            }
                            break;
                        case "జానపద నృత్యం-బృంద ప్రదర్శన"://Log.i("number","57");
                            acroFields.setField("57", String.valueOf(e.getTeam()));
                            break;
                        case "శాస్త్రీయ నృత్యం-బృంద ప్రదర్శన"://Log.i("number","58");
                            acroFields.setField("58", String.valueOf(e.getTeam()));
                            break;
                        case "విచిత్ర (ఫాన్సీ) వేషధారణ (సెట్టింగ్స్ లేకుండా)"://Log.i("number","59");
                            acroFields.setField("59", String.valueOf(e.getTeam()));
                            break;
                        case "విచిత్ర (ఫాన్సీ) వేషధారణ (సెట్టింగ్స్)"://Log.i("number","60");
                            acroFields.setField("60", String.valueOf(e.getTeam()));
                            break;

                    }
                }
                stamper.setFormFlattening(true);
                stamper.close();
                reader.close();
                outputStream.close();

                List<String> toEmailList = Arrays.asList(schools.getHeadMaster_Email(), "vvitbalotsav@gmail.com");
                new SendMailTask(getActivity(), 0, 11).execute("vvitbalotsav2k18@gmail.com", "balotsav2k18", toEmailList, getResources().getString(R.string.email_subject), getResources().getString(R.string.email_body), path1);
            } else
                Log.i("no permissions", "no permissions");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
