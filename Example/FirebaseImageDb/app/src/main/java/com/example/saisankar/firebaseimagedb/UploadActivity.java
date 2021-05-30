package com.example.saisankar.firebaseimagedb;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class UploadActivity extends AppCompatActivity {

    ProgressBar progressBar;
    EditText image_name, image_desc;
    Button chooseImage, uploaddata;
    ImageView displayImage;
    Uri uri;

    StorageReference storageReference;
    DatabaseReference databaseReference;
    StorageTask storageTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        progressBar = findViewById(R.id.progress_bar);
        image_name = findViewById(R.id.imagename);
        image_desc = findViewById(R.id.imageDescription);
        chooseImage = findViewById(R.id.button_choose_image);
        displayImage = findViewById(R.id.chosenImageView);
        uploaddata = findViewById(R.id.uploadBtn);

        storageReference = FirebaseStorage.getInstance().getReference("images_upload");
        databaseReference = FirebaseDatabase.getInstance().getReference("images_upload");


        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, 1);
            }
        });


        uploaddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dataUpload();
            }
        });
    }

    public void dataUpload() {
        if (uri != null) {

            final StorageReference datareference = storageReference
                    .child(System.currentTimeMillis() + "." + getFileExtension(uri));

            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(true);

            storageTask = datareference.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.VISIBLE);
                                    progressBar.setIndeterminate(false);
                                    progressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(UploadActivity.this, "Image Upload Sucessfully", Toast.LENGTH_SHORT).show();
                            /*storageReference.getDownloadUrl().toString(),*/
                            MyModel model = new MyModel(image_name.getText().toString().trim(),

                                    taskSnapshot.getMetadata().getReference().getDownloadUrl().toString(),
                                    image_desc.getText().toString());

                            String uploadId = databaseReference.push().getKey();
                            databaseReference.child(uploadId).setValue(model);

                            progressBar.setVisibility(View.INVISIBLE);

                            startActivity(new Intent(UploadActivity.this, MainActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(UploadActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.i("imageupload", e.getMessage());
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "You haven't Selected Any file selected", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            uri = data.getData();
            Picasso.with(this)
                    .load(uri)
                    .placeholder(R.mipmap.ic_launcher)
                    .centerCrop()
                    .fit()
                    .into(displayImage);
        }

    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


}
