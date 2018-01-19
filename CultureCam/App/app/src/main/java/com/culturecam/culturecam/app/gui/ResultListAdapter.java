package com.culturecam.culturecam.app.gui;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.culturecam.culturecam.R;
import com.culturecam.culturecam.entities.SearchResultImage;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ResultListAdapter extends BaseAdapter {
    private static final String TAG = "ResultViewActivity";

    private Context context;
    private LayoutInflater inflater;
    private List<SearchResultImage> resultImageList;
    private View rowView;

    public ResultListAdapter(Context context, List<SearchResultImage> resultImageList) {
        this.context = context;
        this.resultImageList = resultImageList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return resultImageList.size();
    }

    public SearchResultImage getItem(int position) {
        return resultImageList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        rowView = convertView;
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.list_element, null, true);
        }
        // make shure that details are not visible on recycling views
        View details = rowView.findViewById(R.id.l_detailview);
        details.setVisibility(View.GONE);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageViewElement);
        final String url = getItem(position).getUrl();
        Picasso.with(context).setLoggingEnabled(true);
        Picasso.with(context).load(url)
                //.placeholder(R.drawable.full_size_render)
                .resize(parent.getWidth(), parent.getHeight())
                .centerInside()
                .into(imageView);

        FloatingActionButton linkButton = (FloatingActionButton) rowView.findViewById(R.id.b_shareLink);
        linkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                // i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
                /*i.putExtra(Intent.EXTRA_TEXT,
                        "http://www.culturecam.eu"
                                + "/?shared=" + imageID.split("\\.")[0]);*/
                i.putExtra(Intent.EXTRA_TEXT,url);
                context.startActivity(Intent.createChooser(i, "Share Search"));
            }
        });
        return rowView;
    }

}
