package com.denny.pickerlib.adapter;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Cai on 2016/10/11.
 */

public class PhotoAdapter extends BaseListAdpater<String> {

    private int mMaxSize = 400;


    public PhotoAdapter(List<String> data) {
        super(data);
    }


    public PhotoHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final PhotoHolder holder = new PhotoHolder(parent);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        holder.box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return holder;
    }



    public void onBindViewHolder(PhotoHolder holder, String imageFileUri, int position) {
        ImageLoader.loadImage(imageFileUri,
                holder.imageView, mMaxSize,
                android.R.drawable.sym_def_app_icon);
        holder.setChecked(isChecked(position));
    }

    @Override
    protected void onBindView(ViewHolder holder, String source, int position) {
        onBindViewHolder((PhotoHolder) holder,source,position);
    }

    @Override
    protected ViewHolder onNewView(ViewGroup parent) {
        return new PhotoHolder(parent);
    }

}
