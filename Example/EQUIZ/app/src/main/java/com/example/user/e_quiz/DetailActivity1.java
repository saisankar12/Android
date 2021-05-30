package com.example.user.e_quiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class DetailActivity1 extends AppCompatActivity {

    int s2;
    TextView textView, textViewScore, textViewTimer;
    int total = 5, score = 0;
    int correct = 0, wrong = 0;
    String answer;
    Button button, button1, button2, button3, button4;

    int QuestionNumber = 0;
    int numberOfQuestions = 0;
    Firebase mQuestion, mchoice1, mchoice2, mchoice3, mchoice4, mAnswer;
    ProgressBar progressBarJava;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail1);

        s2 = getIntent().getIntExtra("pos", 0);

        textView = findViewById(R.id.txtQue);
        textViewScore = findViewById(R.id.score);
        textViewTimer = findViewById(R.id.txtTimer);
        button1 = findViewById(R.id.choice1);
        button2 = findViewById(R.id.choice2);
        button3 = findViewById(R.id.choice3);
        button4 = findViewById(R.id.choice4);
        progressBarJava = findViewById(R.id.myJavaProgressBar);

        Firebase.setAndroidContext(getApplicationContext());


        if (s2 == 0) {
            updateQue("LanguageFundamentals");
            updateAnswer("LanguageFundamentals");
        } else if (s2 == 1) {
            updateQue("OperatorsAssignments");
            updateAnswer("OperatorsAssignments");
        } else if (s2 == 2) {
            updateQue("DeclarationsAccessControl");
            updateAnswer("DeclarationsAccessControl");
        } else if (s2 == 3) {
            updateQue("Assertions");
            updateAnswer("Assertions");
        } else if (s2 == 4) {
            updateQue("Exceptions");
            updateAnswer("Exceptions");
        } else if (s2 == 5) {
            updateQue("InnerClass");
            updateAnswer("InnerClass");
        } else if (s2 == 6) {
            updateQue("GarbageCollections");
            updateAnswer("GarbageCollections");
        } else if (s2 == 7) {
            updateQue("Javalang");
            updateAnswer("Javalang");
        } else if (s2 == 8) {
            updateQue("FlowControl");
            updateAnswer("FlowControl");
        } else if (s2 == 9) {
            updateQue("ObjectsCollections");
            updateAnswer("ObjectsCollections");
        } else if (s2 == 10) {
            updateQue("Threads");
            updateAnswer("Threads");
        } else if (s2 == 11) {
            updateQue("JAVAQuestions");
            updateAnswer("JAVAQuestions");
        }

        reverseTimer(180, textViewTimer);
    }

    private void updateQue(final String str) {

        if (numberOfQuestions > 4) {
            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
            intent.putExtra("total", String.valueOf(total));
            intent.putExtra("score", score);
            intent.putExtra("correct", String.valueOf(correct));
            intent.putExtra("wrong", String.valueOf(wrong));
            intent.putExtra("subject", str);
            finish();
            startActivity(intent);
        } else {
            progressBarJava.setVisibility(View.VISIBLE);
            mQuestion = new Firebase("https://e-quiz-a7f0a.firebaseio.com/" + str + "/" + QuestionNumber + "/question");
            mQuestion.addValueEventListener(new com.firebase.client.ValueEventListener() {
                @Override
                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                    progressBarJava.setVisibility(View.GONE);
                    String question = dataSnapshot.getValue(String.class);
                    textView.setText(question);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            mchoice1 = new Firebase("https://e-quiz-a7f0a.firebaseio.com/" + str + "/" + QuestionNumber + "/option1");
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
            mchoice2 = new Firebase("https://e-quiz-a7f0a.firebaseio.com/" + str + "/" + QuestionNumber + "/option2");
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
            mchoice3 = new Firebase("https://e-quiz-a7f0a.firebaseio.com/" + str + "/" + QuestionNumber + "/option3");
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
            mchoice4 = new Firebase("https://e-quiz-a7f0a.firebaseio.com/" + str + "/" + QuestionNumber + "/option4");
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
            mAnswer = new Firebase("https://e-quiz-a7f0a.firebaseio.com/" + str + "/" + QuestionNumber + "/answer");
            mAnswer.addValueEventListener(new com.firebase.client.ValueEventListener() {
                @Override
                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                    answer = dataSnapshot.getValue(String.class);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            QuestionNumber++;
            numberOfQuestions++;
        }

    }

    private void updateAnswer(final String string) {

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button1.getText().equals(answer)) {
                    correct++;
                    score++;
                    updateScore(score);
                    updateQue(string);
                    Toast.makeText(DetailActivity1.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                } else {
                    wrong++;
                    updateQue(string);
                    Toast.makeText(DetailActivity1.this, "Wrong Answer", Toast.LENGTH_SHORT).show();

                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button2.getText().equals(answer)) {
                    correct++;
                    score++;
                    updateScore(score);
                    updateQue(string);
                    Toast.makeText(DetailActivity1.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                } else {
                    wrong++;
                    updateQue(string);
                    Toast.makeText(DetailActivity1.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button3.getText().equals(answer)) {
                    correct++;
                    score++;
                    updateScore(score);
                    updateQue(string);
                    Toast.makeText(DetailActivity1.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                } else {
                    wrong++;
                    updateQue(string);
                    Toast.makeText(DetailActivity1.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button4.getText().equals(answer)) {
                    correct++;
                    score++;
                    updateScore(score);
                    updateQue(string);
                    Toast.makeText(DetailActivity1.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                } else {
                    wrong++;
                    updateQue(string);
                    Toast.makeText(DetailActivity1.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateScore(int score) {
        textViewScore.setText("Score : " + score);
    }

    public void reverseTimer(final int Seconds, final TextView textView) {
        new CountDownTimer(Seconds * 1000 + 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                textView.setText(String.format("%02d", minutes) + ":" + String.format("%02d", seconds));

            }

            @Override
            public void onFinish() {
                textView.setText("Time Up!!");
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                intent.putExtra("total", String.valueOf(total));
                intent.putExtra("score", String.valueOf(score));
                intent.putExtra("correct", String.valueOf(correct));
                intent.putExtra("wrong", String.valueOf(wrong));
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
                startActivity(new Intent(DetailActivity1.this, JavaActivity.class));
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
