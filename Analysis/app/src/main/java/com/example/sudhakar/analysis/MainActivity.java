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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText e1,e2;
    private Button b1;
    private TextView t1;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!= null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),DisplayActivity.class));
        }

        setContentView(R.layout.activity_main);
        e1=(EditText) findViewById(R.id.email);
        e2=(EditText) findViewById(R.id.pwd);
        b1=(Button) findViewById(R.id.Register);
        t1=(TextView) findViewById(R.id.textviewregister);
        ProgressDialog progressDialog=new ProgressDialog(this);

        b1.setOnClickListener(this);
        t1.setOnClickListener(this);
    }
    private void registerUser(){
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
        //progressDialog.setMessage("Registering User.......");
        //    progressDialog.show();
        mAuth.createUserWithEmailAndPassword(e,p).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    if(mAuth.getCurrentUser()!= null)
                    {
                        finish();
                        startActivity(new Intent(getApplicationContext(),DisplayActivity.class));
                    }
                    Toast.makeText(MainActivity.this,"Registered Successfully.......",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Could not Register...Please try again.....",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == b1){
            registerUser();
        }
        if(v == t1){
            startActivity(new Intent(this,LoginActivity.class));
        }
    }
}
