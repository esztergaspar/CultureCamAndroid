package com.culturecam.culturecam.app.gui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.culturecam.culturecam.R;
import com.culturecam.culturecam.app.system.ImageSearchService;
import com.culturecam.culturecam.entities.ImageDTO;
import com.culturecam.culturecam.entities.SearchResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.culturecam.culturecam.app.system.ImageSearchService.EXTRA_RESULT_LIST;

public class ResultViewActivity extends AppCompatActivity {
    private SearchResult searchResult;

    private int start;

    @BindView(R.id.listView)
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        searchResult = (SearchResult) intent.getSerializableExtra(LoadViewActivity.RESULT);

        ResultListAdapter adapter = new
                ResultListAdapter(this, searchResult.getItems());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


            }
        });
    }
}
