package com.denny.pickerlib;

import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.denny.pickerlib.model.Album;
import com.denny.pickerlib.utils.BitmapUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Cai on 2016/10/11.
 */

class AlbumPresenter {

    private final static int MAXSIZE = 400;

    private PickerActivity mView;
    private List<Album> mAlbums;
    private AlbumLoader mLoader;

    AlbumPresenter(PickerActivity view) {
        mView = view;
        mLoader = new AlbumLoader();
    }

    void loadPhotoAlbums() {
        mLoader.execute();
    }

    List<Album> getPhotoAlbums() {
        if (mAlbums != null) {
            return mAlbums;
        }
        mAlbums = new ArrayList<>();
        Album all = getAllPhotos();
        mAlbums.add(all);
        List<Album> albumList = filterPhotoDir(all.getPictures());
        mAlbums.addAll(albumList);
        return mAlbums;
    }

    private List<Album> filterPhotoDir(List<String> pictures) {
        LinkedHashMap<String, Album> albumPahtMap = new LinkedHashMap<>();
        for (Iterator<String> item = pictures.iterator(); item.hasNext(); ) {
            String path = item.next();
            String dir = new File(path).getParent();
            Album album = albumPahtMap.get(dir);
            if (album == null) {
                album = new Album();
                album.setAlbumName(new File(dir).getName());
                album.setFirstPictureBmp(BitmapUtils.loadBitmap(path,MAXSIZE));
                album.setAlbumPath(dir);
                albumPahtMap.put(dir,album);
            }
            album.addPicture(path);
        }
        return new ArrayList<>(albumPahtMap.values());
    }


    private Album getAllPhotos() {
        Uri external = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String mimeType = MediaStore.Images.Media.MIME_TYPE;
        String data = MediaStore.Images.Media.DATA;
        Cursor cursor = null;
        try {
            cursor = mView.getBaseContext().getContentResolver().query(external, new String[]{data},
                    "(" + mimeType + " = ? or " + mimeType + " = ? or " + mimeType + " = ? or " + mimeType + " = ? " + ")",
                    new String[]{"image/png", "image/jpg", "image/jpeg", "image/webp"},
                    MediaStore.Images.Media.DATE_MODIFIED + " desc");
            return createAlbum(cursor);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private Album createAlbum(Cursor cursor) {
        HashSet<String> pathSet = new HashSet<>();
        Album all = new Album();
        all.setAlbumPath("*");
        all.setAlbumName("所有图片");

        int index = cursor.getColumnIndex(cursor.getColumnNames()[0]);

        while (cursor.moveToNext()) {
            String path = cursor.getString(index).toLowerCase();
            if (!pathSet.contains(path)) {
                all.addPicture(path);
            }
        }

        all.setFirstPictureBmp(BitmapFactory.decodeFile(all.getPictures().get(0)));
        return all;
    }


    void destroy() {
        if (mLoader.getStatus() != AsyncTask.Status.FINISHED) {
            mLoader.cancel(true);
        }
    }

    private class AlbumLoader extends AsyncTask<Void, Integer, List<Album>> {

        @Override
        protected void onPreExecute() {
            mView.showLoadding();
        }

        @Override
        protected List<Album> doInBackground(Void... params) {
            List<Album> result = getPhotoAlbums();
            return result;
        }

        @Override
        protected void onPostExecute(List<Album> alba) {
            mView.dismissLoadding();
            if (isCancelled()) {
                return;
            }
            mView.showPhotoAlbums(alba);
        }
    }
}
