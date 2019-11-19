package com.example.moment;

import android.app.Application;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.example.moment.adapter.ViewPagerAdapter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class CardActivity extends AppCompatActivity {

    String pathSave;
    MediaRecorder mediaRecorder;
    private MomentApplication mApp;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_card);
        findViewById(R.id.audioStopbtn).setOnClickListener(onClickListener);
        mApp = (MomentApplication) getApplication();
        mediaRecorder = mApp.mediaRecorder;


      //  인덱스 랜덤으로 만들어서 넣어주면 카드오브ㅈ
        CardObject cardObject=mApp.dBhelper.selectcard(0);
/*
        DiaryObject diaryObject = new DiaryObject();
        diaryObject.d_audio ="audio path";
        diaryObject.d_photo = "photo path";
        diaryObject.d_title = " title";//yyyyMMdd
        diaryObject.d_content = "content";//edittext.gettext.tostring

        long result = mApp.dBhelper.insertdiary(diaryObject);
        if(result ==1){
            //정상 다음화면
        }else{
            //문제가 있음을 사용자에게 toast
        }
        */
        pathSave = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath()+
                File.separator+
                new SimpleDateFormat("yyyyMMddhhmmss_SSS").format(new Date())+".wav";
        };
    View.OnClickListener onClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.audioStopbtn:
                    stopAudio();
                    break;
            }
        }
    };

    private void stopAudio() {
        if(mediaRecorder != null){
            mediaRecorder.stop();
            mediaRecorder.release();
            setupMediaRecorder();
            Toast.makeText(this, "녹음이 종료됩니다.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"녹음본이 없습니다.", Toast.LENGTH_SHORT).show();
        }

    }

    private void setupMediaRecorder() {
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(pathSave);
    }


}


