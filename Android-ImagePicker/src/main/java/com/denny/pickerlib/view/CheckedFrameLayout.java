package com.denny.pickerlib.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;

import com.denny.pickerlib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cai on 2016/11/7.
 */

public class CheckedFrameLayout extends FrameLayout implements Checkable {

    private int mViewId;
    private Checkable mCheckableChild;

    public CheckedFrameLayout(Context context) {
        super(context);
    }

    public CheckedFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }



    public CheckedFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CheckedFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CheckedFrameLayout);
        try {
            mViewId = array.getResourceId(R.styleable.CheckedFrameLayout_checkableViewId,0);
        }finally {
            array.recycle();
        }
    }


    @Override
    public void setChecked(boolean checked) {
        setChildChecked(checked);
    }

    private void setChildChecked(boolean checked) {
        if(mCheckableChild==null){
            mCheckableChild = (Checkable) findViewById(mViewId);
        }
        mCheckableChild.setChecked(checked);
    }

    @Override
    public boolean isChecked() {
        return isChildChecked();
    }

    private boolean isChildChecked() {
        if(mCheckableChild==null){
            mCheckableChild = (Checkable) findViewById(mViewId);
        }
        return mCheckableChild.isChecked();
    }

    @Override
    public void toggle() {
        if(mCheckableChild==null){
            mCheckableChild = (Checkable) findViewById(mViewId);
        }
        mCheckableChild.toggle();
    }
}
