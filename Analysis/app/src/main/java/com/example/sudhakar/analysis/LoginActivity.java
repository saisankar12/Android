package com.example.sudhakar.analysis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText e1,e2;
    private Button b1;
    private TextView t1;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth= FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!= null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),DisplayActivity.class));
        }

        e1=(EditText) findViewById(R.id.email);
        e2=(EditText) findViewById(R.id.pwd);
        b1=(Button) findViewById(R.id.login);
        t1=(TextView) findViewById(R.id.textviewsignin);
        progressDialog=new ProgressDialog(this);

        b1.setOnClickListener(this);
        t1.setOnClickListener(this);
    }
    private void userLogin(){
        String e=e1.getText().toString().trim();
        String p=e2.getText().toString().trim();
        if(TextUtils.isEmpty(e)){
            Toast.makeText(this,"Please enter e-mail",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(p)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Registering User.......");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(e,p).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(getApplicationContext(),DisplayActivity.class));
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == b1){
            userLogin();
        }
        if(v == t1){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
    }
}
