package com.example.paymentgateways;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    TextView tv;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.status);

        // for intialize the payment
        Checkout.preload(this);

        findViewById(R.id.payment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Checkout checkout = new Checkout();

                final Activity activity = MainActivity.this;

                try {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("name", "Merchant Name");
                    jsonObject.put("description", "Test Order");
                    jsonObject.put("amount", "1000");
                    jsonObject.put("currency", "INR");

                    checkout.open(activity, jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        et = findViewById(R.id.edittext);
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = et.getText().toString();
                Intent i = new Intent(MainActivity.this, SecondActivity.class);
                i.putExtra("data", s);
                startActivityForResult(i,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra(SecondActivity.key);
                tv.setText(result);
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onPaymentSuccess(String s) {
        tv.setText(s);
        tv.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
    }

    @Override
    public void onPaymentError(int i, String s) {
        tv.setText(s);
        tv.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
    }
}