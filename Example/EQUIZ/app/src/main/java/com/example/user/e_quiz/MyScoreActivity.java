package com.example.user.e_quiz;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MyScoreActivity extends AppCompatActivity {


    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    TextView textViewCLang;
    String emailIDd;
    MyScore myScore;
    public String Clanguage;
    String languageFundamentals,OperatorsAssignments,DeclarationsAccessControl,Assertions;
    String Exceptions,InnerClasses,GarbageCollections,JavalangClass,FlowControl,ObjectsandCollections;
    String Threads,OverallQuestions;
    TextView textViewLF,textViewOA,textViewDA,textViewA,textViewE,textViewIC;
    TextView textViewGC,textViewJL,textViewFC,textViewOC,textViewT,textViewOQ;
    ProgressBar progressBarScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_score);

        progressBarScore = findViewById(R.id.myScoreProgress);
        //textViewCLang = findViewById(R.id.txtClnagScore);
        textViewLF = findViewById(R.id.txtLF);
        textViewOA = findViewById(R.id.txtOA);
        textViewDA = findViewById(R.id.txtDA);
        textViewA = findViewById(R.id.txtA);
        textViewE = findViewById(R.id.txtE);
        textViewIC = findViewById(R.id.txtIC);
        textViewGC = findViewById(R.id.txtGC);
        textViewJL = findViewById(R.id.txtJL);
        textViewFC = findViewById(R.id.txtFC);
        textViewOC = findViewById(R.id.txtOC);
        textViewT = findViewById(R.id.txtT);
        textViewOQ = findViewById(R.id.txtOQ);
        emailIDd = LoginActivity.email;
        databaseReference = firebaseDatabase.getReference().child("MyScore");
        Query query = databaseReference.orderByChild("mail").equalTo(emailIDd);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    myScore = dataSnapshot1.getValue(MyScore.class);

                    progressBarScore.setVisibility(View.GONE);
                    languageFundamentals = myScore.getLanguageFundamentals();
                    OperatorsAssignments = myScore.getOperatorsAssignments();
                    DeclarationsAccessControl = myScore.getDeclarationsAccessControl();
                    Assertions = myScore.getAssertions();
                    Exceptions = myScore.getExceptions();
                    InnerClasses = myScore.getInnerClasses();
                    GarbageCollections = myScore.getGarbageCollections();
                    JavalangClass = myScore.getJavalangClass();
                    FlowControl = myScore.getFlowControl();
                    ObjectsandCollections = myScore.getObjectsandCollections();
                    Threads = myScore.getThreads();
                    OverallQuestions = myScore.getOverallQuestions();


           //         textViewCLang.setText("MY ID \t \t \t \t"+emailIDd);
                    textViewLF.setText(languageFundamentals);
                    textViewOA.setText(OperatorsAssignments);
                    textViewDA.setText(DeclarationsAccessControl);
                    textViewA.setText(Assertions);
                    textViewE.setText(Exceptions);
                    textViewIC.setText(InnerClasses);
                    textViewGC.setText(GarbageCollections);
                    textViewJL.setText(JavalangClass);
                    textViewFC.setText(FlowControl);
                    textViewOC.setText(ObjectsandCollections);
                    textViewT.setText(Threads);
                    textViewOQ.setText(OverallQuestions);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

