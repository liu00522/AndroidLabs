package com.cst2335.liu00522;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ProfileActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView mImageButton;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";      // should it be here?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mImageButton = findViewById(R.id.imgBtn);
        EditText emailBoxP = findViewById(R.id.emailBoxP);

        mImageButton.setOnClickListener(click -> dispatchTakePictureIntent());
        Intent fromMain = getIntent();
        String email = fromMain.getStringExtra("EMAIL");
        emailBoxP.setText(email);

        Log.e(ACTIVITY_NAME, "In function: onCreate()");

        Button chatButton = findViewById(R.id.gotoChat);
        Intent gotoChat = new Intent(ProfileActivity.this, ChatRoomActivity.class);
        chatButton.setOnClickListener(click -> {
            startActivity(gotoChat);
        });

        Button weatherBtn = findViewById(R.id.gotoWeather);
        Intent gotoWeather = new Intent(ProfileActivity.this, WeatherForecast.class);
        weatherBtn.setOnClickListener(click -> {
            startActivity(gotoWeather);
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(ACTIVITY_NAME, "In function: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ACTIVITY_NAME, "In function: onResume()");
    }

    protected void onPause(String message) {
        super.onPause();
        Log.e(ACTIVITY_NAME, "In function: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(ACTIVITY_NAME, "In function: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(ACTIVITY_NAME, "In function: onDestroy()");
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
        Log.e(ACTIVITY_NAME, "In function: onActivityResult()");
    }


}
