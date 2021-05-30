package app.balotsav.com.balotsavslider.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.gson.Gson;

import java.io.File;

import app.balotsav.com.balotsavslider.R;
import app.balotsav.com.balotsavslider.model.Schools;

public class PdfViewActivity extends AppCompatActivity {

    FloatingActionButton email;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdfview);

        PDFView pdfView = findViewById(R.id.pdfView);
        String path1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Balotsav.pdf";
        Log.i("path", path1);
        final File file = new File(path1);
        pdfView.fromFile(file).load();

        Gson gson = new Gson();
        String json = getSharedPreferences("Balotsav", MODE_PRIVATE).getString("data", " ");
        final Schools s = gson.fromJson(json, Schools.class);

        email = findViewById(R.id.id_email_fab);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PdfViewActivity.this, "Emailing....", Toast.LENGTH_SHORT).show();
                try {
                    File Root = Environment.getExternalStorageDirectory();
                    String filelocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Balotsav.pdf";
                    File file1 = new File(filelocation);
                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{s.getHeadMaster_Email(), "sriharsha1103@gmail.com"});
                    //System.out.println(" email:"+s.getHeadMaster_Email());
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Registration for Balotsav");
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file1));
                    intent.putExtra(Intent.EXTRA_TEXT, "Congratulations on Registration for Balotsav");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(intent);
                } catch (Exception e) {
                    System.out.println("is exception raises during sending mail" + e);
                }
            }
        });

    }
}