package com.denny.pickimage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.denny.pickerlib.Picker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView mPhotoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPhotoList = (ListView) findViewById(R.id.ivs);
        findViewById(R.id.multiPick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Picker().multi(9).start(MainActivity.this);
            }
        });
        findViewById(R.id.singlePick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Picker().single().start(MainActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String[] photos = Picker.getOutput(data);
            if (photos != null) {
                Log.i("Picker", Arrays.toString(photos));
                showPhotos(photos);
            }
        }
    }

    private void showPhotos(String[] photos) {
        List<Map<String, String>> mapList = new ArrayList<>();
        for (String item : photos) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("image", item);
            mapList.add(map);
        }
        mPhotoList.setAdapter(new SimpleAdapter(this, mapList, R.layout.image_item, new String[]{"image"}, new int[]{R.id.imageView}));
    }
}
