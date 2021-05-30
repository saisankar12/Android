package com.example.intents;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText et1;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=findViewById(R.id.explicit);
        et1=findViewById(R.id.dial);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,
                        Main2Activity.class);
                intent.putExtra("AMAN",et1.getText().toString());
                startActivity(intent);
            }
        });

    }

    public void implicit(View view) {
        int num= Integer.parseInt(et1.getText().toString());
        Uri uri=Uri.parse("tel:"+num);
        Intent i=new Intent(Intent.ACTION_DIAL,uri);
        startActivity(i);
    }
}
