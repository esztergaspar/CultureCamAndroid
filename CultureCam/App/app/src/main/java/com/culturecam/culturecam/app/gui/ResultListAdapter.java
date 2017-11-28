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

import java.util.List;

public class ResultListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<ImageDTO> imageDTOS;

    public ResultListAdapter(Context context, List<ImageDTO> imageDTOS) {
        this.context = context;
        this.imageDTOS = imageDTOS;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return imageDTOS.size();
    }

    public Object getItem(int position) {
        return imageDTOS.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            View rowView= inflater.inflate(R.layout.list_element, null, true);
            imageView = (ImageView) rowView.findViewById(R.id.imageViewElement);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageBitmap(imageDTOS.get(position).getImage());
        return imageView;
    }
}
