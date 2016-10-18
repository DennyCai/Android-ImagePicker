package com.denny.pickerlib;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.denny.pickerlib.adapter.AlbumAdapter;
import com.denny.pickerlib.adapter.ImageLoader;
import com.denny.pickerlib.adapter.PhotoAdapter;
import com.denny.pickerlib.model.Album;
import com.denny.pickerlib.support.SelectionHelper;

/**
 * Created by Cai on 2016/10/11.
 */

public class PickerActivity extends AppCompatActivity {

    private RecyclerView mPhotoWall;

    private int mChoiceNumber;

    private PhotoAlbum mPhotoAlbum;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        init();
        super.onCreate(savedInstanceState);
        loadInput();

        setupView();

        Album all = new Album();
        all.setAlbumName("所有图片");
        all.setAlbumPath("*");
        showPhotos(all);
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
        if(mAlbunWin!=null) mAlbunWin.hide();
        ImageLoader.destroy();
    }

    private void showPhotos(Album album) {
        setTitle(album.getAlbumName());
        mAlbum.setText(album.getAlbumName());
        if (mPhotoAlbum == null)
            mPhotoAlbum = new PhotoAlbum(this);
        Cursor photos = mPhotoAlbum.getPhotoAlbum(album);
        if (mAdapter == null)
            mPhotoWall.setAdapter(mAdapter = new PhotoAdapter(photos, mChoiceNumber, mOnChecked));
        else {
            mAdapter.sweapCursor(photos);
        }
        mPhotoWall.scrollToPosition(0);
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
        mPick.setEnabled(false);
        mPick.setText(getString(R.string.pick, 0, mChoiceNumber));
        mPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImages();
            }
        });

        RecyclerView.LayoutManager lm = new SquareGridLayoutManager(3, SquareGridLayoutManager.VERTICAL);
        lm.setAutoMeasureEnabled(false);
        mPhotoWall.setLayoutManager(lm);
    }

    private void pickImages() {
        String[] photoArr = mAdapter.getCheckedDataArray();
        Intent data = new Intent();
        data.putExtra(Picker.Extra.OUTPUT,photoArr);
        setResult(RESULT_OK,data);
        finish();
    }

    private void showAlbum(View anchor) {
        if (mAlbunWin == null)
            mAlbunWin = new AlbumWindow(this, mPhotoAlbum.getPhotoAlbums(), new AlbumAdapter.OnAlbumSelectedListener() {
                @Override
                public void onAlbumSelect(Album album) {
                    showPhotos(album);
                    mAlbunWin.hide();
                }
            });
        mAlbunWin.show(anchor);
    }

    @Nullable
    protected <T> T findView(@IdRes int id) {
        return (T) findViewById(id);
    }
}
