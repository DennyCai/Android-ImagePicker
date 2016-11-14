package com.denny.pickerlib;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Cai on 2016/11/11.
 */

public class BaseActivity extends Activity {

    private TitleHolder mTitleHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.custom_title);
        mTitleHolder = new TitleHolder(this);
        mTitleHolder.initStyle();
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mTitleHolder.setTitle(title);
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        mTitleHolder.setTitle(titleId);
    }

    private class TitleHolder{
        TextView title;
        ImageView icon;

        TitleHolder(Activity activity){
            title = (TextView) findViewById(R.id.title);
            icon = (ImageView) findViewById(R.id.ivIcon);
            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        public void setTitle(CharSequence title) {
            this.title.setText(title);
        }

        public void setTitle(int titleId) {
            this.title.setText(titleId);
        }

        public void initStyle() {
        }
    }
}
