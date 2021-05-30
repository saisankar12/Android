package com.example.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    /*Payment API_KEY = rzp_test_xSfvdapDyzVMGz*/

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textView);

        //Preload payment resources

        Checkout.preload(getApplicationContext());

        Button b1 = findViewById(R.id.button);
                b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });
    }

    public void startPayment() {
        //checkout.setKeyID("<YOUR_KEY_ID>");    /**   * Instantiate Checkout   */
        Checkout checkout = new Checkout();  /**   * Set your logo here   */
        //checkout.setKeyID("rzp_test_xSfvdapDyzVMGz");
        //checkout.setImage(R.mipmap.ic_launcher_round);  /**   * Reference to current activity   */
        final Activity activity = this;  /**   * Pass your payment options to the Razorpay Checkout as a JSONObject   */
        try {
            JSONObject options = new JSONObject();      /**      * Merchant Name      * eg: ACME Corp || HasGeek etc.      */
            options.put("name", "Merchant Name");      /**      * Description can be anything      * eg: Reference No. #123123 - This order number is passed by you for your internal reference. This is not the `razorpay_order_id`.      *     Invoice Payment      *     etc.      */
            options.put("description", "Test Order");
            //options.put("order_id", "order_9A33XWu170gUtm");
            options.put("currency", "INR");      /**      * Amount is always passed in currency subunits      * Eg: "500" = INR 5.00      */
            options.put("amount", "500");
            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e("ErrorMessage", "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        tv.setText("Payment Sucess");
        tv.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.i("fail",s);
        tv.setText("Payment Fail");
        tv.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
    }
}