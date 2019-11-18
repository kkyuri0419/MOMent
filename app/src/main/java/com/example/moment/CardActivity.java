package com.example.moment;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.example.moment.adapter.ViewPagerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class CardActivity extends AppCompatActivity {

    String pathSave = "";


    public ViewAllActivity getViewAllActivity(MediaRecorder mediaRecorder) {
        ViewAllActivity viewAllActivity = new ViewAllActivity();
        viewAllActivity.mediaRecorder = mediaRecorder;
        return viewAllActivity;
    }

    MediaRecorder mediaRecorder = new MediaRecorder();
    MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_card);
        findViewById(R.id.audioStopbtn).setOnClickListener(onClickListener);

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
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
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


