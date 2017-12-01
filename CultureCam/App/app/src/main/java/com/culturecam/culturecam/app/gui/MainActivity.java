package com.culturecam.culturecam.app.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.culturecam.culturecam.R;
import com.culturecam.culturecam.app.system.ImageSearchService;
import com.culturecam.culturecam.entities.SearchEngines;

import static com.culturecam.culturecam.app.gui.activity_chooseimage.REQUEST_IMAGE_CAPTURE;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Button b_takepic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageSearchService.getInstance().setCurrentSearchEngine(SearchEngines.IR_SEARCH_ENGINE);
    }

    public void onClick(View view){
        Log.v(TAG, "onClick called");

        Intent intent = new Intent(this, activity_chooseimage.class);
        startActivity(intent);
    }
}
