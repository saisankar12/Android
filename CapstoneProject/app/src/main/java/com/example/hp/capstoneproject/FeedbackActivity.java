package com.example.hp.capstoneproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FeedbackActivity extends AppCompatActivity {
    public static final String server_url = "https://capstoneproject-8bec8.firebaseio.com/";
    DatabaseReference databaseReference;
    public static final String Database_path = "user_feedback";
    Firebase firebase;
    @InjectView(R.id.EditTextFeedbackBody)
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.inject(this);
        Firebase.setAndroidContext(FeedbackActivity.this);
        firebase = new Firebase(server_url);
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_path);
    }

    public void submitFeedback(View view) {
        String feedback = editText.getText().toString();
        String feedbackRecord = databaseReference.push().getKey();
        databaseReference.child(feedbackRecord).setValue(feedback);
        Toast.makeText(this, "Feedback stored successfully", Toast.LENGTH_SHORT).show();
    }
}
