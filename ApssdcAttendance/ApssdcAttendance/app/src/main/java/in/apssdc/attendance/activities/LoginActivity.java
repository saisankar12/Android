package in.apssdc.attendance.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import in.apssdc.attendance.R;
import in.apssdc.attendance.adapter.CropingOptionAdapter;
import in.apssdc.attendance.common.CheckInternetGps;
import in.apssdc.attendance.common.CheckNetworkConnection;
import in.apssdc.attendance.common.PrefManager;
import in.apssdc.attendance.model.CropingOption;
import in.apssdc.attendance.model.EmployeeDetails;
import in.apssdc.attendance.model.Login;
import in.apssdc.attendance.model.Response;

public class LoginActivity extends AppCompatActivity{

    EditText et_email,et_password;
    ImageView iv_login,iv_user,iv_plus;
    Login login;
    TextView scrollText;
    EmployeeDetails employeeDetails;
    String userName,password,imageUrl;
    private File outPutFile = null;
    private Uri imageCaptureUri;
    private static final int CAMERA_CODE = 101, GALLERY_CODE = 102, CROPING_CODE = 103;
    private final static int PERMISSION_REQ_CODE = 1;
    PrefManager prefManager;
    LocationManager lManager;
    int requestCode=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        et_email=(EditText)findViewById(R.id.et_email);
        et_password=(EditText)findViewById(R.id.et_password);
        iv_login=(ImageView)findViewById(R.id.bt_login);
        iv_user=(ImageView)findViewById(R.id.circleImageView);
        iv_plus=(ImageView)findViewById(R.id.iv_add);
        scrollText=(TextView)findViewById(R.id.scroll_text);
        scrollText.setSelected(true);
        login=new Login();
        employeeDetails=new EmployeeDetails();
        lManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        prefManager = new PrefManager(this);
        if (prefManager.isFirstTimeLaunch() == true) {
            launchHomeScreen();
        }
        outPutFile = new File(Environment.getExternalStorageDirectory(), "profile.jpg");
        try{
            SharedPreferences gpref= getApplicationContext().getSharedPreferences("GmailImage", MODE_PRIVATE);
            String gphoto=gpref.getString("imagepath","photo");
            SharedPreferences pref = getApplicationContext().getSharedPreferences("Image", MODE_PRIVATE);
            String photo=pref.getString("imagepath","photo");
            assert photo != null;
            assert gphoto!=null;
            if(!photo.equals("photo"))
            {
                byte[] b = Base64.decode(photo, Base64.DEFAULT);
                InputStream is = new ByteArrayInputStream(b);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                iv_user.setImageBitmap(bitmap);
                iv_plus.setVisibility(View.GONE);
            }else if(!gphoto.equalsIgnoreCase("photo")){
                byte[] b = Base64.decode(gphoto, Base64.DEFAULT);
                InputStream is = new ByteArrayInputStream(b);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                iv_user.setImageBitmap(bitmap);
                iv_plus.setVisibility(View.GONE);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        SharedPreferences pref=getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        userName=pref.getString("username","uname");
        password=pref.getString("password","pwd");
        if(!userName.equalsIgnoreCase("uname") && !password.equalsIgnoreCase("pwd")){
            et_email.setText(userName);
            et_password.setText(password);
        }else{
            et_email.setText("");
            et_password.setText("");
        }

        //Add image to imageView
        iv_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"Capture Photo", "Choose from Gallery", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Add profile photo");
                builder.setCancelable(false);
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Capture Photo")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            //File f = new File(android.os.Environment.getExternalStorageDirectory(), "profile1.jpg");
                            imageCaptureUri = Uri.fromFile(outPutFile);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageCaptureUri);
                            startActivityForResult(intent, CAMERA_CODE);
                        } else if (items[item].equals("Choose from Gallery")) {
                            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(i, GALLERY_CODE);
                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                            iv_plus.setVisibility(View.VISIBLE);
                        }
                    }
                });
                builder.show();
            }
        });

        iv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    if(et_email.getText().toString().equalsIgnoreCase("")){
                        Toast.makeText(getApplicationContext(),"Enter email address",Toast.LENGTH_SHORT).show();
                    }else if(!isValidEmail(et_email.getText().toString())){
                        Toast.makeText(getApplicationContext(),"Invalid email address",Toast.LENGTH_SHORT).show();
                    }else if(et_password.getText().toString().equalsIgnoreCase("")){
                        Toast.makeText(getApplicationContext(),"Enter password",Toast.LENGTH_SHORT).show();
                    }else{
                         if(isDateAutomatic(LoginActivity.this) && isTimeAutomatic(LoginActivity.this)){
                             try{
                                 login.setUsername(et_email.getText().toString());
                                 login.setPassword(et_password.getText().toString());
                                 if(!userName.equals("uname") && !password.equals("pwd")){
                                     if(userName.equalsIgnoreCase(et_email.getText().toString()) && password.equalsIgnoreCase(et_password.getText().toString())){
                                         if(CheckNetworkConnection.isConnectionAvailable(LoginActivity.this)) {
                                             try{
                                                 new LoginTask().execute();
                                             }catch(Exception e){
                                                 e.printStackTrace();
                                             }
                                         }else{
                                             new CheckInternetGps(LoginActivity.this).callInternetAlert();
                                         }
                                     }else{
                                         Toast.makeText(getApplicationContext(),"You are not Authorised",Toast.LENGTH_SHORT).show();
                                     }
                                 }else{
                                     if(CheckNetworkConnection.isConnectionAvailable(LoginActivity.this)) {
                                         try{
                                             new LoginTask().execute();
                                         }catch(Exception e){
                                             e.printStackTrace();
                                         }
                                     }else{
                                         new CheckInternetGps(LoginActivity.this).callInternetAlert();
                                     }
                                 }
                             }catch (Exception e){
                                 e.printStackTrace();
                             }
                         }else{
                             Toast.makeText(getApplicationContext(),"Enable Automatice date & time and time Zone",Toast.LENGTH_LONG).show();
                             startActivityForResult(new Intent(Settings.ACTION_DATE_SETTINGS), 0);
                         }
                    }
                }else{
                    new CheckInternetGps(LoginActivity.this).checkDialogForGps();
                }
            }
        });
    }
    //validation for email
    public final static boolean isValidEmail(CharSequence target){
        if(target==null){
            return false;
        }else{
            return Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
    //async task for login
    class LoginTask extends AsyncTask<Void,Void,Response>{

        ProgressDialog dialog;
        boolean successful;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{
                dialog = ProgressDialog.show(LoginActivity.this, "Authenticating...", null, true, true);
                dialog.setCancelable(false);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        protected Response doInBackground(Void... params) {
                Response response=null;
                try{
                    final String URL=getString(R.string.login);
                    RestTemplate restTemplate = new RestTemplate();
                    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    response=restTemplate.postForObject(URL,login,Response.class);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return response;
        }
        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            dialog.dismiss();
            JSONObject jsonObject,sub_object,emp_object;
            if(response!=null){
                try{
                    jsonObject= new JSONObject(response.toString());
                    successful = jsonObject.getBoolean("successful");
                    if(successful){
                        SharedPreferences pref=getSharedPreferences("user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("username",et_email.getText().toString());
                        editor.putString("password",et_password.getText().toString());
                        editor.commit();
                        sub_object=jsonObject.getJSONObject("responseObject");
                        emp_object=sub_object.getJSONObject("empData");
                        String name=isNull(emp_object.getString("name"));
                        String email=isNull(emp_object.getString("email"));
                        String mobile=isNull(emp_object.getString("mobile"));
                        String designation=isNull(emp_object.getString("designation"));
                        String traineeid=isNull(emp_object.getString("traineeid"));
                        String placeofwork=isNull(emp_object.getString("placeofwork"));
                        String assigned_to=isNull(emp_object.getString("assigned_to"));
                        String dob=isNull(emp_object.getString("dob"));
                        String aadharid=isNull(emp_object.getString("aadharid"));
                        employeeDetails.setName(name);
                        employeeDetails.setEmail(email);
                        employeeDetails.setMobile(mobile);
                        employeeDetails.setDesignation(designation);
                        employeeDetails.setTraineeid(traineeid);
                        employeeDetails.setPlaceofwork(placeofwork);
                        employeeDetails.setAssigned_to(assigned_to);
                        employeeDetails.setDob(dob);
                        employeeDetails.setAadharId(aadharid);
                        callGmailPic(email);
                    }else{
                        Toast.makeText(getApplicationContext(),"Invalid credentials",Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(getApplicationContext(), "Sorry! Please try again", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
        alertDialog.setMessage("Do you want to close the application?");
        alertDialog.setCancelable(false);
        alertDialog.setIcon(R.drawable.red_geofence);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();
                    moveTaskToBack(true);
                }else{
                    finish();
                    moveTaskToBack(true);
                }
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) {
            imageCaptureUri = data.getData();
            CropingIMG();
        } else if (requestCode == CAMERA_CODE && resultCode == Activity.RESULT_OK) {
            CropingIMG();
        } else if (requestCode == CROPING_CODE) {
            try {
                if (outPutFile.exists()) {
                    Bitmap photo = decodeFile(outPutFile);
                    iv_user.setImageBitmap(photo);
                    iv_plus.setVisibility(View.GONE);
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("Image", MODE_PRIVATE);
                    SharedPreferences.Editor edit=pref.edit();
                    edit.putString("imagepath",encodeTobase64(photo));
                    edit.commit();
                }else {
                    Toast.makeText(getApplicationContext(), "Error while save image", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(requestCode==requestCode){
            if(resultCode==RESULT_OK){
                int result=data.getIntExtra("result",0);
                if(result==1){
                    android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(LoginActivity.this).create();
                    alertDialog.setTitle("Session Timeout");
                    alertDialog.setMessage("Please Login again");
                    alertDialog.setCancelable(false);
                    alertDialog.setIcon(R.mipmap.ic_alert);
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
            }
        }
    }
    public void CropingIMG() {
        try {
            final ArrayList<CropingOption> cropOptions = new ArrayList<CropingOption>();
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setType("image/*");
            List<ResolveInfo> list = getPackageManager().queryIntentActivities( intent, 0 );
            int size = list.size();
            if(size == 0) {
                Toast.makeText(this, "Cann't find image for croping", Toast.LENGTH_SHORT).show();
                return;
            }else
            {
                intent.setData(imageCaptureUri);
                intent.putExtra("outputX", 512);
                intent.putExtra("outputY", 512);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                //Create output file here
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outPutFile));
                if (size == 1) {
                    Intent i   = new Intent(intent);
                    ResolveInfo res = (ResolveInfo) list.get(0);
                    i.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                    startActivityForResult(i, CROPING_CODE);
                }else{
                    for (ResolveInfo res : list) {
                        final CropingOption co = new CropingOption();
                        co.title  = getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                        co.icon  = getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                        co.appIntent= new Intent(intent);
                        co.appIntent.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                        cropOptions.add(co);
                    }
                    CropingOptionAdapter adapter = new CropingOptionAdapter(getApplicationContext(), cropOptions);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Choose Croping Widget");
                    builder.setCancelable(false);
                    builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                        public void onClick( DialogInterface dialog, int item ) {
                            startActivityForResult( cropOptions.get(item).appIntent, CROPING_CODE);
                        }
                    });
                    builder.setOnCancelListener( new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel( DialogInterface dialog ) {
                            if (imageCaptureUri != null ) {
                                getContentResolver().delete(imageCaptureUri, null, null );
                                imageCaptureUri = null;
                            }
                        }
                    } );
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public Bitmap decodeFile(File f) {
        try
        {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 512;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public static String encodeTobase64(Bitmap image) {
        String imageEncoded=null;
        try
        {
            Bitmap bitmap_image = image;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap_image.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return imageEncoded;
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQ_CODE);
            return;
        }else if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQ_CODE);
            return;
        }else if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQ_CODE);
            return;
        }
    }
    public void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        for(int i=0;i<=3;i++){
            Toast.makeText(getApplicationContext(),"Allow all Permissions otherwise app doesn't work",Toast.LENGTH_SHORT).show();
        }
    }
    public void callGmailPic(String email){
        try{
            new GmailTask(email).execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public class GmailTask extends AsyncTask<Void,Void,Response>{

        String mail,msg;
        ProgressDialog dialog;

        public GmailTask(String mail){
            this.mail=mail;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{
                dialog = ProgressDialog.show(LoginActivity.this, "Loading...", null, true, true);
                dialog.setCancelable(false);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        protected Response doInBackground(Void... params) {
            try{
                DefaultHttpClient client=new DefaultHttpClient();
                HttpGet get=new HttpGet("http://picasaweb.google.com/data/entry/api/user/"+mail+"?alt=json");
                HttpResponse response=client.execute(get);
                InputStream isr=response.getEntity().getContent();
                int i=isr.read();
                msg="";
                while(i!=-1){
                    msg=msg+(char)i;
                    i=isr.read();
                }
            }catch (Exception e) {
            }
            return null;
        }
        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            JSONObject jsonObject,subObject,object;
            dialog.dismiss();
            if(msg.length()>0){
                try{
                    jsonObject=new JSONObject(msg);
                    subObject=jsonObject.getJSONObject("entry");
                    object=subObject.getJSONObject("gphoto$thumbnail");
                    imageUrl=object.getString("$t");
                    if(imageUrl.length()>0){
                        new ImageViewTask(imageUrl).execute();
                    }else{
                        Toast.makeText(getApplicationContext(),"Login successfull",Toast.LENGTH_LONG).show();
                        Intent i=new Intent();
                        i.setComponent(new ComponentName(getApplicationContext(),GeofenceLocationActivity.class));
                        i.putExtra("emp_object",employeeDetails);
                        startActivityForResult(i, requestCode);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(getApplicationContext(),"Sorry! Please try again",Toast.LENGTH_LONG).show();
            }
        }
    }
    class ImageViewTask extends AsyncTask<Void,Void,Void>{
        String url;
        ProgressDialog dialog;

        public ImageViewTask(String url){
            this.url=url;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{
                dialog = ProgressDialog.show(LoginActivity.this, "Loading...", null, true, true);
                dialog.setCancelable(false);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        protected Void doInBackground(Void... params) {
            try{
                URL u=new URL(url);
                InputStream  isr=u.openStream();
                Bitmap bmp=BitmapFactory.decodeStream(isr);
                SharedPreferences pref = getApplicationContext().getSharedPreferences("GmailImage", MODE_PRIVATE);
                SharedPreferences.Editor edit=pref.edit();
                edit.putString("imagepath",encodeTobase64(bmp));
                edit.commit();
            }catch (Exception e) {e.printStackTrace();}
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try{
                dialog.dismiss();
                Toast.makeText(getApplicationContext(),"Login successfull",Toast.LENGTH_LONG).show();
                Intent i=new Intent();
                i.setComponent(new ComponentName(getApplicationContext(),GeofenceLocationActivity.class));
                i.putExtra("emp_object",employeeDetails);
                startActivityForResult(i, requestCode);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    //Automatic date & time
    public static boolean isDateAutomatic(Context c){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1){
            return Settings.Global.getInt(c.getContentResolver(),Settings.Global.AUTO_TIME,0)==1;
        }else{
            return Settings.System.getInt(c.getContentResolver(),Settings.System.AUTO_TIME,0)==1;
        }
    }
    //Automatic time zone
    public static boolean isTimeAutomatic(Context c){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1){
            return Settings.Global.getInt(c.getContentResolver(),Settings.Global.AUTO_TIME_ZONE,0)==1;
        }else{
            return Settings.System.getInt(c.getContentResolver(),Settings.System.AUTO_TIME_ZONE,0)==1;
        }
    }
    public String isNull(String msg){
        if(msg != null && !msg.isEmpty()){
            return msg;
        }else{
            return "-";
        }
    }
}
