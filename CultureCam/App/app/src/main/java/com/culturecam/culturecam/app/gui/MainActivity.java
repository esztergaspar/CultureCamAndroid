package com.culturecam.culturecam.app.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.culturecam.culturecam.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Button b_takepic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){
        Log.v(TAG, "onClick called");

        Intent intent = new Intent(this, activity_chooseimage.class);
        startActivity(intent);
    }
}
