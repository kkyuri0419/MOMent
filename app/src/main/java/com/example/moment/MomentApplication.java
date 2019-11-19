package com.example.moment;

import android.app.Application;
import android.media.MediaRecorder;

import com.example.moment.helper.DBhelper;

public class MomentApplication extends Application {
    public MediaRecorder mediaRecorder;
    public DBhelper dBhelper;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaRecorder = new MediaRecorder();
        dBhelper = new DBhelper(this,"MomentDB.db", null, 1);
    }
}
