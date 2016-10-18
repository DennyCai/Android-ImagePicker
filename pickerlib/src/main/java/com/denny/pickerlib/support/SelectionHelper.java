package com.denny.pickerlib.support;

import android.text.TextUtils;
import android.util.SparseBooleanArray;

import java.util.Collections;

/**
 * Created by Cai on 2016/10/17.
 */

public class SelectionHelper {

    private SparseBooleanArray mMap;
    private int mMaxSelection;
    private OnCheckedListener mListener;

    public SelectionHelper(int selectionNumber){
        mMap = new SparseBooleanArray(selectionNumber);
        mMaxSelection = selectionNumber;
    }

    public void setDefaultSelected(boolean defaultSelected,int pos){
        if(defaultSelected)
            mMap.put(pos,true);
    }

    public boolean isSelected(int position){
        return mMap.get(position);
    }

    private void setMap(int position, boolean select) {
        if(mMaxSelection==1){
            mMap.clear();
        }
        if(mMap.size()>=mMaxSelection&&select)
            return;
        if(select) {
            mMap.put(position, true);
        }
        else {
            mMap.delete(position);
        }
        notifyChecked(position,select);
    }

    public void setPosition(int pos, boolean selected) {
        setMap(pos,selected);
    }

    public int size() {
        return mMap.size();
    }

    public void reset() {
        mMap.clear();
    }

    private void notifyChecked(int pos,boolean checked){
        if(mListener!=null)
            mListener.onChecked(pos,checked);
    }

    public void setOnCheckedListener(OnCheckedListener mListener) {
        this.mListener = mListener;
    }

    public int[] getSelected() {
        final int len =  mMap.size();
        int[] array = new int[len];
        String mapStr = mMap.toString();
        mapStr = mapStr.substring(1,mapStr.length()-1);
        String[] keyVals = mapStr.split(",");
        for (int i=0;i<keyVals.length;++i){
            array[i] = Integer.parseInt(keyVals[i].split("=")[0].trim());
        }
        return array;
    }

    public interface OnCheckedListener{
        void onChecked(int position,boolean checked);
    }
}
