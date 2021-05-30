package in.apssdc.attendance.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import in.apssdc.attendance.R;
import in.apssdc.attendance.common.CheckInternetGps;
import in.apssdc.attendance.common.CheckNetworkConnection;
import in.apssdc.attendance.dialogs.MyCustomProgressDialog;
import in.apssdc.attendance.model.EmployeeDetails;
import in.apssdc.attendance.model.IssueModel;
import in.apssdc.attendance.model.Response;

public class IssueActivity extends AppCompatActivity{

    Spinner sp_issueType;
    EditText et_issue;
    Button bt_submit;
    IssueModel issueModel;
    String[] values;
    EmployeeDetails employeeDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);
        sp_issueType=(Spinner)findViewById(R.id.issue_type);
        et_issue=(EditText)findViewById(R.id.issue);
        bt_submit=(Button)findViewById(R.id.submit);
        values=new String[]{"Choose issue type","Assigned location related","Application related","Others"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_text_color,values);
        sp_issueType.setAdapter(adapter);
        employeeDetails = (EmployeeDetails) getIntent().getSerializableExtra("emp_object");
        issueModel=new IssueModel();

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sp_issueType.getSelectedItem().toString().equalsIgnoreCase("Choose issue type")){
                    Toast.makeText(getApplicationContext(),"Choose issue type",Toast.LENGTH_SHORT).show();
                }else if(et_issue.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(),"Enter issue",Toast.LENGTH_SHORT).show();
                }else{
                    if(CheckNetworkConnection.isConnectionAvailable(IssueActivity.this)) {
                        try{
                            issueModel.setAttendeeId(employeeDetails.getTraineeid());
                            issueModel.setEmpName(employeeDetails.getName());
                            issueModel.setEmpEmail(employeeDetails.getEmail());
                            issueModel.setIssue(et_issue.getText().toString());
                            issueModel.setIssueType(sp_issueType.getSelectedItem().toString());
                            Calendar cal = Calendar.getInstance();
                            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                            String strDate=formatter.format(cal.getTime());
                            issueModel.setIssueDate((Date)formatter.parse(strDate));
                            new IssueTask(IssueActivity.this).execute();
                        }catch (Exception e){e.printStackTrace();}
                    }else{
                        new CheckInternetGps(IssueActivity.this).callInternetAlert();
                    }
                }
            }
        });
    }
    class IssueTask extends AsyncTask<Void,Void,Response>{

        private final ProgressDialog progressDialog;
        boolean b;

        public IssueTask(Context ctx) {
            progressDialog = MyCustomProgressDialog.ctor(ctx);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{
                progressDialog.show();
            }catch(Exception e){e.printStackTrace();}
        }
        @Override
        protected Response doInBackground(Void... voids) {
            Response response=null;
            try{
                final String URL=getString(R.string.issue);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                response=restTemplate.postForObject(URL,issueModel,Response.class);
            }catch (Exception e){
                e.printStackTrace();
            }
            return response;
        }
        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            progressDialog.dismiss();
            try{
                JSONObject object=new JSONObject(response.toString());
                b=object.getBoolean("successful");
                if(b){
                    Toast.makeText(getApplicationContext(),"Issue submitted successfully",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }catch (Exception e){e.printStackTrace();}
        }
    }
}
