package com.example.user.e_quiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.e_quiz.MyModel.ModelC;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class quiz extends AppCompatActivity {

    Button button1,button2,button3,button4;
    TextView textViewQuestion,textViewTimer,textViewScore;

    int score = 0;
    int correct =0,wrong=0;
    int QuestionNumber = 0;
    int total =10;
    String Answer;
    String question;
    ProgressBar p;
    Firebase mQuestion,mchoice1,mchoice2,mchoice3,mchoice4,mAnswer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Firebase.setAndroidContext(getApplicationContext());
        final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.tick);

        p = findViewById(R.id.myProgress);
        //p.setVisibility(View.VISIBLE);

        textViewQuestion = findViewById(R.id.txtQuestion);
        textViewTimer = findViewById(R.id.txtTimer);
        textViewScore = findViewById(R.id.txtscoreview);
        button1 = findViewById(R.id.btnOption1);
        button2 = findViewById(R.id.btnOption2);
        button3 = findViewById(R.id.btnOption3);
        button4 = findViewById(R.id.btnOption4);

        updateQuestion();
        //For Button1
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                if (button1.getText().equals(Answer)){
                    score = score + 1;
                    correct++;
                    updateScore(score);
                    updateQuestion();
                    //button1.setBackgroundColor(Color.GREEN);
                    Toast.makeText(getApplicationContext(), "correct", Toast.LENGTH_SHORT).show();

                }else {
                    wrong++;
                    updateQuestion();
                //    button1.setBackgroundColor(Color.RED);
                    Toast.makeText(getApplicationContext(), "Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //For Button2
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                if (button2.getText().equals(Answer)){
                    score = score + 1;
                    correct++;
                    updateScore(score);
                    updateQuestion();
                 //   button2.setBackgroundColor(Color.GREEN);
                    Toast.makeText(getApplicationContext(), "correct", Toast.LENGTH_SHORT).show();
                }else {
                    wrong++;
                    updateQuestion();
                   // button2.setBackgroundColor(Color.RED);
                    Toast.makeText(getApplicationContext(), "Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //For Button3
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                if (button3.getText().equals(Answer)){
                    score = score + 1;
                    correct++;
                    updateScore(score);
                    updateQuestion();
                    //button3.setBackgroundColor(Color.GREEN);
                    Toast.makeText(getApplicationContext(), "correct", Toast.LENGTH_SHORT).show();
                }else {
                    wrong++;
                    updateQuestion();
                    //button3.setBackgroundColor(Color.RED);
                    Toast.makeText(getApplicationContext(), "Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //For Button4
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                if (button4.getText().equals(Answer)){
                    score = score + 1;
                    correct++;
                    updateScore(score);
                    updateQuestion();
                    //button4.setBackgroundColor(Color.GREEN);
                    Toast.makeText(getApplicationContext(), "correct", Toast.LENGTH_SHORT).show();
                }else {
                    updateQuestion();
                    wrong++;
                    //button4.setBackgroundColor(Color.RED);
                    Toast.makeText(getApplicationContext(), "Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        reverseTimer(300,textViewTimer);
    }
    private void updateScore(int mscore){
        textViewScore.setText("Score : "+score);
    }

    private void updateQuestion() {
       /* total++;
        if (total>9){
                    //Open the REsult Activity
            Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
            intent.putExtra("total",String.valueOf(total));
            intent.putExtra("correct",String.valueOf(correct));
            intent.putExtra("wrong",String.valueOf(wrong));
            startActivity(intent);
        }else{

            //databaseReference = FirebaseDatabase.getInstance().getReference().child("Questions").child(String.valueOf(total));
            databaseReference=FirebaseDatabase.getInstance().getReference("CQuestions").child(String.valueOf(total));

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    final ModelC modelC = dataSnapshot.getValue(ModelC.class);

                    textViewQuestion.setText(modelC.getQuestion());
                    button1.setText(modelC.getOption1());
                    button2.setText(modelC.getOption2());
                    button3.setText(modelC.getOption3());
                    button4.setText(modelC.getOption4());

                    button1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (button1.getText().toString().equals(modelC.getAnswer())){

                                Toast.makeText(quiz.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                                button1.setBackgroundColor(Color.GREEN);
                                correct ++;
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
            //                            correct++;
                                        button1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion();
                                    }
                                },1500);
                            }else {
                                //If answer is wrong....then we will find correct one among the remaining three
                                Toast.makeText(quiz.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                                wrong++;
                                button1.setBackgroundColor(Color.RED);

                                if (button2.getText().toString().equals(modelC.getAnswer())){
                                    button2.setBackgroundColor(Color.GREEN);
                                    button2.setClickable(false);
                                }else if (button3.getText().toString().equals(modelC.getAnswer()))
                                {
                                    button3.setBackgroundColor(Color.GREEN);
                                    button3.setClickable(false);
                                }else if (button4.getText().toString().equals(modelC.getAnswer()))
                                {
                                    button4.setBackgroundColor(Color.GREEN);
                                    button4.setClickable(false);
                                }

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        button1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        button2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        button3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        button4.setBackgroundColor(Color.parseColor("#03A9F4"));

                                        updateQuestion();
                                    }
                                },1500);

                            }
                        }
                    });
                    button2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (button2.getText().toString().equals(modelC.getAnswer())){

                                Toast.makeText(quiz.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                                button2.setBackgroundColor(Color.GREEN);
                                correct ++;
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        button2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion();
                                    }
                                },1500);
                            }else {
                                //If answer is wrong....then we will find correct one among the remaining three
                                Toast.makeText(quiz.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                                wrong++;
                                button2.setBackgroundColor(Color.RED);

                                if (button1.getText().toString().equals(modelC.getAnswer())){
                                    button1.setBackgroundColor(Color.GREEN);
                                }else if (button3.getText().toString().equals(modelC.getAnswer()))
                                {
                                    button3.setBackgroundColor(Color.GREEN);
                                }else if (button4.getText().toString().equals(modelC.getAnswer()))
                                {
                                    button4.setBackgroundColor(Color.GREEN);
                                }

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        button1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        button2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        button3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        button4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion();
                                    }
                                },1500);

                            }
                        }
                    });
                    button3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (button3.getText().toString().equals(modelC.getAnswer())){
                                Toast.makeText(quiz.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                                button3.setBackgroundColor(Color.GREEN);
                                correct ++;
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
              //                          correct++;
                                        button3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion();
                                    }
                                },1500);
                            }else {
                                //If answer is wrong....then we will find correct one among the remaining three
                                Toast.makeText(quiz.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                                wrong++;
                                button3.setBackgroundColor(Color.RED);

                                if (button1.getText().toString().equals(modelC.getAnswer())){
                                    button1.setBackgroundColor(Color.GREEN);
                                }else if (button2.getText().toString().equals(modelC.getAnswer()))
                                {
                                    button2.setBackgroundColor(Color.GREEN);
                                }else if (button4.getText().toString().equals(modelC.getAnswer()))
                                {
                                    button4.setBackgroundColor(Color.GREEN);
                                }

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        button1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        button2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        button3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        button4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion();
                                    }
                                },1500);

                            }
                        }
                    });
                    button4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (button4.getText().toString().equals(modelC.getAnswer())){
                                Toast.makeText(quiz.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                                button4.setBackgroundColor(Color.GREEN);
                                correct ++;
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                //                        correct++;
                                        button4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion();
                                    }
                                },1500);
                            }else {
                                //If answer is wrong....then we will find correct one among the remaining three
                                Toast.makeText(quiz.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                                wrong++;
                                button4.setBackgroundColor(Color.RED);

                                if (button1.getText().toString().equals(modelC.getAnswer())){
                                    button1.setBackgroundColor(Color.GREEN);
                                }else if (button3.getText().toString().equals(modelC.getAnswer()))
                                {
                                    button3.setBackgroundColor(Color.GREEN);
                                }else if (button2.getText().toString().equals(modelC.getAnswer()))
                                {
                                    button2.setBackgroundColor(Color.GREEN);
                                }

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        button1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        button2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        button3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        button4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                        updateQuestion();
                                    }
                                },1500);

                            }
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
*/
        //p.setVisibility(View.GONE);
        if (QuestionNumber > 9) {
            Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
            intent.putExtra("total",String.valueOf(total));
            intent.putExtra("correct",String.valueOf(correct));
            intent.putExtra("wrong",String.valueOf(wrong));
            intent.putExtra("score",score);
            intent.putExtra("subject","CQuestions");
            startActivity(intent);
        } else {
            p.setVisibility(View.VISIBLE);
            mQuestion = new Firebase("https://e-quiz-a7f0a.firebaseio.com/CQuestions/" + QuestionNumber + "/question");
            mQuestion.addValueEventListener(new com.firebase.client.ValueEventListener() {
                @Override
                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                    p.setVisibility(View.GONE);
                    question = dataSnapshot.getValue(String.class);
                    textViewQuestion.setText(question);
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            mchoice1 = new Firebase("https://e-quiz-a7f0a.firebaseio.com/CQuestions/" + QuestionNumber + "/option1");
            mchoice1.addValueEventListener(new com.firebase.client.ValueEventListener() {
                @Override
                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                    String choice1 = dataSnapshot.getValue(String.class);
                    button1.setText(choice1);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            mchoice2 = new Firebase("https://e-quiz-a7f0a.firebaseio.com/CQuestions/" + QuestionNumber + "/option2");
            mchoice2.addValueEventListener(new com.firebase.client.ValueEventListener() {
                @Override
                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                    String choice2 = dataSnapshot.getValue(String.class);
                    button2.setText(choice2);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            mchoice3 = new Firebase("https://e-quiz-a7f0a.firebaseio.com/CQuestions/" + QuestionNumber + "/option3");
            mchoice3.addValueEventListener(new com.firebase.client.ValueEventListener() {
                @Override
                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                    String choice3 = dataSnapshot.getValue(String.class);
                    button3.setText(choice3);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            mchoice4 = new Firebase("https://e-quiz-a7f0a.firebaseio.com/CQuestions/" + QuestionNumber + "/option4");
            mchoice4.addValueEventListener(new com.firebase.client.ValueEventListener() {
                @Override
                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                    String choice4 = dataSnapshot.getValue(String.class);
                    button4.setText(choice4);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            mAnswer = new Firebase("https://e-quiz-a7f0a.firebaseio.com/CQuestions/" + QuestionNumber + "/answer");
            mAnswer.addValueEventListener(new com.firebase.client.ValueEventListener() {
                @Override
                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                    Answer = dataSnapshot.getValue(String.class);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            QuestionNumber++;
        }
    }

    public void reverseTimer(final int Seconds, final TextView textView){
        new CountDownTimer(Seconds*1000 + 1000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                textViewTimer.setText(String.format("%02d",minutes) + ":" + String.format("%02d",seconds));
            }
            @Override
            public void onFinish() {
                Toast.makeText(quiz.this, "Time Up!!", Toast.LENGTH_LONG).show();
               // textView.setText("Time Up!!");
                textViewTimer.setText("Time up!");
                Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
                //intent.putExtra("total",String.valueOf(total));
                // intent.putExtra("score",String.valueOf(score));
                //intent.putExtra("correct",String.valueOf(correct));
                //intent.putExtra("wrong",String.valueOf(wrong));
                //startActivity(intent);

                intent.putExtra("total",String.valueOf(total));
                intent.putExtra("correct",String.valueOf(correct));
                intent.putExtra("wrong",String.valueOf(wrong));
                intent.putExtra("score",score);
                intent.putExtra("subject","CQuestions");
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
