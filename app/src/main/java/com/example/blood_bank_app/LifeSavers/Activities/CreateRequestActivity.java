package com.example.blood_bank_app.LifeSavers.Activities;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.utils.Oscillator;
import androidx.core.content.PermissionChecker;
import androidx.preference.PreferenceManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.bumptech.glide.Glide;
import com.example.blood_bank_app.LifeSavers.Utils.EndPoints;
import com.example.blood_bank_app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URISyntaxException;

public class CreateRequestActivity extends AppCompatActivity {

    EditText nameText, cityText, numberText, messageText;
    ImageButton postImage;
    Button submit_button;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);
        com.androidnetworking.AndroidNetworking.initialize(getApplicationContext());

        String[] BLOODGRPS = new String[]{"A+", "B+", "O+", "AB+", "A-", "B-", "O-", "AB-"};
        AutoCompleteTextView bloodgrpTv2 = findViewById(R.id.bloodgrp_req);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, BLOODGRPS);
        bloodgrpTv2.setAdapter(adapter);

        nameText = findViewById(R.id.name_req);
        cityText = findViewById(R.id.city_req);
        numberText = findViewById(R.id.phoneno_req);
        messageText = findViewById(R.id.message_req);
        postImage = findViewById(R.id.imageButton_req);
        submit_button = findViewById(R.id.create_request_button);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, city, bloodgrp, number, message;
                name = nameText.getText().toString();
                city = cityText.getText().toString();
                number = numberText.getText().toString();
                bloodgrp = bloodgrpTv2.getText().toString();
                message = messageText.getText().toString();

                if(isValid()){
                    //code to upload this post
                    uploadRequest(name, city, bloodgrp, number, message);
                }

            }
        });

        postImage.setOnClickListener(view -> {
            //code to pick image
            permission();
        });
    }

    private void pickImage(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 101);
    }

    private void permission(){
        if(PermissionChecker.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
                !=PermissionChecker.PERMISSION_GRANTED){
            //asking for permission
            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 401);
        }
        else{
            //permission is already there
            pickImage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==401){
            if(grantResults[0]==PermissionChecker.PERMISSION_GRANTED){
                //permission was granted
            }
            else{
                //permission not granted
                showMessage("Permission Declined");
            }
        }
    }



    private void uploadRequest(String name, String city, String bloodgrp, String number, String message){
        //code to upload the message
        String path = "";
        try{
            path = getPath(imageUri);
        }catch (URISyntaxException e){
            showMessage("wrong uri");
        }
        //String number = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("number", "12345");
        AndroidNetworking.upload(EndPoints.upload_request)
                .addMultipartFile("file", new File(path))
                .addQueryParameter("name", name)
                .addQueryParameter("city", city)
                .addQueryParameter("bloodgrp", bloodgrp)
                .addQueryParameter("number", number)
                .addQueryParameter("message", message)
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {
                        postImage.setOnClickListener(null);
                    }
                })

                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getBoolean("success")){
                                showMessage("Successful");
                                CreateRequestActivity.this.finish();
                            }
                            else{
                                showMessage(response.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == RESULT_OK){
            if(data!=null){
                imageUri = data.getData();
                Glide.with(getApplicationContext()).load(imageUri).into(postImage);
            }
        }
    }

    private boolean isValid(){
        if(messageText.getText().toString().isEmpty()){
            showMessage("Message should not be empty");
            return false;
        }
        else if(imageUri==null){
            showMessage("Pick image");
            return false;
        }
        return true;
    }

    private void showMessage(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @SuppressLint("NewApi")
    private String getPath(Uri uri) throws URISyntaxException {
        final boolean needToCheckUri = Build.VERSION.SDK_INT >= 19;
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        // deal with different Uris.
        if (needToCheckUri && DocumentsContract.isDocumentUri(getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }


    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }


    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }


    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

}