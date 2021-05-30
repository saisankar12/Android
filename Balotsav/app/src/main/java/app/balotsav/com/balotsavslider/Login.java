package app.balotsav.com.balotsavslider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import org.json.JSONObject;
import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    SharedPreferences.Editor editor;EditText et1,et2;FirebaseDatabase firebaseDatabase;DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(getSharedPreferences("Balotsav",MODE_PRIVATE).getInt("registered",0)==1)
        {startActivity(new Intent(Login.this,DashBoard.class));
            finish();}//vs

        editor=getSharedPreferences("Balotsav",MODE_PRIVATE).edit();
        et1=findViewById(R.id.et1);
        et2=findViewById(R.id.et2);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");
        firebaseDatabase=FirebaseDatabase.getInstance();
    }

    public void clickToRegister(View view) {
        Intent in = new Intent(this,RegisterActivity.class);
        startActivity(in);
        finish();
    }

    public void clcikToDashBoard(View view) throws JSONException {
        String pswd;
        if(!TextUtils.isEmpty(et1.getText().toString()) && !TextUtils.isEmpty(et2.getText().toString())) {
            if (new CheckNetwork(Login.this).isNetworkAvailable()){
                progressDialog.show();
                DatabaseReference databaseReference = firebaseDatabase.getReference("Schools").child(et1.getText().toString());
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            Schools schools = dataSnapshot.getValue(Schools.class);
                            //String scode = schools.getScode().toString();
                            String pswd = schools.getPassword().toString();
                            if (pswd.equals(et2.getText().toString())) {
                                editor.putInt("registered", 1);
                                Gson gson = new Gson();
                                String json = gson.toJson(schools);
                                editor.putString("data", json);
                                editor.putString("scode", et1.getText().toString());
                                editor.apply();
                                progressDialog.dismiss();
                                Intent in = new Intent(Login.this, DashBoard.class);
                                startActivity(in);
                                finish();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(Login.this, R.string.wrong_password, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, R.string.event_not_reg, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
            else {
                Toast.makeText(Login.this, R.string.check_connection, Toast.LENGTH_LONG).show();
            }

        }
        else{
            if(TextUtils.isEmpty(et1.getText())){
                et1.setHint(R.string.enter_all_fields);
                et1.setHintTextColor(getResources().getColor(R.color.red));
            }
            else if(TextUtils.isEmpty(et2.getText())){
                et2.setHint(R.string.enter_all_fields);
                et2.setHintTextColor(getResources().getColor(R.color.red));
            }
            else{
                et1.setHint(R.string.enter_all_fields);
                et1.setHintTextColor(getResources().getColor(R.color.red));
                et2.setHint(R.string.enter_all_fields);
                et2.setHintTextColor(getResources().getColor(R.color.red));
            }
        }

    }
}
