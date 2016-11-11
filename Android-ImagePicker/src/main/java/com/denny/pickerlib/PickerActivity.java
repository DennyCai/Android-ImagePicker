package com.denny.pickerlib;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.denny.pickerlib.adapter.ImageLoader;
import com.denny.pickerlib.adapter.PhotoAdapter;
import com.denny.pickerlib.model.Album;
import com.denny.pickerlib.support.SelectionHelper;

import java.util.List;


/**
 * Created by Cai on 2016/10/11.
 */

public class PickerActivity extends BaseActivity {

    private GridView mPhotoWall;

    private int mChoiceNumber;

    private AlbumPresenter mAlbumPresenter;
    private AlbumWindow mAlbunWin;
    private PhotoAdapter mAdapter;

    private Button mAlbum;
    private Button mPick;

    private SelectionHelper.OnCheckedListener mOnChecked = new SelectionHelper.OnCheckedListener() {
        @Override
        public void onChecked(int position, boolean checked) {
            showSelected(position, checked);
        }
    };
    private ProgressDialog mPrg;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        init();
        super.onCreate(savedInstanceState);
        loadInput();

        setupView();

    }

    private void showSelected(int position, boolean checked) {
        int checkSize = -1;
        mPick.setText(getString(R.string.pick, checkSize = mAdapter.getCheckedSize(), mChoiceNumber));
        if(checkSize>0){
            mPick.setEnabled(true);
        }else
            mPick.setEnabled(false);
    }

    private void init() {
        ImageLoader.init();
    }

    @Override
    public void onBackPressed() {
        if (mAlbunWin!=null&&mAlbunWin.isShow())
            mAlbunWin.hide();
        else {
            setResult(RESULT_CANCELED);
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPrg!=null) mPrg.dismiss();
        if(mAlbunWin!=null) mAlbunWin.hide();
        ImageLoader.destroy();
    }

    private void showPhotos(Album album) {
        setTitle(album.getAlbumName());
        mAlbum.setText(album.getAlbumName());
        if (mAlbumPresenter == null)
            mAlbumPresenter = new AlbumPresenter(this);
        Cursor photos = mAlbumPresenter.getPhotoAlbum(album);
        if (mAdapter == null)
            mPhotoWall.setAdapter(null);
        else {
            mAdapter.sweapCursor(photos);
        }
        mPhotoWall.scrollTo(0,0);
    }

    private void loadInput() {
        mChoiceNumber = getIntent().getIntExtra(Picker.Extra.CHOICE_NUMBER, 1);
    }

    private void setupView() {
        setContentView(R.layout.picker_activity);
        mPhotoWall = findView(R.id.photoWall);
        mAlbum = findView(R.id.choiceAlbum);
        mPick = findView(R.id.pick);

        mAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlbum(v);
            }
        });
        mAlbunWin = new AlbumWindow(this, new AlbumWindow.OnCheckedListener() {
            @Override
            public void onCheck(Album album) {
                showPhotos(album);
            }
        });
        mPick.setEnabled(false);
        mPick.setText(getString(R.string.pick, 0, mChoiceNumber));
        mPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImages();
            }
        });
    }

    private void pickImages() {
        String[] photoArr = getPickImages();
        Intent data = new Intent();
        data.putExtra(Picker.Extra.OUTPUT,photoArr);
        setResult(RESULT_OK,data);
        finish();
    }

    private String[] getPickImages() {
        return new String[0];
    }

    private void showAlbum(View anchor) {
        mAlbunWin.show(anchor);
    }

    @Nullable
    protected <T> T findView(@IdRes int id) {
        return (T) findViewById(id);
    }

    public void showPhotoAlbums(List<Album> albas) {
        mAlbunWin.setPhotoAlbums(albas);
    }

    public void showLoadding() {
        mPrg = ProgressDialog.show(this,"加载中","");
    }

    public void dismissLoadding() {
        mPrg.dismiss();
    }
}
