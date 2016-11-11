package com.denny.pickerlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.denny.pickerlib.adapter.AlbumAdapter;
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

    AlbumWindow(Context context , OnCheckedListener listener) {
        mCotxt = context;
        mListener = listener;
    }

    void show(View anchor) {
        if (mWindow == null) {
            createWindow(anchor);
        }
        if (!mWindow.isShowing())
            mWindow.showAsDropDown(anchor);
    }

    @SuppressLint("InflateParams")
    private void createWindow(View anchor) {
        View content = LayoutInflater.from(mCotxt).inflate(R.layout.layout_album_popup, null);

        mAlbumView = (ListView) content.findViewById(R.id.album_list);
        mAlbumView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mAlbumView.setItemChecked(0,true);
        mAlbumView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(mListener!=null){
                    mListener.onCheck(mAlbums.get(position));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mWindow = new PopupWindow(content);
        mWindow.setWidth(Utility.getContentWidth(anchor));
        mWindow.setHeight((int) (Utility.getContentHeight(anchor) - mCotxt.getResources().getDimension(R.dimen.bottomHeight)));
    }

    void hide() {
        if (mWindow.isShowing())
            mWindow.dismiss();
    }

    boolean isShow(){
        return mWindow.isShowing();
    }

    void setPhotoAlbums(List<Album> albas) {
        mAlbums = albas;
        new AlbumAdapter(mAlbums).setToAbsmAlbumView
    }

    interface OnCheckedListener{
        void onCheck(Album album);
    }
}
