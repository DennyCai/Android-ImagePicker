package com.denny.pickerlib.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.denny.pickerlib.R;

/**
 * Created by Cai on 2016/10/11.
 */

public class PhotoHolder extends ViewHolder {

    ImageView imageView;
    CheckBox box;

    public PhotoHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo,parent,false));
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
        box = (CheckBox) itemView.findViewById(R.id.checkBox);
    }

    public void toggle(){
        box.toggle();
        if(box.isChecked()){
            imageView.setColorFilter(Color.parseColor("#55696969"));
        }else{
            imageView.setColorFilter(null);
        }
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
