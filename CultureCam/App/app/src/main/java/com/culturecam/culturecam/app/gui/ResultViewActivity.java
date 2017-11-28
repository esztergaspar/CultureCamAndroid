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

import java.util.ArrayList;
import java.util.List;

import static com.culturecam.culturecam.app.system.ImageSearchService.EXTRA_RESULT_LIST;

public class ResultViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //Intent intent = getIntent();
        List<ImageDTO> imageList = ImageSearchService.getInstance().getSearchResult();

        ResultListAdapter adapter = new
                ResultListAdapter(this, imageList);
        ListView list=(ListView)findViewById(R.id.listView);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


            }
        });

    }
}
