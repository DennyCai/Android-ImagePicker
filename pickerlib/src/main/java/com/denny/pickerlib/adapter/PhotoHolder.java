package com.denny.pickerlib.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.denny.pickerlib.R;

/**
 * Created by Cai on 2016/10/11.
 */

public class PhotoHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    CheckBox box;

    public PhotoHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo,parent,false));
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
        box = (CheckBox) itemView.findViewById(R.id.checkBox);
    }

    public void setChecked(boolean checked){
        box.setChecked(checked);
        if(checked){
            imageView.setColorFilter(Color.parseColor("#55696969"));
        }else{
            imageView.setColorFilter(null);
        }
    }
}
