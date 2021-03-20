package com.cst2335.liu00522;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    SharedPreferences pref = null;
    EditText emailBox;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailBox = findViewById(R.id.emailBox);
        pref = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        String savedEmailAdd = pref.getString("emailAdd", "");
        emailBox.setText(savedEmailAdd);

         Button btn = findViewById(R.id.button);
        Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);

        btn.setOnClickListener(click -> {
            goToProfile.putExtra("EMAIL", emailBox.getText().toString());
            startActivity(goToProfile);
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("emailAdd", emailBox.getText().toString());
        edit.commit();

    }


}