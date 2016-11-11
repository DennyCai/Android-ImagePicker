package com.denny.pickerlib.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.denny.pickerlib.model.Album;

import java.util.List;

/**
 * Created by Cai on 2016/10/17.
 */
public class AlbumAdapter extends BaseListAdpater<Album> {

    public AlbumAdapter(List<Album> data) {
        super(data);
    }

    @Override
    protected void onBindView(ViewHolder holder, Album source, int position) {
        AlbumHolder albumHolder = (AlbumHolder) holder;
        
    }

    @Override
    protected ViewHolder onNewView(ViewGroup parent) {
        return new AlbumHolder(parent);
    }
}
