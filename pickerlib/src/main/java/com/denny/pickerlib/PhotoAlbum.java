package com.denny.pickerlib;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.util.SimpleArrayMap;
import android.util.ArrayMap;
import android.util.ArraySet;

import com.denny.pickerlib.adapter.PhotoHolder;
import com.denny.pickerlib.model.Album;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Cai on 2016/10/11.
 */

class PhotoAlbum {

    private Context mContext;
    private List<Album> mAlbums;

    PhotoAlbum(Context context) {
        mContext = context;
    }

    Cursor getLastPhotoAlbum() {
        return queryPhotos(null, "desc");
    }

    List<Album> getPhotoAlbums() {
        if (mAlbums != null)
            return mAlbums;
        mAlbums = new ArrayList<>();
        LinkedHashMap<String, Album> linkedMap = new LinkedHashMap<>();

        Cursor cursor = queryPhotos(null, "desc");
        addAllPhotoAlbum(mAlbums, cursor);
        try {
            while (cursor.moveToNext()) {
                String path = cursor.getString(cursor.getColumnIndex(cursor.getColumnNames()[0])).toLowerCase();
                String parent = path.substring(0, path.lastIndexOf('/'));
                String name = parent.substring(parent.lastIndexOf('/') + 1);
                Album album = new Album();
                album.setAlbumPath(parent);
                album.setFirstPicture(path);
                album.setAlbumName(name);
                Album al = linkedMap.get(album.getAlbumPath());
                if (al == null) {
                    linkedMap.put(album.getAlbumPath(), album);
//                    album.setPictureNumber(new File(album.getAlbumPath()).listFiles(new FilenameFilter() {
//                        @Override
//                        public boolean accept(File dir, String name) {
//                            return !new File(dir, name).isDirectory()
//                                    && name.lastIndexOf('.') != -1
//                                    && name.lastIndexOf('/') == -1
//                                    && (name.endsWith("png") || name.endsWith("jpg") || name.endsWith("jpeg") || name.endsWith("webp"));
//                        }
//                    }).length);
                }else{
                    al.incrementPicture();
                }
            }
            mAlbums.addAll(linkedMap.values());
            return mAlbums;
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    private void addAllPhotoAlbum(List<Album> albumList, Cursor cursor) {
        Album all = new Album();
        all.setAlbumName("所有图片");
        all.setAlbumPath("*");
        if (cursor.moveToNext())
            all.setFirstPicture(cursor.getString(cursor.getColumnIndex(cursor.getColumnNames()[0])));
        cursor.moveToPrevious();
        albumList.add(all);
    }

    private Cursor queryPhotos(String like, String order) {
        Uri external = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String mimeType = MediaStore.Images.Media.MIME_TYPE;
        String data = MediaStore.Images.Media.DATA;
        //select "a/b/c/a/b" as path where path like "a/b%" and substr(path,length("a/b")+2) not like "%/%";
        //滤除路径下存在文件夹
        Cursor cursor = mContext.getContentResolver().query(external, new String[]{data},
                "(" + mimeType + " = ? or " + mimeType + " = ? or " + mimeType + " = ? or " + mimeType + " = ? " + ")"
                        + (like != null ? " and " + data + " like \"" + like + "%\"" : "")
                        + (like != null ? " and substr(" + data + ",length('" + like + "')+2) not like \"%/%\"" : ""),
                new String[]{"image/png", "image/jpg", "image/jpeg", "image/webp"},
                MediaStore.Images.Media.DATE_MODIFIED + (order != null ? " " + order : ""));
        return cursor;
    }

    public Cursor getPhotoAlbum(Album album) {
        if (album == null)
            return null;
        if ("*".equals(album.getAlbumPath()))
            return queryPhotos(null, "desc");
        else
            return queryPhotos(album.getAlbumPath(), "desc");
    }
}
