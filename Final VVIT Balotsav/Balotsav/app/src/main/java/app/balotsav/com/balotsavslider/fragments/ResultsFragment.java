package app.balotsav.com.balotsavslider.fragments;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import app.balotsav.com.balotsavslider.R;
import app.balotsav.com.balotsavslider.activities.DashBoardActivity;
import app.balotsav.com.balotsavslider.utils.CheckNetwork;
import app.balotsav.com.balotsavslider.utils.GrantPermissions;

import static android.view.View.GONE;

public class ResultsFragment extends Fragment {

    private CountDownTimer countDownTimer;
    private DatabaseReference mDatabaseReference;
    PDFView resultView;
    private ProgressDialog pDialog;
    private TextView empty;
    private boolean flag = true;
    private View rootview;
    ViewGroup container;
    private LayoutInflater infla;
    private CountDownTimer CDT;
    private int i = 0;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ActionBar actionBar = ((DashBoardActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.results));
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        this.container = container;
        infla = inflater;
        actionBar.setHomeButtonEnabled(true);
        rootview = inflater.inflate(R.layout.fragment_results, container, false);

        empty = rootview.findViewById(R.id.id_empty_results);
        resultView = rootview.findViewById(R.id.id_result_pdf);
        // Inflate the layout for this fragment
        if (new CheckNetwork(getContext()).isNetworkAvailable()) {
            StorageReference mStorageReference = FirebaseStorage.getInstance().getReference();
            mDatabaseReference = FirebaseDatabase.getInstance().getReference("result");
            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String upload = dataSnapshot.getValue(String.class);
                    Log.i("URL", upload);
                    assert upload != null;
                    if (upload.equals("null") ) {
                        Log.i("Test","Empty");
                        resultView.setVisibility(GONE);
                    }
                    else {
                        empty.setVisibility(GONE);
                        Log.i("Test","Not empty");
                        new DownloadFileFromURL().execute(upload);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else {

            rootview = inflater.inflate(R.layout.fragment_results, container, false);
            Toast.makeText(getContext(), R.string.check_connection, Toast.LENGTH_SHORT).show();
            if (new GrantPermissions(getActivity()).checkAndRequestPermissions()) {
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Balotsav_results.pdf");
                if (file !=null && file.exists())
                    resultView.fromFile(file).load();
                else {
                }
            }
        }
        return rootview;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(false);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                if (pDialog == null && !pDialog.isShowing()) {
                    pDialog = new ProgressDialog(getContext());
                    pDialog.setTitle(getString(R.string.results));
                    pDialog.show();
                    pDialog.setMessage("డౌన్ లోడ్ అవుచున్నది.....");
                    pDialog.setCancelable(false);
                } else {
                    pDialog.dismiss();
                }
            } catch (Exception e) {
                Log.i("Error", e.getMessage());
            }

        }


        /**
         * Downloading file in background thread
         */

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();

                // this will be useful so that you can show a tipical 0-100%
                // progress bar
                int lenghtOfFile = conection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);

                // Output stream
                OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/Balotsav_results.pdf");

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // writing data to file
                    output.write(data, 0, count);

                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {

                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Balotsav_results.pdf");
            if (file.exists() && file.length() != 0)
                resultView.fromFile(file).load();
            else {

            }
            if (pDialog != null)
                pDialog.dismiss();

        }
    }
}
