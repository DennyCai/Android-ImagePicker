package com.denny.pickerlib.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.denny.pickerlib.model.Album;
import com.denny.pickerlib.support.SelectionHelper;

import java.util.List;

/**
 * Created by Cai on 2016/10/17.
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumHolder> {

    private OnAlbumSelectedListener mListener;
    private List<Album> mDatas;
    private SelectionHelper mSingleSelection;

    public AlbumAdapter(List<Album> list){
        mSingleSelection = new SelectionHelper(1);
        mSingleSelection.setDefaultSelected(true,0);
        mDatas = list;
    }

    public AlbumAdapter(List<Album> list,OnAlbumSelectedListener listener){
        this(list);
        mListener = listener;
    }

    @Override
    public AlbumHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final AlbumHolder holder = new AlbumHolder(parent);
        View.OnClickListener listener = null;
        holder.itemView.setOnClickListener(listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                if(mSingleSelection.isSelected(pos)) {
                    return;
                }
                mSingleSelection.setPosition(pos,true);
                mListener.onAlbumSelect(mDatas.get(pos));
                notifyDataSetChanged();
            }
        });
        holder.albumRadio.setOnClickListener(listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(AlbumHolder holder, int position) {
        Album album = mDatas.get(position);
        holder.albumName.setText(album.getAlbumName());
        if(position>0) holder.picNumber.setText(album.getPictureNumber()+"张");
        else holder.picNumber.setText("");
        ImageLoader.loadImage(album.getFirstPicture(),holder.albumImage,400,0);
        holder.setChecked(mSingleSelection.isSelected(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public interface OnAlbumSelectedListener{
        void onAlbumSelect(Album album);
    }
}
