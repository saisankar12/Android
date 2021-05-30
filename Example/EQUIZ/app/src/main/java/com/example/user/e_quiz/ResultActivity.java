package com.example.user.e_quiz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.e_quiz.MyModel.ModelC;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    TextView textViewTotal, textViewCorrect, textViewWrong, textViewTotalScore, textViewReview;
    ModelC modelC;
    String models1;
    DatabaseReference databaseReference;
    int total;
    RecyclerView recyclerView;
    ArrayList<ModelC> modelCArrayList;
    MyAdapter adapter;
    ProgressBar progressBar;
    String e,crct;
    Button buttonExp;
    ProgressDialog progressDoalog;
    RatingBar ratingBar;
    Button buttonAddScore;
    MyScore myScore;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.tick);

        progressBar = findViewById(R.id.progres);
        ratingBar = findViewById(R.id.myRating);

        textViewTotal = findViewById(R.id.txtTotal);
        textViewCorrect = findViewById(R.id.txtCorrect);
        textViewWrong = findViewById(R.id.txtWrong);
        //textViewTotalScore = findViewById(R.id.txttotalScore);
        textViewReview = findViewById(R.id.txtviewReview);
        buttonExp = findViewById(R.id.buttonExplanation);

        //buttonAddScore = findViewById(R.id.btnAddScore);
        recyclerView = findViewById(R.id.myRecycler);
        modelCArrayList = new ArrayList<>();

        Intent intent = getIntent();
        final String tot = intent.getStringExtra("total");
        crct = intent.getStringExtra("correct");
        String wrng = intent.getStringExtra("wrong");
        // String sco = intent.getStringExtra("score");
        e = intent.getStringExtra("subject");
        Log.i("program",e);
        textViewTotal.setText(tot);
        textViewCorrect.setText(crct);
        textViewWrong.setText(wrng);

        updateScoreBoard();
        /*buttonAddScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        });*/

        switch (crct) {
            case "5":
                textViewReview.setText("Good,Keep It Up!");
                break;
            case "4":
                textViewReview.setText("It was better attempt but you need to do more practice");
                break;
            case "3":
                textViewReview.setText("It was good attempt but you need to work hard!");
                break;
            case "2":
                textViewReview.setText("It was nice attempt but you need to Practice Well");
                break;
            case "1":
                textViewReview.setText("You have to Concentrate More");
                break;
            case "0":
                textViewReview.setText("Too Poor!");
                break;
        }
        ratingBar.setRating(Float.parseFloat(crct));

        buttonExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                modelCArrayList.clear();
                ImageView imageView = findViewById(R.id.imgswipe);
                imageView.setVisibility(View.VISIBLE);
                progressDoalog = new ProgressDialog(ResultActivity.this);
                progressDoalog.setMax(100);
                progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while (progressDoalog.getProgress() <= progressDoalog
                                    .getMax()) {
                                Thread.sleep(200);
                                handle.sendMessage(handle.obtainMessage());
                                if (progressDoalog.getProgress() == progressDoalog
                                        .getMax()) {
                                    progressDoalog.dismiss();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                update();
            }

            Handler handle = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    progressBar.incrementProgressBy(1);
                    handle.sendMessage(handle.obtainMessage());
                }
            };

        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new MyAdapter(this, modelCArrayList);
        recyclerView.setAdapter(adapter);

    }

    private void updateScoreBoard() {
        String emailID = JavaActivity.user.getEmail();
       /* Log.i("correct",crct);
        Log.i("email",emailID);
       */ databaseReference = firebaseDatabase.getReference().child("MyScore");
        switch (e){
            case "LanguageFundamentals" :
                Query query = databaseReference.orderByChild("mail").equalTo(emailID);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            myScore = dataSnapshot1.getValue(MyScore.class);
                            dataSnapshot1.getRef().child("languageFundamentals").setValue(crct);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "OperatorsAssignments":
                Query query1 = databaseReference.orderByChild("mail").equalTo(emailID);
                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            myScore = dataSnapshot1.getValue(MyScore.class);
                            dataSnapshot1.getRef().child("operatorsAssignments").setValue(crct);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "DeclarationsAccessControl" :
                Query query2 = databaseReference.orderByChild("mail").equalTo(emailID);
                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            myScore = dataSnapshot1.getValue(MyScore.class);
                            dataSnapshot1.getRef().child("declarationsAccessControl").setValue(crct);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "Assertions":
                Query query3 = databaseReference.orderByChild("mail").equalTo(emailID);
                query3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            myScore = dataSnapshot1.getValue(MyScore.class);
                            dataSnapshot1.getRef().child("assertions").setValue(crct);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "Exceptions":
                Query query4 = databaseReference.orderByChild("mail").equalTo(emailID);
                query4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            myScore = dataSnapshot1.getValue(MyScore.class);
                            dataSnapshot1.getRef().child("exceptions").setValue(crct);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "InnerClass":
                Query query5 = databaseReference.orderByChild("mail").equalTo(emailID);
                query5.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            myScore = dataSnapshot1.getValue(MyScore.class);
                            dataSnapshot1.getRef().child("innerClasses").setValue(crct);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "GarbageCollections":
                Query query6 = databaseReference.orderByChild("mail").equalTo(emailID);
                query6.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            myScore = dataSnapshot1.getValue(MyScore.class);
                            dataSnapshot1.getRef().child("garbageCollections").setValue(crct);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "Javalang":
                Query query7 = databaseReference.orderByChild("mail").equalTo(emailID);
                query7.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            myScore = dataSnapshot1.getValue(MyScore.class);
                            dataSnapshot1.getRef().child("javalangClass").setValue(crct);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "FlowControl":
                Query query8 = databaseReference.orderByChild("mail").equalTo(emailID);
                query8.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            myScore = dataSnapshot1.getValue(MyScore.class);
                            dataSnapshot1.getRef().child("flowControl").setValue(crct);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "ObjectsCollections":
                Query query9 = databaseReference.orderByChild("mail").equalTo(emailID);
                query9.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            myScore = dataSnapshot1.getValue(MyScore.class);
                            dataSnapshot1.getRef().child("objectsandCollections").setValue(crct);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "Threads":
                Query query10 = databaseReference.orderByChild("mail").equalTo(emailID);
                query10.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            myScore = dataSnapshot1.getValue(MyScore.class);
                            dataSnapshot1.getRef().child("threads").setValue(crct);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "JAVAQuestions":
                Query query11 = databaseReference.orderByChild("mail").equalTo(emailID);
                query11.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            myScore = dataSnapshot1.getValue(MyScore.class);
                            dataSnapshot1.getRef().child("overallQuestions").setValue(crct);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        }
    }

    private void update() {

          databaseReference = FirebaseDatabase.getInstance().getReference(e);
          databaseReference.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                      progressBar.setVisibility(View.GONE);
                      modelC = snapshot.getValue(ModelC.class);
                      modelCArrayList.add(modelC);

                  }
                  adapter.notifyDataSetChanged();
              }
              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {
                  Toast.makeText(ResultActivity.this, "Errrorrr", Toast.LENGTH_SHORT).show();
              }
          });
        }
    public void onBackPressed() {
        super.onBackPressed();
        Intent m = new Intent(getApplicationContext(),JavaActivity.class);
        startActivity(m);
    }
}
