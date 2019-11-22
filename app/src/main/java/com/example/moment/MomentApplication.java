package com.example.moment;

import android.app.Application;
import android.media.MediaRecorder;

import com.example.moment.helper.DBhelper;

public class MomentApplication extends Application {
    public MediaRecorder mediaRecorder;
    public DBhelper dBhelper;
    public boolean isAudio=false;
    public boolean dad = false;
    public boolean mom =false;
    public boolean daughter =false;
    public boolean son =false;
    boolean[] btn_state = {false,false,false,false,false,false,false,false,false};


    @Override
    public void onCreate() {
        super.onCreate();
        mediaRecorder = new MediaRecorder();
        dBhelper = new DBhelper(this,"MomentDB.db", null, 1);
    }
}
