package com.example.user.e_quiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    FirebaseDatabase reference = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();


        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        databaseReference = reference.getReference("MyScore");

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, ResetPassword.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this,LoginActivity.class));
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
/*
                final String cprolang = "0";
                final String javaLang = "0";*/
                final String mlanguageFundamentals = "X";
                final String mOperatorsAssignments = "X";
                final String mDeclarationsAccessControl = "X";
                final String mAssertions = "X";
                final String mExceptions = "X";
                final String mInnerClasses = "X";
                final String mGarbageCollections = "X";
                final String mJavalangClass = "X";
                final String mFlowControl = "X";
                final String mObjectsandCollections = "X";
                final String mThreads = "X";
                final String mOverallQuestions = "X";


                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //Toast.makeText(SignUp.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                  //  Toast.makeText(SignUp.this, "Authentication failed." + task.getException(),
                                    //        Toast.LENGTH_SHORT).show();
                                    Toast.makeText(SignUp.this, "Please provide Valid E-Mail", Toast.LENGTH_SHORT).show();
                                    new AlertDialog.Builder(getApplicationContext()).setTitle("E-Mail")
                                            .setMessage("Please provide Valid E-Mail")
                                            .setCancelable(false).setIcon(R.mipmap.quizicon)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            }).setNegativeButton("Cancel",null).show();

                                } else {
                                    Toast.makeText(SignUp.this, "Registered Succesfully", Toast.LENGTH_SHORT).show();
                                    MyScore myScore = new MyScore(email,mlanguageFundamentals,mOperatorsAssignments,mDeclarationsAccessControl,
                                            mAssertions,mExceptions,mInnerClasses,mGarbageCollections,mJavalangClass,mFlowControl,mObjectsandCollections,mThreads,mOverallQuestions);
                                    String insert = databaseReference.push().getKey();
                                    databaseReference.child(insert).setValue(myScore);
                                    startActivity(new Intent(SignUp.this, JavaActivity.class));
                                    finish();
                                }
                            }
                        });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}

