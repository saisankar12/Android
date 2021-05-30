package in.apssdc.attendance.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import in.apssdc.attendance.R;
import in.apssdc.attendance.common.CheckInternetGps;
import in.apssdc.attendance.common.CheckNetworkConnection;
import in.apssdc.attendance.model.EmployeeDetails;
import in.apssdc.attendance.model.Response;
import in.apssdc.attendance.model.WorkLog;

public class WorkLogFragment extends Fragment{

    ImageView ivDate;
    TextView tv_date;
    EditText et_assignedTask,et_percentageTask,et_otherWorks,et_issues;
    RadioGroup rg_Status;
    Button b_submit;
    private static final int DATE_DIALOG = 1;
    HashMap<Integer,Dialog> mDialogs = new HashMap<Integer, Dialog>();
    private int day,month,year;
    String assignedTask,otherWorks,issues,status,textDate,percentageCompletion;
    WorkLog work_log;
    WorkLogInterface work_log_interface;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.work_log_fragment, container, false);
        ivDate=(ImageView)v.findViewById(R.id.iv_date);
        et_assignedTask=(EditText)v.findViewById(R.id.taskAssigned);
        rg_Status=(RadioGroup)v.findViewById(R.id.status);
        et_percentageTask=(EditText)v.findViewById(R.id.percentage_status);
        et_otherWorks=(EditText)v.findViewById(R.id.otherWorks);
        et_issues=(EditText)v.findViewById(R.id.issues);
        tv_date=(TextView)v.findViewById(R.id.date);
        b_submit=(Button)v.findViewById(R.id.work_submit);
        setDateToSysdate();
        updateDateDisplay();
        work_log=new WorkLog();
        et_percentageTask.setVisibility(View.GONE);
        progressBar = (ProgressBar)v.findViewById(R.id.progressBar);

        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG);
            }
        });
        rg_Status.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                 if(group.equals(rg_Status)){
                     int id = group.getCheckedRadioButtonId();
                     View radioButton = group.findViewById(id);
                     int radioId = group.indexOfChild(radioButton);
                     RadioButton btn = (RadioButton) group.getChildAt(radioId);
                     status= (String) btn.getText();
                     if(status.equalsIgnoreCase("Completed")){
                         et_percentageTask.setVisibility(View.GONE);
                         et_percentageTask.setText("");
                     }else if(status.equalsIgnoreCase("Pending")){
                         et_percentageTask.setVisibility(View.VISIBLE);
                     }
                 }
            }
        });
        b_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignedTask=et_assignedTask.getText().toString();
                otherWorks=et_otherWorks.getText().toString();
                issues=et_issues.getText().toString();
                textDate=tv_date.getText().toString();
                if(assignedTask.equalsIgnoreCase("")){
                    Toast.makeText(getActivity(),"Enter task details",Toast.LENGTH_SHORT).show();
                }else if (rg_Status.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getActivity(),"Please choose task status", Toast.LENGTH_LONG).show();
                }else
                {
                    work_log.setEmployeeId(work_log_interface.getEmpDetails().getTraineeid());
                    work_log.setTraineeId(work_log_interface.getEmpDetails().getTraineeid());
                    try
                    {
                        if(textDate.length()>0){
                            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            work_log.setDate((Date)formatter.parse(textDate));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    work_log.setScheduledTask(assignedTask);
                    work_log.setOtherTask(otherWorks);
                    work_log.setIssues(issues);
                    work_log.setName(work_log_interface.getEmpDetails().getName());
                    work_log.setPlaceofwork(work_log_interface.getEmpDetails().getPlaceofwork());
                    if(status.equalsIgnoreCase("Completed"))
                    {
                        work_log.setCompleteStatus(status);
                        String per="100%";
                        work_log.setPercentTask(per);
                        if (CheckNetworkConnection.isConnectionAvailable(getActivity())) {
                            new WorkLogTask().execute();
                        }else
                        {
                            new CheckInternetGps(getActivity()).callInternetAlert();
                        }
                    }else if(status.equalsIgnoreCase("Pending"))
                    {
                        work_log.setCompleteStatus(status);
                        percentageCompletion=et_percentageTask.getText().toString();
                        if(percentageCompletion.equalsIgnoreCase(""))
                        {
                            Toast.makeText(getActivity(),"Please enter percentage completion",Toast.LENGTH_SHORT).show();
                        }else
                        {
                            work_log.setPercentTask(percentageCompletion);
                            if (CheckNetworkConnection.isConnectionAvailable(getActivity())) {
                                new WorkLogTask().execute();
                            }else
                            {
                                new CheckInternetGps(getActivity()).callInternetAlert();
                            }
                        }
                    }
                }
            }
        });
        return v;
    }
    private void setDateToSysdate() {
        Calendar c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
    }
    public void showDialog(int dialogId)
    {
        Dialog d = mDialogs.get(dialogId);
        if (d == null){
            d = onCreateDialog(dialogId);
            mDialogs.put(dialogId, d);
        }
        if (d != null){
            onPrepareDialog(d, dialogId);
            d.show();
        }
    }
    private void onPrepareDialog(Dialog d, int dialogId) {
        // change something inside already created Dialogs here
    }
    private Dialog onCreateDialog(int dialogId) {
        switch (dialogId)
        {
            case DATE_DIALOG:
                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(),R.style.DialogTheme, dateSetListener, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                return datePickerDialog;
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int pYear, int monthOfYear, int dayOfMonth) {
            year = pYear;
            month = monthOfYear;
            day = dayOfMonth;
            updateDateDisplay();
        }
    };
    private void updateDateDisplay()
    {
        // Month is 0 based so add 1
        tv_date.setText(String.format("%04d-%02d-%02d", year, month + 1,day));
    }
    class WorkLogTask extends AsyncTask<Void,Void,Response> {
        boolean successful;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected Response doInBackground(Void... params) {
            Response response = null;
            try {
                final String URL =getString(R.string.workLog);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                response = restTemplate.postForObject(URL, work_log, Response.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }
        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            progressBar.setVisibility(View.GONE);
            if(response!=null){
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    successful = jsonObject.getBoolean("successful");
                    if (successful) {
                        Toast.makeText(getActivity(),"successfully Submitted",Toast.LENGTH_SHORT).show();
                        et_assignedTask.setText("");
                        et_percentageTask.setText("");
                        et_otherWorks.setText("");
                        et_issues.setText("");
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(getActivity(),"Sorry! Please try again",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public interface WorkLogInterface
    {
        public EmployeeDetails getEmpDetails();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        work_log_interface=(WorkLogInterface)activity;
    }
}
