package com.denny.pickerlib.adapter;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Cai on 2016/10/11.
 */

public class PhotoAdapter extends BaseListAdpater<String> {

    private int mMaxSize = 400;


    public PhotoAdapter(List<String> data) {
        super(data);
    }

    public void onBindViewHolder(final PhotoHolder holder, String imageFileUri,int position) {
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
    protected ViewHolder onNewView(ViewGroup parent, int position) {
        return new PhotoHolder(parent);
    }

}
