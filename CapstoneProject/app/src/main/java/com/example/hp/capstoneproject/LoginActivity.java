package com.example.hp.capstoneproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity
{
    @InjectView(R.id.login_username)
    EditText login_user;
    @InjectView(R.id.login_password)
    EditText login_pswd;

    String uname,pswd;
    FirebaseAuth auth;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        dialog=new ProgressDialog(this);

        auth=FirebaseAuth.getInstance();
        if (auth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
    }

    public void registerhere(View view)
    {
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    public void user_login(View view)
    {
        dialog.setTitle("Authenticating the User...");
        dialog.setMessage("Loading...plz wait...");
        dialog.setCancelable(false);
        dialog.show();
        uname=login_user.getText().toString();
        pswd=login_pswd.getText().toString();
        auth.signInWithEmailAndPassword(uname,pswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            if (!task.isSuccessful())
            {
                Toast.makeText(LoginActivity.this, "Error in Login", Toast.LENGTH_SHORT).show();
            }
            else {
                dialog.dismiss();
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("uname",uname);
                startActivity(intent);
            }
            }
        });
    }
}
