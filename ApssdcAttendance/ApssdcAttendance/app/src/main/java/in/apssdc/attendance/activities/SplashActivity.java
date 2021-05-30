package in.apssdc.attendance.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import in.apssdc.attendance.R;
import in.apssdc.attendance.common.CheckInternetGps;
import in.apssdc.attendance.common.CheckNetworkConnection;
import in.apssdc.attendance.model.Response;

public class SplashActivity extends AppCompatActivity {

	// Splash screen timer
	private static int SPLASH_TIME_OUT = 1000;
	String versionCode,versionName;
	ProgressBar progressBar;
	int versionNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splash);
		progressBar =(ProgressBar)findViewById(R.id.progressBar);
		progressBar.setVisibility(View.VISIBLE);
		try {
			PackageInfo pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			versionNumber = pinfo.versionCode;
			versionName = pinfo.versionName;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				if (CheckNetworkConnection.isConnectionAvailable(SplashActivity.this)){
					GetVersionCodeTask task=new GetVersionCodeTask();
					task.execute();
				}else
				{
					new CheckInternetGps(SplashActivity.this).callInternetAlert();
				}
			}
		}, SPLASH_TIME_OUT);
	}
	class GetVersionCodeTask extends AsyncTask<Void,Void,Response>
	{
		boolean successful;

		@Override
		protected Response doInBackground(Void... params) {
			Response response=null;
			try{
				final String URL =getString(R.string.app_version);
				RestTemplate restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
				response = restTemplate.getForObject(URL,Response.class);
			}catch (Exception e){
				e.printStackTrace();
			}
			return response;
		}
		@Override
		protected void onPostExecute(Response response) {
			super.onPostExecute(response);
			JSONObject jsonObject;
			progressBar.setVisibility(View.GONE);
			if(response!=null){
				try
				{
					jsonObject = new JSONObject(response.toString());
					successful = jsonObject.getBoolean("successful");
					if(successful){
						versionCode=jsonObject.getString("responseObject");
						if(versionCode.equalsIgnoreCase(versionName)){
							Intent i = new Intent(SplashActivity.this,LoginActivity.class);
							startActivity(i);
						}else{
							Toast.makeText(getApplicationContext(),"Need Update",Toast.LENGTH_SHORT).show();
							final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
							try {
								startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
							}catch (android.content.ActivityNotFoundException anfe) {
								startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
							}
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				Toast.makeText(getApplicationContext(),"Sorry! Please try again",Toast.LENGTH_LONG).show();
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					finishAffinity();
					moveTaskToBack(true);
				}else{
					finish();
					moveTaskToBack(true);
				}
			}
		}
	}
}
