package in.apssdc.attendance.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import in.apssdc.attendance.R;
import in.apssdc.attendance.activities.LoginActivity;
import in.apssdc.attendance.common.CheckInternetGps;
import in.apssdc.attendance.common.CheckNetworkConnection;
import in.apssdc.attendance.model.BiometricData;
import in.apssdc.attendance.model.EmployeeDetails;
import in.apssdc.attendance.model.Response;

public class AttendanceFragment extends Fragment {

    TextView tv_email,tv_inTime,tv_outTime;
    Button inTime,outTime,submit;
    BiometricData attendance;
    MyInterface my_interface;
    ProgressBar progressBar;
    SimpleDateFormat mdformat;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.attendance_fragment, container, false);
        tv_email=(TextView)v.findViewById(R.id.email);
        tv_email.setText(my_interface.getEmpDetails().getEmail());
        inTime=(Button)v.findViewById(R.id.in_time);
        outTime=(Button)v.findViewById(R.id.out_time);
        submit=(Button)v.findViewById(R.id.submit);
        tv_inTime=(TextView) v.findViewById(R.id.tv_inTime);
        tv_outTime=(TextView)v.findViewById(R.id.tv_outTime);
        progressBar =(ProgressBar)v.findViewById(R.id.progressBar);
        attendance=new BiometricData();

        if(isDateAutomatic(getActivity()) && isTimeAutomatic(getActivity())){

            //inDate local storage
            SharedPreferences sp_get=getActivity().getSharedPreferences("attendaceInDate", Context.MODE_PRIVATE);
            String todayDate= sp_get.getString("current", "");
            mdformat = new SimpleDateFormat("yyyy / MM / dd ");
            Date dateobj = new Date();
            String currentDate=mdformat.format(dateobj);

            if(todayDate.equals(currentDate)){
                //same date; disable button
                //get Intime value
                SharedPreferences sp_getinTime =getActivity().getSharedPreferences("InTime", Context.MODE_PRIVATE);
                String inTIME= sp_getinTime.getString("in_time", "time");
                if(!inTIME.equalsIgnoreCase("time")){
                    tv_inTime.setText(inTIME);
                    inTime.setEnabled(false);
                    inTime.setBackgroundResource(R.drawable.button_bg_change);
                }
            }else{
                //different date; allow button click
                tv_inTime.setText("-- : --");
                inTime.setEnabled(true);
                inTime.setBackgroundResource(R.drawable.button_bg_rounded_corners_logout);
            }
            //outDate local storage
            SharedPreferences sp_getOut=getActivity().getSharedPreferences("attendaceOutDate", Context.MODE_PRIVATE);
            String outDate= sp_getOut.getString("current", "");
            if(outDate.equals(currentDate)){
                //same date; disable button
                SharedPreferences sp_getinTime =getActivity().getSharedPreferences("OutTime", Context.MODE_PRIVATE);
                String outTIME= sp_getinTime.getString("out_time", "time");
                if(!outTIME.equalsIgnoreCase("time")){
                    tv_outTime.setText(outTIME);
                    outTime.setEnabled(false);
                    outTime.setBackgroundResource(R.drawable.button_bg_change);
                }
            }else{
                //different date; allow button click
                tv_outTime.setText("-- : --");
                outTime.setEnabled(true);
                outTime.setBackgroundResource(R.drawable.button_bg_rounded_corners_logout);
            }
        }else if(!isDateAutomatic(getActivity()) && !isTimeAutomatic(getActivity())){
            Toast.makeText(getActivity(),"Enable Automatice date & time and time Zone",Toast.LENGTH_LONG).show();
            startActivityForResult(new Intent(Settings.ACTION_DATE_SETTINGS), 0);
        }

        inTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDateAutomatic(getActivity()) && isTimeAutomatic(getActivity())){
                    inTime.setEnabled(false);
                    outTime.setEnabled(true);
                    String inTimeDate = (DateFormat.format("yyyy-MM-dd hh:mm:ss", new Date()).toString());
                    SharedPreferences sp_inTime =getActivity().getSharedPreferences("InTime", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sp_inTime.edit();
                    editor.putString("in_time",inTimeDate);
                    editor.commit();
                    tv_inTime.setText(inTimeDate);
                    //Store current date in Preferences
                    mdformat = new SimpleDateFormat("yyyy / MM / dd ");
                    Date inDate = new Date();
                    SharedPreferences sp_storeInTime=getActivity().getSharedPreferences("attendaceInDate", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editorInTime=sp_storeInTime.edit();
                    editorInTime.putString("current",mdformat.format(inDate));
                    editorInTime.commit();
                }else if(!isDateAutomatic(getActivity()) && !isTimeAutomatic(getActivity())){
                    Toast.makeText(getActivity(),"Enable Automatice date & time and time Zone",Toast.LENGTH_LONG).show();
                    startActivityForResult(new Intent(Settings.ACTION_DATE_SETTINGS), 0);
                }
            }
        });
        outTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDateAutomatic(getActivity()) && isTimeAutomatic(getActivity())){
                    if(tv_inTime.getText().toString().equalsIgnoreCase("-- : --")){
                        Toast.makeText(getActivity(),"Please give in time",Toast.LENGTH_SHORT).show();
                    }else{
                        submit.setEnabled(true);
                        String outTimeDate = (DateFormat.format("yyyy-MM-dd hh:mm:ss", new Date()).toString());
                        SharedPreferences sp_inTime =getActivity().getSharedPreferences("OutTime", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=sp_inTime.edit();
                        editor.putString("out_time",outTimeDate);
                        editor.commit();
                        tv_outTime.setText(outTimeDate);
                        //Store current date in Preferences
                        mdformat = new SimpleDateFormat("yyyy / MM / dd ");
                        Date outDate = new Date();
                        SharedPreferences sp_storeOutTime=getActivity().getSharedPreferences("attendaceOutDate", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editorOutTime=sp_storeOutTime.edit();
                        editorOutTime.putString("current",mdformat.format(outDate));
                        editorOutTime.commit();
                    }
                }else if(!isDateAutomatic(getActivity()) && !isTimeAutomatic(getActivity())){
                    Toast.makeText(getActivity(),"Enable Automatice date & time and time Zone",Toast.LENGTH_LONG).show();
                    startActivityForResult(new Intent(Settings.ACTION_DATE_SETTINGS), 0);
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tv_inTime.getText().toString().equalsIgnoreCase("-- : --")){
                    Toast.makeText(getActivity(),"Please give in time",Toast.LENGTH_SHORT).show();
                }else{
                    inTime.setBackgroundResource(R.drawable.button_bg_change);
                    attendance.setUid(my_interface.getEmpDetails().getAadharId());
                    attendance.setEmployeeId(my_interface.getEmpDetails().getTraineeid());
                    if(my_interface.getEmpDetails().getLatitude()==null){
                        attendance.setLatitude(0.0);
                        attendance.setLongitude(0.0);
                    }else{
                        attendance.setLatitude(my_interface.getEmpDetails().getLatitude());
                        attendance.setLongitude(my_interface.getEmpDetails().getLongitude());
                    }
                    attendance.setImei(my_interface.getEmpDetails().getImeiNumber());
                    attendance.setLogTime(tv_inTime.getText().toString());
                    attendance.setLogUser(my_interface.getEmpDetails().getEmail());
                    attendance.setName(my_interface.getEmpDetails().getName());
                    attendance.setEmail(my_interface.getEmpDetails().getEmail());
                    attendance.setDeviceId(Build.SERIAL);
                    attendance.setStatus("EMPLOYEE");
                    attendance.setAttendanceType("GEOFENCE");
                    if(tv_outTime.getText().toString().equalsIgnoreCase("-- : --"))
                    {
                        attendance.setOutTime(null);
                        if (CheckNetworkConnection.isConnectionAvailable(getActivity())) {
                            new AttendanceTask().execute();
                        }else{
                            new CheckInternetGps(getActivity()).callInternetAlert();
                        }
                    }else
                    {
                        outTime.setBackgroundResource(R.drawable.button_bg_change);
                        outTime.setEnabled(false);
                        attendance.setOutTime(tv_outTime.getText().toString());
                        if (CheckNetworkConnection.isConnectionAvailable(getActivity())) {
                            new AttendanceTask().execute();
                        }else{
                            new CheckInternetGps(getActivity()).callInternetAlert();
                        }
                    }
                    submit.setEnabled(false);
                }
            }
        });
        return v;
    }
    class AttendanceTask extends AsyncTask<Void,Void,Response>
    {
        boolean successful;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected Response doInBackground(Void... params) {
            Response response=null;
            try
            {
                final String URL =getString(R.string.attendance);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                response = restTemplate.postForObject(URL,attendance,Response.class);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return response;
        }
        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            progressBar.setVisibility(View.GONE);
            if(response!=null){
                try{
                    JSONObject jsonObject = new JSONObject(response.toString());
                    successful = jsonObject.getBoolean("successful");
                    if (successful) {
                        Toast.makeText(getActivity(),"Success",Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Toast.makeText(getActivity(),"Sorry..failed",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(getActivity(),"Sorry! Please try again",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public interface MyInterface
    {
        public EmployeeDetails getEmpDetails();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        my_interface=(MyInterface)activity;
    }
    //Automatic date & time
    public static boolean isDateAutomatic(Context c){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1){
            return Settings.Global.getInt(c.getContentResolver(),Settings.Global.AUTO_TIME,0)==1;
        }else{
            return Settings.System.getInt(c.getContentResolver(),Settings.System.AUTO_TIME,0)==1;
        }
    }
    //Automatic time zone
    public static boolean isTimeAutomatic(Context c){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1){
            return Settings.Global.getInt(c.getContentResolver(),Settings.Global.AUTO_TIME_ZONE,0)==1;
        }else{
            return Settings.System.getInt(c.getContentResolver(),Settings.System.AUTO_TIME_ZONE,0)==1;
        }
    }
}



