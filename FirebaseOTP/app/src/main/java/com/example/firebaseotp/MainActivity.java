package com.example.firebaseotp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    EditText number, otp;
    FirebaseAuth auth;
    //PhoneAuthProvider.OnVerificationStateChangedCallbacks mCall;
    String vid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number = findViewById(R.id.et_mobilenumber);
        otp = findViewById(R.id.et_verfiyotp);

        auth = FirebaseAuth.getInstance();

    }

    public void sendOTP(View view) {
        String mobilenumber = number.getText().toString();
        if (mobilenumber.isEmpty()) {
            number.setError("PLease Enter Mobile Number");
            number.requestFocus();
            return;
        } if (mobilenumber.length() < 10) {
            number.setText("Enter Valid Mobile Number");
            number.requestFocus();
            return;
        }
        PhoneAuthProvider.getInstance().verifyPhoneNumber(mobilenumber, 60,
                    TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD, mCall);

    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCall = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

        }
        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

        }
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            vid = s;
        }
    };

    public void verify(View view) {
        String verifyotp = otp.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(vid,verifyotp);
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Phone Number Verified Sucessfully", Toast.LENGTH_SHORT).show();
                    }else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                        Toast.makeText(MainActivity.this, "Not Verified, Invalid OTP", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }
}
