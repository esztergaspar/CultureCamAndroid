package com.culturecam.culturecam.app.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.culturecam.culturecam.R;
import com.culturecam.culturecam.entities.ImageDTO;
import com.culturecam.culturecam.entities.ResultImage;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ResultListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<ResultImage> resultImageList;

    public ResultListAdapter(Context context, List<ResultImage> resultImageList) {
        this.context = context;
        this.resultImageList = resultImageList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return resultImageList.size();
    }

    public Object getItem(int position) {
        return resultImageList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }



    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = (ImageView) convertView;
        if (imageView == null) {
            View rowView= inflater.inflate(R.layout.list_element, null, true);
            imageView = (ImageView) rowView.findViewById(R.id.imageViewElement);
        } 
        String url = ((ResultImage) getItem(position)).getCachedThmbUrl();
        Picasso.with(context).setLoggingEnabled(true);
        Picasso.with(context).load(url).placeholder(R.drawable.full_size_render).fit().into(imageView);

        return imageView;
    }
}
