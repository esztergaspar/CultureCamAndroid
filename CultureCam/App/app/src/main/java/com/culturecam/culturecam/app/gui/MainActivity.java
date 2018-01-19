package com.culturecam.culturecam.app.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.culturecam.culturecam.R;
import com.culturecam.culturecam.entities.SearchEngines;
import com.culturecam.culturecam.imageSearchSystem.ImageSearchService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private boolean viewOpen;

    @BindView(R.id.menuOverlay)
    public ConstraintLayout menuOverlay;

    @BindView(R.id.engineSwitch)
    public Switch engineSwitch;

    @BindView(R.id.b_openMenu)
    public ImageButton b_openMenu;

    @BindView(R.id.b_about)
    public Button b_about;

    @BindView(R.id.b_content)
    public Button b_content;

    @BindView(R.id.b_feedback)
    public Button b_feedback;

    @BindView(R.id.text_about)
    public TextView text_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ImageSearchService.getInstance().setCurrentSearchEngine(SearchEngines.IR_SEARCH_ENGINE);
        b_openMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuOverlay.setVisibility((menuOverlay.getVisibility() == View.GONE ? View.VISIBLE : View.GONE));
                viewOpen = false;
                enableView(false);
            }
        });
        engineSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ImageSearchService.getInstance().setCurrentSearchEngine(b ? SearchEngines.LIRE_SOLR_ENGINE : SearchEngines.IR_SEARCH_ENGINE);
            }
        });
        b_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    enableView(!viewOpen);
                    text_about.setText(Html.fromHtml(getResources().getString(R.string.about)));
                    viewOpen = !viewOpen;
            }
        });
        b_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableView(!viewOpen);
                text_about.setText(Html.fromHtml(getResources().getString(R.string.content)));
                viewOpen = !viewOpen;
            }
        });
        b_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableView(!viewOpen);
                text_about.setText(Html.fromHtml(getResources().getString(R.string.feedback)));
                viewOpen = !viewOpen;
            }
        });
    }

    public void onClick(View view){
        Log.v(TAG, "onClick called");

        Intent intent = new Intent(this, activity_chooseimage.class);
        startActivity(intent);
    }

    private void enableView(boolean enable) {
        if (!enable) {
            text_about.setVisibility(View.GONE);
            b_about.setVisibility(View.VISIBLE);
            b_content.setVisibility(View.VISIBLE);
            b_feedback.setVisibility(View.VISIBLE);
        } else {
            text_about.setVisibility(View.VISIBLE);
            b_about.setVisibility(View.GONE);
            b_content.setVisibility(View.GONE);
            b_feedback.setVisibility(View.GONE);
        }
    }
}
