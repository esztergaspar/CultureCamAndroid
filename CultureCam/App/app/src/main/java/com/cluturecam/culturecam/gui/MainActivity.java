package com.cluturecam.culturecam.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cluturecam.culturecam.R;

public class MainActivity extends AppCompatActivity {

    Button b_takepic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){
        System.out.println("Button funktioniert!!");
    }
}
