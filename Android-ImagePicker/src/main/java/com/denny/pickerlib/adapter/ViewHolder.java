package com.denny.pickerlib.adapter;

import android.view.View;

/**
 * Created by Cai on 2016/11/11.
 */
public class ViewHolder {

    protected View itemView;
    private int mPos;

    public ViewHolder(View item){
        itemView = item;
    }

    public void setPosition(int pos){
        mPos = pos;
    }

    public int getPosition() {
        return mPos;
    }
}
