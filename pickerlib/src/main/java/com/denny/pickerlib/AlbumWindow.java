package com.denny.pickerlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.denny.pickerlib.adapter.AlbumAdapter;
import com.denny.pickerlib.model.Album;
import com.denny.pickerlib.utils.Utility;

import java.util.List;

/**
 * Created by Cai on 2016/10/12.
 */

public class AlbumWindow {
    private final AlbumAdapter.OnAlbumSelectedListener mListener;
    private PopupWindow mWindow;
    private List<Album> mAlbums;
    private Context mCotxt;

    public AlbumWindow(Context context, List<Album> albums, AlbumAdapter.OnAlbumSelectedListener listener) {
        mAlbums = albums;
        mCotxt = context;
        mListener = listener;
    }

    public void show(View anchor) {
        if (mWindow == null) {
            createWindow(anchor);
        }
        if (!mWindow.isShowing())
            mWindow.showAsDropDown(anchor);
    }

    @SuppressLint("InflateParams")
    private void createWindow(View anchor) {
        View content = LayoutInflater.from(mCotxt).inflate(R.layout.layout_album_popup, null);
        mWindow = new PopupWindow(content);
        RecyclerView albumView = (RecyclerView) content.findViewById(R.id.album_rv);
        albumView.setLayoutManager(new LinearLayoutManager(mCotxt));
        albumView.setAdapter(new AlbumAdapter(mAlbums,mListener));
        mWindow.setWidth(Utility.getContentWidth(anchor));
        mWindow.setHeight((int) (Utility.getContentHeight(anchor) - mCotxt.getResources().getDimension(R.dimen.bottomHeight)));
    }

    public void hide() {
        if (mWindow.isShowing())
            mWindow.dismiss();
    }

    public boolean isShow(){
        return mWindow.isShowing();
    }

}
