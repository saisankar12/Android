
package com.example.user.e_quiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JavaQuiz extends AppCompatActivity {

    TextView textViewJQuestion,mScoreview,textViewJTimer;
    Button buttonj1,buttonj2,buttonj3,buttonj4;

   // public List<Integer> questionNos = new ArrayList<>();
    int numberOfQuestions = 0;
    int QuestionNumber =0;
    int total = 10;
    int score = 0;
    int correct =0,wrong=0;

    String Answer;

    Firebase mQuestion,mchoice1,mchoice2,mchoice3,mchoice4,mAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_quiz);

        Firebase.setAndroidContext(getApplicationContext());

        final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.tick);
        textViewJQuestion = findViewById(R.id.jquestion);
        mScoreview = findViewById(R.id.score);
        textViewJTimer = findViewById(R.id.txtJTimer);

        buttonj1 = findViewById(R.id.choice1);
        buttonj2 = findViewById(R.id.choice2);
        buttonj3 = findViewById(R.id.choice3);
        buttonj4 = findViewById(R.id.choice4);
        updateJQuestion();
        //For Button1
        buttonj1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                if (buttonj1.getText().equals(Answer)){
                    score = score + 1;
                    correct++;
                    updateScore(score);
                    updateJQuestion();
                   // Toast.makeText(getApplicationContext(), "correct", Toast.LENGTH_SHORT).show();
                   }else {
                    wrong++;
                    updateJQuestion();
                    //Toast.makeText(getApplicationContext(), "Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //For Button2
        buttonj2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                if (buttonj2.getText().equals(Answer)){
                    score = score + 1;
                    correct++;
                    updateScore(score);
                    updateJQuestion();
                    //Toast.makeText(getApplicationContext(), "correct", Toast.LENGTH_SHORT).show();
                }else {
                    wrong++;
                    updateJQuestion();
                    //Toast.makeText(getApplicationContext(), "Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //For Button3
        buttonj3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                if (buttonj3.getText().equals(Answer)){
                    score = score + 1;
                    correct++;
                    updateScore(score);
                    updateJQuestion();
                    //Toast.makeText(getApplicationContext(), "correct", Toast.LENGTH_SHORT).show();
                }else {
                    wrong++;
                    updateJQuestion();
                    //Toast.makeText(getApplicationContext(), "Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //For Button4
        buttonj4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                if (buttonj4.getText().equals(Answer)){
                    score = score + 1;
                    correct++;
                    updateScore(score);
                    updateJQuestion();
                    //Toast.makeText(getApplicationContext(), "correct", Toast.LENGTH_SHORT).show();
                }else {
                    updateJQuestion();
                    wrong++;
                    //Toast.makeText(getApplicationContext(), "Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        reverseTimer(3000,textViewJTimer);
    }

    private void updateScore(int mscore){
        mScoreview.setText("Score : "+score);
    }
    private void updateJQuestion() {

        if (numberOfQuestions > 9) {
            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
            intent.putExtra("total",String.valueOf(total));
            intent.putExtra("score",score);
            intent.putExtra("correct", String.valueOf(correct));
            intent.putExtra("wrong", String.valueOf(wrong));
            intent.putExtra("subject","JAVAQuestions");
            startActivity(intent);
        } else {
            mQuestion = new Firebase("https://e-quiz-a7f0a.firebaseio.com/JAVAQuestions/" + QuestionNumber + "/question");
            mQuestion.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String question = dataSnapshot.getValue(String.class);
                    textViewJQuestion.setText(question);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            mchoice1 = new Firebase("https://e-quiz-a7f0a.firebaseio.com/JAVAQuestions/" + QuestionNumber + "/option1");
            mchoice1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String choice1 = dataSnapshot.getValue(String.class);
                    buttonj1.setText(choice1);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            mchoice2 = new Firebase("https://e-quiz-a7f0a.firebaseio.com/JAVAQuestions/" + QuestionNumber + "/option2");
            mchoice2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String choice2 = dataSnapshot.getValue(String.class);
                    buttonj2.setText(choice2);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            mchoice3 = new Firebase("https://e-quiz-a7f0a.firebaseio.com/JAVAQuestions/" + QuestionNumber + "/option3");
            mchoice3.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String choice3 = dataSnapshot.getValue(String.class);
                    buttonj3.setText(choice3);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            mchoice4 = new Firebase("https://e-quiz-a7f0a.firebaseio.com/JAVAQuestions/" + QuestionNumber + "/option4");
            mchoice4.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String choice4 = dataSnapshot.getValue(String.class);
                    buttonj4.setText(choice4);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            mAnswer = new Firebase("https://e-quiz-a7f0a.firebaseio.com/JAVAQuestions/" + QuestionNumber + "/answer");
            mAnswer.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Answer = dataSnapshot.getValue(String.class);
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            QuestionNumber++;
            numberOfQuestions++;
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
                intent.putExtra("score",String.valueOf(score));
                intent.putExtra("correct",String.valueOf(correct));
                intent.putExtra("wrong",String.valueOf(wrong));
                startActivity(intent);

            }
        }.start();
    }

    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Play Or Quit");
        builder.setMessage("Do you want to quit the Quiz..? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.show();
    }
    }
