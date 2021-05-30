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

public class RegisterActivity extends AppCompatActivity {
    @InjectView(R.id.register_username)
    EditText reg_user;
    @InjectView(R.id.register_password)
    EditText reg_pswd;

    String uname, pswd;
    FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);

        auth = FirebaseAuth.getInstance();
        dialog=new ProgressDialog(this);
    }

    public void user_signUp(View view)
    {
        dialog.setTitle("Authenticating the User...");
        dialog.setMessage("Loading...plz wait...");
        dialog.setCancelable(false);
        dialog.show();
        uname = reg_user.getText().toString();
        pswd = reg_pswd.getText().toString();
        auth.createUserWithEmailAndPassword(uname,pswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful())
                {
                    Toast.makeText(RegisterActivity.this, "Error in Registering user", Toast.LENGTH_SHORT).show();
                }
                else {
                    dialog.dismiss();
                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
