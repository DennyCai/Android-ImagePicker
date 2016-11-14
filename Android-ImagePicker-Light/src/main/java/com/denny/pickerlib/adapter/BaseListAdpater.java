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
    protected AbsListView mAbsView;

    public BaseListAdpater(List<T> data){
        mData = data;
    }

    public BaseListAdpater<T> setToAbsListView(AbsListView view){
        view.setAdapter(this);
        mAbsView = view;
        return this;
    }

    @Override
    public boolean isChecked(int position) {
        if(mAbsView==null||mAbsView.getCheckedItemPositions()==null) {
            return false;
        }
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
            holder = onNewView(parent,position);
            convertView = holder.itemView;
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setPosition(position);
        onBindView(holder,mData.get(position),position);
        return convertView;
    }

    protected abstract void onBindView(ViewHolder holder, T source, int position);

    protected abstract ViewHolder onNewView(ViewGroup parent, int position);

    public void setDatas(List<T> newData) {
        mData.clear();
        mData.addAll(newData);
        notifyDataSetChanged();
    }
}
