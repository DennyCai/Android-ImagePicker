package com.denny.pickerlib;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by Cai on 2016/10/11.
 */

public class Picker {

    interface Extra{
        String CHOICE_NUMBER = "choice_number";
        String OUTPUT = "output";
    }

    private Intent mIntent;

    public Picker(){
        mIntent = new Intent();
    }

    public Picker single(){
        mIntent.putExtra(Extra.CHOICE_NUMBER,1);
        return this;
    }

    public Picker multi(int max){
        mIntent.putExtra(Extra.CHOICE_NUMBER,max);
        return this;
    }

    public void start(Activity activity){
        start(activity,0);
    }

    private void start(Activity activity,int requestCode){
        mIntent.setClass(activity,PickerActivity.class);
        activity.startActivityForResult(mIntent,requestCode);
    }

    @Nullable
    public static String[] getOutput(Intent result){
        if(result!=null)
            return result.getStringArrayExtra(Extra.OUTPUT);
        else
            return null;
    }

    public Intent getIntent(){
        return mIntent;
    }
}
