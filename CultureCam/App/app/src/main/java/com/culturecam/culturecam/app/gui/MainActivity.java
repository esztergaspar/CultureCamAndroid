package com.culturecam.culturecam.app.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.culturecam.culturecam.R;
import com.culturecam.culturecam.entities.SearchEngines;
import com.culturecam.culturecam.imageSearchSystem.ImageSearchService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.engineSwitch)
    public Switch engineSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ImageSearchService.getInstance().setCurrentSearchEngine(SearchEngines.IR_SEARCH_ENGINE);
        engineSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ImageSearchService.getInstance().setCurrentSearchEngine(b ? SearchEngines.LIRE_SOLR_ENGINE : SearchEngines.IR_SEARCH_ENGINE);
            }
        });
    }

    public void onClick(View view){
        Log.v(TAG, "onClick called");

        Intent intent = new Intent(this, activity_chooseimage.class);
        startActivity(intent);
    }

    public void onEngineSwitch() {

    }
}
