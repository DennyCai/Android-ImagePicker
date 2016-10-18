package com.denny.pickerlib.adapter;

import android.database.Cursor;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.denny.pickerlib.support.SelectionHelper;

/**
 * Created by Cai on 2016/10/11.
 */

public class PhotoAdapter extends CursorAdapter<PhotoHolder> {


    private int mMaxSize = 400;

    private SelectionHelper mSelecter;


    public PhotoAdapter(Cursor cursor,int maxChoice) {
        setCursor(cursor);
        mSelecter = new SelectionHelper(maxChoice);
    }

    public PhotoAdapter(Cursor cursor, int maxChoice, SelectionHelper.OnCheckedListener listener){
        this(cursor, maxChoice);
        mSelecter.setOnCheckedListener(listener);
    }

    @Override
    public void sweapCursor(Cursor cursor) {
        mSelecter.reset();
        super.sweapCursor(cursor);
    }

    @Override
    public PhotoHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final PhotoHolder holder = new PhotoHolder(parent);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle(holder.getAdapterPosition());
            }
        });
        holder.box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle(holder.getAdapterPosition());
            }
        });
        return holder;
    }

    private void toggle(int position){
        setChecked(position,!isChecked(position));
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(PhotoHolder holder, Cursor cursor) {
        ImageLoader.loadImage(cursor.getString(cursor.getColumnIndex(cursor.getColumnNames()[0])),
                holder.imageView, mMaxSize,
                android.R.drawable.sym_def_app_icon);
        holder.setChecked(isChecked(cursor.getPosition()));
    }

    private boolean isChecked(int position){
        return mSelecter.isSelected(position);
    }

    private void setChecked(int position,boolean checked){
        mSelecter.setPosition(position,checked);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        close();
    }

    public int[] getCheckedArray() {
        int[] array = mSelecter.getSelected();
        return array;
    }

    public String[] getCheckedDataArray(){
        int[] pos = mSelecter.getSelected();
        String[] photos = new String[pos.length];
        Cursor cursor = getCursor();
        int before = cursor.getPosition();
        for (int i=0;i<pos.length;++i){
            cursor.moveToPosition(pos[i]);
            photos[i] = cursor.getString(cursor.getColumnIndex(cursor.getColumnNames()[0]));
        }
        cursor.moveToPosition(before);
        return photos;
    }

    public int getCheckedSize(){
        return mSelecter.size();
    }
}
