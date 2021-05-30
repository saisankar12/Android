package app.balotsav.com.balotsavslider.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import app.balotsav.com.balotsavslider.R;
import app.balotsav.com.balotsavslider.model.Schools;
import app.balotsav.com.balotsavslider.utils.CheckNetwork;
import app.balotsav.com.balotsavslider.utils.SendMailTask;
import app.balotsav.com.balotsavslider.utils.Timer;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] schoolType = {"--- స్కూల్ టైపు ని సెలెక్ట్ చేసుకొండి ---", "STATE", "CBSE", "ICSE"};
    EditText sname, scode, pswd, hmname, hmaemail, hmphno, coname, cophno1, cophno2, town, district, state, pincode;
    String selected, otp, sent;
    Button register;
    FirebaseAuth mAuth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    boolean b = false;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, dr;
    CheckBox checkBoxaccd;
    ProgressDialog progressDialog;
    TextView acclink;
    Spinner spinner;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private String phone;
    PhoneAuthProvider.ForceResendingToken force;
    private CountDownTimer CDT;
    private int i=0;
    private String text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sharedPreferences = getSharedPreferences("Balotsav", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        mAuth = FirebaseAuth.getInstance();
        register = findViewById(R.id.register);
        sname = findViewById(R.id.sname);
        acclink = findViewById(R.id.id_Acomdationlink);
        pswd = findViewById(R.id.pswd);
        scode = findViewById(R.id.scode);
        checkBoxaccd = findViewById(R.id.id_needAcomdiation);
        hmaemail = findViewById(R.id.tmail);
        hmname = findViewById(R.id.t1);
        hmphno = findViewById(R.id.p1);
        coname = findViewById(R.id.t2);
        cophno1 = findViewById(R.id.p21);
        cophno2 = findViewById(R.id.p2);
        town = findViewById(R.id.village);
        district = findViewById(R.id.District);
        state = findViewById(R.id.State);
        pincode = findViewById(R.id.pincode);
        spinner = findViewById(R.id.id_spinner);
        spinner.setOnItemSelectedListener(this);
        progressDialog = new ProgressDialog(this);
        ArrayAdapter<String> obj = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, schoolType);
        spinner.setAdapter(obj);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(RegisterActivity.this, R.string.otp_sent, Toast.LENGTH_LONG).show();
                b = true;

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(RegisterActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.e("OTP error", e.getLocalizedMessage());
            }

            @Override
            public void onCodeSent(final String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                sent = s;
                force = forceResendingToken;
            }
        };


    }

    private boolean validate(EditText[] fields) {
        for (int i = 0; i < fields.length; i++) {
            EditText currentField = fields[i];
            if (currentField.getText().toString().length() <= 0) {
                return false;
            }
        }
        return true;
    }


    public void clcikToLogin(View view) {
        Intent in = new Intent(this, LoginActivity.class);
        startActivity(in);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void clickNeedAcom(View view) {
        if (checkBoxaccd.isChecked() == true) {
            acclink.setText(R.string.click_here_to_fill_Accomodation_form);
            acclink.setTextColor(Color.BLUE);
            acclink.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
            acclink.setVisibility(View.VISIBLE);
        } else {
            acclink.setVisibility(View.GONE);
        }
    }

    public void getAccomodation(View view) {
        Intent accom = new Intent(this, AccomodationActivity.class);
        startActivity(accom);
    }

    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    public void register(final View view) {
        register.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });
        if (validate(new EditText[]{sname, pswd, scode, hmaemail, hmname, hmphno, coname, cophno1, cophno2, town, district, state, pincode})) {
            selected = spinner.getSelectedItem().toString();
            phone = "+" + cophno1.getText().toString() + cophno2.getText().toString();
            if (new CheckNetwork(RegisterActivity.this).isNetworkAvailable()) {
                //next(sent, otp);
                text = scode.getText().toString().replace("/","@");
                Log.i("Test - RegisterActivity", text);
                databaseReference = firebaseDatabase.getReference("Schools").child(text);
                dr = databaseReference;
                progressDialog.setMessage("Loading");
                progressDialog.setCancelable(false);
                progressDialog.show();
               // new Timer(progressDialog, RegisterActivity.this).count();
                String scode1 = scode.getText().toString();
                dr.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.i("Error :",dataSnapshot.getKey());
                        if (dataSnapshot.exists()) {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, R.string.school_code_is_already_taken, Toast.LENGTH_LONG).show();
                        } else {
                            PhoneAuthProvider.getInstance().verifyPhoneNumber(phone, 60, TimeUnit.SECONDS, RegisterActivity.this, mCallbacks);
                            LayoutInflater li = LayoutInflater.from(RegisterActivity.this);
                            final View promptsView = li.inflate(R.layout.otp, null);
                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    RegisterActivity.this);
                            alertDialogBuilder.setView(promptsView);
                            alertDialogBuilder.setMessage(getResources().getString(R.string.message_otp));

                            final EditText userInput = (EditText) promptsView
                                    .findViewById(R.id.editTextDialogUserInput);
                            userInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                            progressDialog.dismiss();
                            alertDialogBuilder
                                    .setCancelable(false)
                                    .setNeutralButton("Resend OTP", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            PhoneAuthProvider.getInstance().verifyPhoneNumber(phone, 60, TimeUnit.SECONDS, RegisterActivity.this, mCallbacks, force);
                                            register(view);
                                        }
                                    })
                                    .setPositiveButton("OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    // get user input and set it to result
                                                    // edit text
                                                    if (!TextUtils.isEmpty(userInput.getText().toString())) {
                                                        progressDialog.show();
                                                        register.setEnabled(false);
                                                        otp = userInput.getText().toString();
                                                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(sent, otp);
                                                        mAuth.signInWithCredential(credential)
                                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                                        if (task.isSuccessful()) {


                                                                            Schools schools = new Schools(
                                                                                   sname.getText().toString(),
                                                                                    scode.getText().toString(),
                                                                                    selected,
                                                                                    pswd.getText().toString(),
                                                                                    hmname.getText().toString(),
                                                                                    hmaemail.getText().toString(),
                                                                                    hmphno.getText().toString(),
                                                                                    coname.getText().toString(),
                                                                                    phone,
                                                                                    town.getText().toString(),
                                                                                    district.getText().toString(),
                                                                                    state.getText().toString(),
                                                                                    pincode.getText().toString());
                                                                            dr.setValue(schools);
                                                                            Gson gson = new Gson();
                                                                            String json = gson.toJson(schools);
                                                                            editor.putString("data", json);
                                                                            editor.putString("scode", text);
                                                                            editor.apply();
                                                                            String subject = getResources().getString((R.string.email_school_subject));
                                                                            String body = getResources().getString(R.string.email_school_body) + scode.getText().toString() + getResources().getString(R.string.email_school_body2) + pswd.getText().toString() + getResources().getString(R.string.email_school_body3);
                                                                            List<String> toEmailList = Arrays.asList(hmaemail.getText().toString(), "vvitbalotsav@gmail.com");
                                                                            new SendMailTask(RegisterActivity.this, 1, 10).execute("vvitbalotsav2k18@gmail.com", "balotsav2k18", toEmailList, subject, body);
                                                                            progressDialog.dismiss();


                                                                        } else {
                                                                            progressDialog.dismiss();
                                                                            Toast.makeText(RegisterActivity.this, R.string.incorrect_otp, Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                    }
                                                }
                                            });


                            final AlertDialog alertDialog = alertDialogBuilder.create();
                            // show it
                            alertDialog.show();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            } else
                Toast.makeText(RegisterActivity.this, R.string.check_connection, Toast.LENGTH_LONG).show();
        } else
        Toast.makeText(RegisterActivity.this, R.string.enter_all_fields, Toast.LENGTH_LONG).show();
    }
}