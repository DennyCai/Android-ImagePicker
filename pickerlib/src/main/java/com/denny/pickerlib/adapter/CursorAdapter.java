package com.denny.pickerlib.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Cai on 2016/10/11.
 */

public abstract class CursorAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private Cursor mCursor;
    private int mCount = 0;

    public void setCursor(Cursor cursor){
        if (cursor!=null&& !cursor.isClosed()&&cursor!=mCursor){
            mCursor = cursor;
        }
        mCount = mCursor.getCount();
    }

    public void sweapCursor(Cursor cursor){
        Cursor preCur = mCursor;
        setCursor(cursor);
        if(preCur!=mCursor){
            preCur.close();
        }
        notifyDataSetChanged();
    }

    protected Cursor getCursor(){
        return mCursor;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        mCursor.moveToPosition(position);
        onBindViewHolder(holder,mCursor);
    }

    public abstract void onBindViewHolder(VH holder,Cursor cursor);

    public void close(){
        if(mCursor!=null&& !mCursor.isClosed())
            mCursor.close();
    }

    @Override
    public int getItemCount() {
        return mCount;
    }
}
