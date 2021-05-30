package com.example.user.e_quiz;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.e_quiz.MyModel.ModelC;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class OSQuiz extends AppCompatActivity {

    TextView txtQuestion,textViewTtimer,textViewScore;
    RadioButton rda,rdb,rdc,rdd;
    Button butNext;
    RadioGroup radioGroup;
    int total = 0,score=0;
    int correct=1,wrong=0;
    String my_answer ;
    DatabaseReference databaseReference;

    ModelC modelC;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_osquiz);

        txtQuestion=(TextView)findViewById(R.id.textView1);
        textViewTtimer = findViewById(R.id.txtOSTimer);
        textViewScore = findViewById(R.id.txtscore);

        rda=(RadioButton)findViewById(R.id.radio0);
        rdb=(RadioButton)findViewById(R.id.radio1);
        rdc=(RadioButton)findViewById(R.id.radio2);
        rdd = findViewById(R.id.radio3);
        radioGroup = findViewById(R.id.radioGroup1);
        rdd.setBackgroundResource(R.raw.tick);


        butNext=(Button)findViewById(R.id.button1);



        updateQue();

        butNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radio button by returned id
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                String selectedtext = radioButton.getText().toString();
                total++;
                updateQue();
                if (radioGroup.getCheckedRadioButtonId() <= 0)
                {
                   Log.i("Question","Select Any Answer");
                   Toast.makeText(OSQuiz.this, "Select Any Answer", Toast.LENGTH_SHORT).show();
                    }
            else {
                    if (selectedtext.equals(my_answer)) {
                        Log.i("selected_text", selectedtext);
                        Log.i("my_answer", my_answer);
                        correct++;
                        score++;
                        updateScore(score);
                        Toast.makeText(OSQuiz.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                    } else {
                        wrong++;
                        Toast.makeText(OSQuiz.this, "Wrong Answer", Toast.LENGTH_SHORT).show();

                    }
                    radioGroup.clearCheck();
                }    }
        });
        reverseTimer(300,textViewTtimer);
    }

    private void updateQue() {

        if (total > 14) {
            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
            intent.putExtra("total", String.valueOf(total));
            intent.putExtra("correct", String.valueOf(correct));
            intent.putExtra("wrong", String.valueOf(wrong));
            intent.putExtra("subject","OSQuestions");
            intent.putExtra("score",score);
            startActivity(intent);
        } else {
            databaseReference = FirebaseDatabase.getInstance().getReference("OSQuestions").child(String.valueOf(total));
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    modelC = dataSnapshot.getValue(ModelC.class);

                    txtQuestion.setText(modelC.getQuestion());
                    rda.setText(modelC.getOption1());
                    rdb.setText(modelC.getOption2());
                    rdc.setText(modelC.getOption3());
                    rdd.setText(modelC.getOption4());
                    my_answer=modelC.getAnswer();
                    Log.i("my_answer",my_answer);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });
        }
    }
    public void reverseTimer(final int Seconds, final TextView textView){
        new CountDownTimer(Seconds*1000 + 1000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                textView.setText(String.format("%02d",minutes) + ":" + String.format("%02d",seconds));

            }
            @Override
            public void onFinish() {
                textView.setText("Time Up!!");
                Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
                intent.putExtra("total",String.valueOf(total));
                intent.putExtra("score",String.valueOf(correct+wrong));
                intent.putExtra("correct",String.valueOf(correct));
                intent.putExtra("wrong",String.valueOf(wrong));
                startActivity(intent);

            }
        }.start();

    }


    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Play Or Quit");
        builder.setMessage("Do you want to quit the Quiz..? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Toast.makeText(OSQuiz.this, "hiiiiii", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Toast.makeText(OSQuiz.this, "hiiiiijjjjji", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }


    private void updateScore(int score){
        textViewScore.setText("Score : "+score);
    }

}
