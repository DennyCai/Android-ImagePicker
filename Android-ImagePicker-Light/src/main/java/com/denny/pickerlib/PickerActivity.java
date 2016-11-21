package com.denny.pickerlib;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.denny.pickerlib.adapter.ImageLoader;
import com.denny.pickerlib.adapter.PhotoAdapter;
import com.denny.pickerlib.model.Album;
import com.denny.pickerlib.support.SelectionHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Cai on 2016/10/11.
 */

public class PickerActivity extends BaseActivity {

    private int mChoiceNumber;

    private AlbumPresenter mAlbumPresenter;
    private AlbumWindow mAlbunWin;
    private PhotoAdapter mAdapter;

    private GridView mPhotoWall;
    private TextView mAlbum;
    private Button mPick;
    private ProgressDialog mPrg;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        init();
        super.onCreate(savedInstanceState);
        loadInput();

        setupView();

    }

    private void showSelected() {
        int checkSize;
        mPick.setText(getString(R.string.pick, checkSize = mPhotoWall.getCheckedItemCount(), mChoiceNumber));
        if (checkSize > 0) {
            mPick.setEnabled(true);
        } else
            mPick.setEnabled(false);
    }

    private void init() {
        ImageLoader.init();
        mAlbumPresenter = new AlbumPresenter(this);
    }

    @Override
    public void onBackPressed() {
        if (mAlbunWin != null && mAlbunWin.isShow())
            mAlbunWin.hide();
        else {
            setResult(RESULT_CANCELED);
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPrg != null) mPrg.dismiss();
        mAlbumPresenter.destroy();
        ImageLoader.destroy();
    }

    private void showPhotos(Album album) {
        setTitle(album.getAlbumName());
        mPhotoWall.clearChoices();
        mAlbum.setText(album.getAlbumName());
        mAdapter.setDatas(album.getPictures());
        showSelected();
        ImageLoader.reset();
    }

    private void loadInput() {
        mChoiceNumber = getIntent().getIntExtra(Picker.Extra.CHOICE_NUMBER, 1);
    }

    private void setupView() {
        setContentView(R.layout.picker_activity);
        mPhotoWall = findView(R.id.photoWall);
        mAlbum = findView(R.id.choiceAlbum);
        mPick = findView(R.id.pick);

        mPhotoWall.setChoiceMode(mChoiceNumber == 1 ? GridView.CHOICE_MODE_SINGLE : GridView.CHOICE_MODE_MULTIPLE);
        mAdapter = new PhotoAdapter(new ArrayList<String>());
        mAdapter.setToAbsListView(mPhotoWall);
        mPhotoWall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mPhotoWall.getCheckedItemCount() > mChoiceNumber) {
                    if (mPhotoWall.getCheckedItemPositions().get(position)) {
                        mPhotoWall.setItemChecked(position, false);
                        Toast.makeText(PickerActivity.this, getString(R.string.over_text, mChoiceNumber), Toast.LENGTH_LONG).show();
                    }
                }
                showSelected();
                mAdapter.notifyDataSetChanged();
            }
        });
        mAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlbum(findViewById(R.id.bottomLayout));
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

        mAlbumPresenter.loadPhotoAlbums();
    }

    private void pickImages() {
        String[] photoArr = getPickImages();
        Intent data = new Intent();
        data.putExtra(Picker.Extra.OUTPUT, photoArr);
        setResult(RESULT_OK, data);
        finish();
    }

    private String[] getPickImages() {
        SparseBooleanArray array = mPhotoWall.getCheckedItemPositions();
        List<String> photos = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            if(array.valueAt(i)) {
                photos.add(mAdapter.getItem(array.keyAt(i)));
            }
        }
        String[] arr = new String[photos.size()];
        photos.toArray(arr);
        return arr;
    }

    private void showAlbum(View anchor) {
        if (mAlbunWin.isShow()) {
            mAlbunWin.hide();
        } else {
            mAlbunWin.show(anchor);
        }
    }

    @Nullable
    protected <T extends View> T findView(@IdRes int id) {
        return (T) findViewById(id);
    }

    public void showPhotoAlbums(List<Album> albas) {
        mAlbunWin.setPhotoAlbums(albas);
    }

    public void showLoadding() {
        mPrg = ProgressDialog.show(this, "加载中", "");
    }

    public void dismissLoadding() {
        mPrg.dismiss();
    }
}
