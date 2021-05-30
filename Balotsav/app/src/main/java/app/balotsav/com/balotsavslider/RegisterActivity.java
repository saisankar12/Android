package app.balotsav.com.balotsavslider;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.util.Size;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] schoolType = {"Select--","STATE", "CBSE", "ICSE"};
    EditText sname, scode,pswd,hmname,hmaemail,hmphno,coname,cophno,town,district,state,pincode;
    String selected,otp,sent;Button register;
    FirebaseAuth mAuth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    boolean b = false;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, dr;
    CheckBox checkBoxaccd;ProgressDialog progressDialog;
    TextView acclink;Spinner spinner;SharedPreferences sharedPreferences;SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sharedPreferences=getSharedPreferences("Balotsav",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        mAuth = FirebaseAuth.getInstance();
        register=findViewById(R.id.register);
        sname = findViewById(R.id.sname);
        acclink = findViewById(R.id.id_Acomdationlink);
        pswd=findViewById(R.id.pswd);
        scode = findViewById(R.id.scode);
        checkBoxaccd = findViewById(R.id.id_needAcomdiation);
        hmaemail=findViewById(R.id.tmail);
        hmname=findViewById(R.id.t1);
        hmphno=findViewById(R.id.p1);
        coname=findViewById(R.id.t2);
        cophno=findViewById(R.id.p2);
        town=findViewById(R.id.village);
        district=findViewById(R.id.District);
        state=findViewById(R.id.State);
        pincode=findViewById(R.id.pincode);
        spinner = findViewById(R.id.id_spinner);
        spinner.setOnItemSelectedListener(this);
        progressDialog=new ProgressDialog(this);
        ArrayAdapter<String> obj = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, schoolType);
        spinner.setAdapter(obj);
        firebaseDatabase = FirebaseDatabase.getInstance();
        final EditText editText = new EditText(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(new CheckNetwork(RegisterActivity.this).isNetworkAvailable()){
                    if (validate(new EditText[]{sname, pswd, scode, hmaemail, hmname, hmphno, coname, cophno, town, district, state, pincode})) {
                        selected = spinner.getSelectedItem().toString();
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(cophno.getText().toString(), 60, TimeUnit.SECONDS, RegisterActivity.this, mCallbacks);
                        LayoutInflater li = LayoutInflater.from(RegisterActivity.this);
                        final View promptsView = li.inflate(R.layout.otp, null);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                RegisterActivity.this);
                        alertDialogBuilder.setView(promptsView);

                        final EditText userInput = (EditText) promptsView
                                .findViewById(R.id.editTextDialogUserInput);
                        alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // get user input and set it to result
                                                // edit text
                                                if (!TextUtils.isEmpty(userInput.getText().toString())) {
                                                    otp = userInput.getText().toString();
                                                    progressDialog.setMessage("Loading");
                                                    progressDialog.setCancelable(false);
                                                    progressDialog.show();
                                                    next(sent, otp);
                                                }
                                            }
                                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                    } else
                        Toast.makeText(RegisterActivity.this, R.string.enter_all_fields, Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(RegisterActivity.this,R.string.check_connection,Toast.LENGTH_LONG).show();
            }
        });
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(RegisterActivity.this, R.string.otp_sent, Toast.LENGTH_LONG).show();
                b = true;

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(RegisterActivity.this,e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.e("OTP error",e.getLocalizedMessage());
            }

            @Override
            public void onCodeSent(final String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                sent=s;
            }
        };


    }
    private boolean validate(EditText[] fields){
        for(int i = 0; i < fields.length; i++){
            EditText currentField = fields[i];
            if(currentField.getText().toString().length() <= 0){
                return false;
            }
        }
        return true;
    }

    public void next(String s,String otp) {
        Log.i("next",s+" "+otp);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(s, otp);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            databaseReference = firebaseDatabase.getReference("Schools").child(scode.getText().toString());
                            //databaseReference.setValue(scode.getText().toString());
                            dr = databaseReference;
                            dr.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        progressDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this,R.string.school_code_is_already_taken,Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Schools schools=new Schools(
                                                sname.getText().toString(),
                                                scode.getText().toString(),
                                                selected,
                                                pswd.getText().toString(),
                                                hmname.getText().toString(),
                                                hmaemail.getText().toString(),
                                                hmphno.getText().toString(),
                                                coname.getText().toString(),
                                                cophno.getText().toString(),
                                                town.getText().toString(),
                                                district.getText().toString(),
                                                state.getText().toString(),
                                                pincode.getText().toString());
                                        dr.setValue(schools);
                                        Gson gson = new Gson();
                                        String json = gson.toJson(schools);
                                        editor.putString("data", json);
                                        editor.putString("scode",scode.getText().toString());
                                        editor.apply();
                                        progressDialog.dismiss();
                                        Intent in = new Intent(RegisterActivity.this, DashBoard.class);
                                        startActivity(in);
                                        finish();
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this,R.string.incorrect_otp,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        /*if (s.equals(otp)) {
            databaseReference = firebaseDatabase.getReference(selected).child(scode.getText().toString());
            //databaseReference.setValue(scode.getText().toString());
            dr = databaseReference;
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("sname", sname.getText().toString());
            hashMap.put("p1", p1.getText().toString());
            hashMap.put("p2", p2.getText().toString());
            hashMap.put("p3", p3.getText().toString());
            hashMap.put("t1", t1.getText().toString());
            hashMap.put("t2", t2.getText().toString());
            hashMap.put("t3", t3.getText().toString());
            hashMap.put("tmail",tmail.getText().toString());
            dr.setValue(hashMap);
            Intent in = new Intent(RegisterActivity.this, DashBoard.class);
            startActivity(in);
        }
        else
            Toast.makeText(RegisterActivity.this,"wrong otp", Toast.LENGTH_LONG).show();*/
    }

    public void clcikToLogin(View view) {
        Intent in = new Intent(this, Login.class);
        startActivity(in);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void clickNeedAcom(View view) {
        if(checkBoxaccd.isChecked()==true)
        {
            acclink.setText(R.string.click_here_to_fill_Accomodation_form);
            acclink.setTextColor(Color.RED);
            acclink.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
        }
        else
        {
            acclink.setText("");
        }
    }
}
