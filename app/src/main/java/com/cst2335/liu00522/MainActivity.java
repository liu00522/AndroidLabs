package com.cst2335.liu00522;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  click button to show toast message
        Button btn = findViewById(R.id.button);

        btn.setOnClickListener(v -> Toast.makeText(MainActivity.this,
                // why not R.string.toast_msg?
                R.string.toast_message,
                Toast.LENGTH_LONG).show());

        //  switch on to show Snackbar message
        Switch mySwitch = findViewById(R.id.mySwitch);

        mySwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Snackbar.make(btn, isChecked ? R.string.isOn : R.string.isOff,
                    Snackbar.LENGTH_LONG)
                    .setAction("Undo", click -> buttonView.setChecked(!isChecked))
                    .show();
        });

    }


}
