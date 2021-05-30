package app.balotsav.com.balotsavslider.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import app.balotsav.com.balotsavslider.R;

public class AccomodationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accomodation);
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLScBrHCuok1WkEl_UsrBrhjFQV3Wl7j2PAaslOMZfvHVOx2Bog/viewform");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}
