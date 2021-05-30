package com.example.firebasedbcrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText et1,et2,et3,et4,et5;
    static DatabaseReference reference;
    RecyclerView rv;
    List<Student> students;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 = findViewById(R.id.name);
        et2 = findViewById(R.id.rollnumber);
        et3 = findViewById(R.id.Mobile);
        et4 = findViewById(R.id.updaterollnumber);
        et5 = findViewById(R.id.updateMobile);
        rv =findViewById(R.id.recycler);
        students =new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("StudentData");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                students.clear();
                for( DataSnapshot dataSnap:dataSnapshot.getChildren()){
                    Student student=dataSnap.getValue(Student.class);
                    students.add(student);
                    rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    rv.setAdapter(new MyDataAdapter(MainActivity.this,students));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    public void save(View view) {
        String name = et1.getText().toString();
        String roll = et2.getText().toString();
        String mobile = et3.getText().toString();
        String id = reference.push().getKey();
        // One Way is HashMap

        Student student = new Student(name,roll,mobile);
        reference.child(roll).setValue(student);


    }

    public void update(View view) {
        String uroll = et4.getText().toString();
        final String umobile = et5.getText().toString();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query query= ref.child("StudentData").orderByChild("roll").equalTo(uroll);

        final HashMap<String, Object> map = new HashMap<>();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    map.put("number",umobile);
                    snapshot.getRef().updateChildren(map);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
