package com.example.user.e_quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RulesAndInstructions extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_and_instructions);

      /*  button = findViewById(R.id.btnTakeQuiz);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RulesAndInstructions.this, DetailActivity1.class));
                Toast.makeText(RulesAndInstructions.this, "Custom Dialog", Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}
