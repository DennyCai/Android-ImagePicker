package com.denny.pickerlib.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Cai on 2016/11/11.
 */

public abstract class BaseListAdpater<T> extends BaseAdapter implements IndexCheckAdapter {

    private List<T> mData;
    private AbsListView mAbsView;

    public BaseListAdpater(List<T> data){
        mData = data;
    }

    public void setToAbsListView(AbsListView view){
        view.setAdapter(this);
        mAbsView = view;
    }

    @Override
    public boolean isChecked(int position) {
        return mAbsView.getCheckedItemPositions().get(position);
    }

    @Override
    public T getItem(int position){
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = onNewView(parent);
            convertView = holder.itemView;
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        onBindView(holder,mData.get(position),position);
        return null;
    }

    protected abstract void onBindView(ViewHolder holder, T source, int position);

    protected abstract ViewHolder onNewView(ViewGroup parent);
}
