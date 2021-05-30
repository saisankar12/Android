package app.balotsav.com.balotsavslider.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONException;

import app.balotsav.com.balotsavslider.R;
import app.balotsav.com.balotsavslider.model.Schools;
import app.balotsav.com.balotsavslider.utils.CheckNetwork;
import app.balotsav.com.balotsavslider.utils.Timer;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences.Editor editor;
    EditText et1, et2;
    FirebaseDatabase firebaseDatabase;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getSharedPreferences("Balotsav", MODE_PRIVATE).getInt("registered", 0) == 1) {
            startActivity(new Intent(LoginActivity.this, DashBoardActivity.class));
            finish();
        }//vs

        editor = getSharedPreferences("Balotsav", MODE_PRIVATE).edit();
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void clickToRegister(View view) {
        Intent in = new Intent(this, RegisterActivity.class);
        startActivity(in);
        finish();
    }

    public void clcikToDashBoard(View view) throws JSONException {
        String pswd;
        if (!TextUtils.isEmpty(et1.getText().toString()) && !TextUtils.isEmpty(et2.getText().toString())) {
            if (new CheckNetwork(LoginActivity.this).isNetworkAvailable()) {
                progressDialog.show();
               // new Timer(progressDialog, LoginActivity.this).count();
                DatabaseReference databaseReference = firebaseDatabase.getReference("Schools").child(et1.getText().toString().replaceAll("/","@"));
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            Schools schools = dataSnapshot.getValue(Schools.class);

                            String pswd = schools.getPassword().toString();
                            if (pswd.equals(et2.getText().toString())) {
                                editor.putInt("registered", 1);
                                Gson gson = new Gson();
                                String json = gson.toJson(schools);
                                editor.putString("data", json);
                                editor.putString("scode", et1.getText().toString().replaceAll("/","@"));
                                editor.apply();
                                progressDialog.dismiss();
                                Intent in = new Intent(LoginActivity.this, DashBoardActivity.class);
                                startActivity(in);
                                finish();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, R.string.wrong_password, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, getString(R.string.invalid_username), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            } else {
                Toast.makeText(LoginActivity.this, R.string.check_connection, Toast.LENGTH_LONG).show();
            }

        } else {
            if (TextUtils.isEmpty(et1.getText()) && TextUtils.isEmpty(et2.getText())) {
                Toast.makeText(LoginActivity.this, R.string.enter_all_fields, Toast.LENGTH_LONG).show();

            } else if (TextUtils.isEmpty(et2.getText())) {
                Toast.makeText(LoginActivity.this, R.string.empty_password, Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(LoginActivity.this, R.string.empty_school_code, Toast.LENGTH_LONG).show();


            }
        }

    }
}
