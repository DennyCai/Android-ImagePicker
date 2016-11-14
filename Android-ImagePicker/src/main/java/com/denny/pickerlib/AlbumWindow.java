package com.denny.pickerlib;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.denny.pickerlib.adapter.AlbumAdapter;
import com.denny.pickerlib.adapter.AlbumHolder;
import com.denny.pickerlib.model.Album;
import com.denny.pickerlib.support.SelectionHelper;
import com.denny.pickerlib.utils.Utility;

import java.util.List;

/**
 * Created by Cai on 2016/10/12.
 */

public class AlbumWindow {
    private PopupWindow mWindow;
    private List<Album> mAlbums;
    private Context mCotxt;

    private ListView mAlbumView;
    private OnCheckedListener mListener;
    private View mContent;
    private AlbumAdapter mAdapter;

    AlbumWindow(Context context , OnCheckedListener listener) {
        mCotxt = context;
        mListener = listener;
        createWindow();
    }

    void show(View anchor) {
        if(mWindow==null){
            initWindow(mContent,anchor);

        }
        if (!mWindow.isShowing())
            mWindow.showAsDropDown(anchor);
    }

    private void initWindow(View mContent,View anchor) {
        mWindow = new PopupWindow(mContent);
        mWindow.setWidth(Utility.getContentWidth(anchor));
        mWindow.setHeight((int) (Utility.getContentHeight(anchor) - mCotxt.getResources().getDimension(R.dimen.bottomHeight)));
    }

    @SuppressLint("InflateParams")
    private void createWindow() {
        mContent = LayoutInflater.from(mCotxt).inflate(R.layout.layout_album_popup, null);

        mAlbumView = (ListView) mContent.findViewById(R.id.album_list);
        mAlbumView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mAlbumView.setItemChecked(0,true);
        mAlbumView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mListener!=null){
                    mListener.onCheck(mAlbums.get(position));
                }
                mAlbumView.setItemChecked(position,true);
                hide();
            }
        });
    }

    void hide() {
        if (mWindow.isShowing())
            mWindow.dismiss();
    }

    boolean isShow(){
        return mWindow==null?false:mWindow.isShowing();
    }

    void setPhotoAlbums(List<Album> albas) {
        mAlbums = albas;
        mAdapter = new AlbumAdapter(mAlbums);
        mAdapter.setToAbsListView(mAlbumView);
        mAlbumView.setItemChecked(0,true);
        mListener.onCheck(mAlbums.get(0));
    }

    private AlbumAdapter getAdapter(){
        return mAdapter;
    }

    interface OnCheckedListener{
        void onCheck(Album album);
    }
}
