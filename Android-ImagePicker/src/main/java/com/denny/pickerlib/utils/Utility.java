package com.denny.pickerlib.utils;

import android.view.View;

/**
 * Created by Cai on 2016/10/17.
 */
public class Utility {
    public static int getContentWidth(View anchor) {
        View view = findContentView(anchor);
        if(view==null)
            throw new IllegalStateException("anchor is not in content view");
        return view.getMeasuredWidth();
    }

    private static View findContentView(View view) {
        while(view!=null&&view.getId()!=android.R.id.content){
            view = (View) view.getParent();
        }
        return view;
    }

    public static int getContentHeight(View anchor) {
        View view = findContentView(anchor);
        if(view==null)
            throw new IllegalStateException("anchor is not in content view");
        return view.getMeasuredHeight();
    }
}
