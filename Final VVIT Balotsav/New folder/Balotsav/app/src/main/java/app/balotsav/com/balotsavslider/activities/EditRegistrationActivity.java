package app.balotsav.com.balotsavslider.activities;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import java.util.ArrayList;
import app.balotsav.com.balotsavslider.R;
import app.balotsav.com.balotsavslider.model.Schools;
import app.balotsav.com.balotsavslider.utils.CheckNetwork;

public class EditRegistrationActivity extends AppCompatActivity {
    EditText sname, scode, hmname, hmaemail, hmphno, coname, cophno2, town, district, state, pincode;
    ArrayList<String> a;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_registration);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        a = new ArrayList<>();
        a.add("--- స్కూల్ టైపు ని సెలెక్ట్ చేసుకొండి ---");
        a.add("STATE");
        a.add("CBSE");
        a.add("ICSE");
        sname = findViewById(R.id.sname);
        scode = findViewById(R.id.scode);
        hmaemail = findViewById(R.id.tmail);
        hmname = findViewById(R.id.t1);
        hmphno = findViewById(R.id.p1);
        coname = findViewById(R.id.t2);
        cophno2 = findViewById(R.id.p2);
        town = findViewById(R.id.village);
        district = findViewById(R.id.District);
        state = findViewById(R.id.State);
        pincode = findViewById(R.id.pincode);
        Gson g = new Gson();
        String json = getSharedPreferences("Balotsav", MODE_PRIVATE).getString("data", " ");
        Schools s = g.fromJson(json, Schools.class);
        sname.setText(s.getSchool_Name());
        scode.setText(s.getSchool_Code());
        hmname.setText(s.getHeadMaster_Name());
        hmaemail.setText(s.getHeadMaster_Email());
        hmphno.setText(s.getHeadMaster_PhNo());
        coname.setText(s.getCoordinator_Name());
        cophno2.setText(s.getCoordinator_PhNo());
        town.setText(s.getTown());
        district.setText(s.getDistrict());
        state.setText(s.getState());
        pincode.setText(s.getPinCode());
    }

    public void Changes(View view) {
        if (new CheckNetwork(this).isNetworkAvailable()) {
            Schools s = new Schools();
            s.setSchool_Name(sname.getText().toString());
            s.setSchool_Code(scode.getText().toString());
            s.setHeadMaster_Name(hmname.getText().toString());
            s.setHeadMaster_Email(hmaemail.getText().toString());
            s.setHeadMaster_PhNo(hmphno.getText().toString());
            s.setCoordinator_Name(coname.getText().toString());
            s.setCoordinator_PhNo(cophno2.getText().toString());
            s.setTown(town.getText().toString());
            s.setDistrict(district.getText().toString());
            s.setState(state.getText().toString());
            s.setPinCode(pincode.getText().toString());
            Gson g = new Gson();
            String str = g.toJson(s);
            SharedPreferences sh = getSharedPreferences("Balotsav", MODE_PRIVATE);
            SharedPreferences.Editor editor = sh.edit();
            editor.putString("data", str);
            editor.apply();
            databaseReference.child("Schools").child(s.getSchool_Code()).setValue(s);
            Toast.makeText(this, "మార్పులు చేయబడినవి", Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        }
        else
            Toast.makeText(this, R.string.check_connection, Toast.LENGTH_SHORT).show();
    }
}