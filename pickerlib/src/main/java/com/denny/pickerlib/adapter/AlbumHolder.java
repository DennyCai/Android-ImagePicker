package com.denny.pickerlib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.denny.pickerlib.R;

/**
 * Created by Cai on 2016/10/17.
 */
public class AlbumHolder extends RecyclerView.ViewHolder {

    ImageView albumImage;
    TextView albumName;
    TextView picNumber;
    RadioButton albumRadio;

    public AlbumHolder(View parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, (ViewGroup) parent, false));
        albumImage = (ImageView) itemView.findViewById(R.id.album_iv);
        albumName = (TextView) itemView.findViewById(R.id.album_name);
        albumRadio = (RadioButton) itemView.findViewById(R.id.album_rb);
        picNumber = (TextView) itemView.findViewById(R.id.picNum);
    }

    public void setChecked(boolean checked) {
        albumRadio.setChecked(checked);
    }
}
