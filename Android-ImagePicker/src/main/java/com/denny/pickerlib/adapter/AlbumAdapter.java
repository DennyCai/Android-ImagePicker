package com.denny.pickerlib.adapter;

import android.view.View;
import android.view.ViewGroup;

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
    protected void onBindView(ViewHolder holder, final Album album, final int position) {
        final AlbumHolder albumHolder = (AlbumHolder) holder;
        albumHolder.albumName.setText(album.getAlbumName());
        if(position>0) {
            albumHolder.picNumber.setText(album.getPicturesNumber() + "张");
        } else {
            albumHolder.picNumber.setText("");
        }
        albumHolder.albumImage.setImageBitmap(album.getFirstPictureBmp());
        albumHolder.setChecked(isChecked(position));

    }

    @Override
    protected ViewHolder onNewView(ViewGroup parent, int position) {
        final AlbumHolder albumHolder = new AlbumHolder(parent);
        return new AlbumHolder(parent);
    }
}
