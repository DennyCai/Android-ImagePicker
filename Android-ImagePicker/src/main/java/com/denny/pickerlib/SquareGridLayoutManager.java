package com.denny.pickerlib;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Cai on 2016/10/14.
 */

public class SquareGridLayoutManager extends StaggeredGridLayoutManager {
    public SquareGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public SquareGridLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

}
