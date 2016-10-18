package com.denny.pickimage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.denny.pickerlib.Picker;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        if(resultCode==RESULT_OK) {
            String[] photos = Picker.getOutput(data);
            if(photos!=null)
                Log.i("Picker", Arrays.toString(photos));
        }
    }
}
