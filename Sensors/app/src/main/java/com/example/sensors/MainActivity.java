package com.example.sensors;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.nisrulz.sensey.FlipDetector;
import com.github.nisrulz.sensey.LightDetector;
import com.github.nisrulz.sensey.RotationAngleDetector;
import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.ShakeDetector;
import com.github.nisrulz.sensey.TouchTypeDetector;


public class MainActivity extends AppCompatActivity {
   // ShakeDetector.ShakeListener shakeListener;


    Switch s1,s2,s3,s4,s5;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        s1 = findViewById(R.id.switch1);
        s2 = findViewById(R.id.switch2);
        s3 = findViewById(R.id.switch3);
        s4 = findViewById(R.id.switch4);
        s5 = findViewById(R.id.switch5);


        tv = findViewById(R.id.textView);
        Sensey.getInstance().init(this);



        final ShakeDetector.ShakeListener shakeListener=new ShakeDetector.ShakeListener() {
            @Override
            public void onShakeDetected() {
                //Sensey.getInstance().startShakeDetection(shakeListener);
                //Toast.makeText(MainActivity.this, "Shake Detect", Toast.LENGTH_SHORT).show();

                tv.setText("Shake Detected!");
            }

            @Override
            public void onShakeStopped() {
                //Toast.makeText(MainActivity.this, "Shake Stopped", Toast.LENGTH_SHORT).show();
                //Sensey.getInstance().stopShakeDetection(shakeListener);

                tv.setText("Shake Stopped!");
            }
        };

        final LightDetector.LightListener lightListener=new LightDetector.LightListener() {
            @Override
            public void onDark() {
                tv.setText("Dark Detected!");
            }

            @Override
            public void onLight() {
                tv.setText("Light Detected!");
            }
        };

        final RotationAngleDetector.RotationAngleListener angleListener=new RotationAngleDetector.RotationAngleListener() {
            @Override
            public void onRotation(float v, float v1, float v2) {

                tv.setText("x-->"+v+"\n"+"y-->"+v1+"\n"+"z-->"+v2);

            }
        };

        final FlipDetector.FlipListener flipListener=new FlipDetector.FlipListener() {
            @Override
            public void onFaceDown() {
                tv.setText("Face Down");
            }

            @Override
            public void onFaceUp() {
                tv.setText("Face Up");
            }
        };

        final TouchTypeDetector.TouchTypListener touchTypListener=new TouchTypeDetector.TouchTypListener() {
            @Override
            public void onDoubleTap() {
                tv.setText("Double Tap Detected!");
            }

            @Override
            public void onLongPress() {
                tv.setText("Long Press Detected!");
            }

            @Override
            public void onScroll(int i) {
                tv.setText("Scrolling Detected!");
            }

            @Override
            public void onSingleTap() {
                tv.setText("Single Tap Detected!");
            }

            @Override
            public void onSwipe(int i) {
                tv.setText("Swipe Detected!");
            }

            @Override
            public void onThreeFingerSingleTap() {
                tv.setText("Three Fingers Tap Detected!");
            }

            @Override
            public void onTwoFingerSingleTap() {
                tv.setText("Two Fingers Tap Detected!");
            }
        };


        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    tv.setVisibility(View.VISIBLE);
                    Sensey.getInstance().startShakeDetection(shakeListener);
                }else {
                    tv.setVisibility(View.GONE);
                    Sensey.getInstance().stopShakeDetection(shakeListener);
                }
            }
        });

        s2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    tv.setVisibility(View.VISIBLE);
                    Sensey.getInstance().startLightDetection(lightListener);
                }else {
                    tv.setVisibility(View.GONE);
                    Sensey.getInstance().stopLightDetection(lightListener);
                }
            }
        });

        s3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    tv.setVisibility(View.VISIBLE);
                    Sensey.getInstance().startRotationAngleDetection(angleListener);
                }else {
                    tv.setVisibility(View.GONE);
                    Sensey.getInstance().stopRotationAngleDetection(angleListener);
                }
            }
        });

        s4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    tv.setVisibility(View.VISIBLE);
                    Sensey.getInstance().startFlipDetection(flipListener);
                }else {
                    tv.setVisibility(View.GONE);
                    Sensey.getInstance().stopFlipDetection(flipListener);
                }
            }
        });

        s5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    tv.setVisibility(View.VISIBLE);
                    Sensey.getInstance().startTouchTypeDetection(MainActivity.this,touchTypListener);
                }else {
                    tv.setVisibility(View.GONE);
                    Sensey.getInstance().stopTouchTypeDetection();
                }
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // Setup onTouchEvent for detecting type of touch gesture
        Sensey.getInstance().setupDispatchTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // *** IMPORTANT ***
        // Stop Sensey and release the context held by it
        Sensey.getInstance().stop();
    }

}



















