package com.example.moment;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.gridlayout.widget.GridLayout;
import androidx.viewpager.widget.ViewPager;

public class ViewAll extends AppCompatActivity {

    GridLayout mainGrid;
    boolean[] btn_state = {false,false,false,false,false,false,false,false,false};
    public String pathSave = "";
    private MomentApplication mApp;
    final int REQUEST_PERMISSION_CODE = 1000;


    ViewPager viewPager;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_card);
        mApp = (MomentApplication) getApplication();
        mainGrid = (GridLayout) findViewById(R.id.mainGrid);
        findViewById(R.id.startButton).setOnClickListener(onClickListener);
        findViewById(R.id.settingButton).setOnClickListener(onClickListener);
        findViewById(R.id.diaryButton).setOnClickListener(onClickListener);



        setToggleEvent(mainGrid);

    }

    private void setToggleEvent(GridLayout mainGrid) {
        for(int i = 0; i<mainGrid.getChildCount(); i++){
            final ImageView imageView = (ImageView)mainGrid.getChildAt(i);
            final int finalI = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btn_state[finalI] == false){
                        imageView.setImageResource(R.drawable.card_back);
                        btn_state[finalI] = true;
                    }
                    else{
                        switch (finalI){
                            case 0:
                                imageView.setImageResource(R.drawable.final0);
                                btn_state[finalI] = false;
                                break;
                            case 1:
                                imageView.setImageResource(R.drawable.final1);
                                btn_state[finalI] = false;
                                break;
                            case 2:
                                imageView.setImageResource(R.drawable.final2);
                                btn_state[finalI] = false;
                                break;
                            case 3:
                                imageView.setImageResource(R.drawable.final3);
                                btn_state[finalI] = false;
                                break;
                            case 4:
                                imageView.setImageResource(R.drawable.final4);
                                btn_state[finalI] = false;
                                break;
                            case 5:
                                imageView.setImageResource(R.drawable.final5);
                                btn_state[finalI] = false;
                                break;
                            case 6:
                                imageView.setImageResource(R.drawable.final6);
                                btn_state[finalI] = false;
                                break;
                            case 7:
                                imageView.setImageResource(R.drawable.final7);
                                btn_state[finalI] = false;
                                break;
                            case 8:
                                imageView.setImageResource(R.drawable.final8);
                                btn_state[finalI] = false;
                                break;

                        }

                    }
                }
            });
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){


        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.startButton:
                    showPopup();
                    break;
                case R.id.settingButton:
                    startUserProfileActivity();
                    break;
                case R.id.diaryButton:
                    startDiaryList();
                    break;
            }
        }
    };

    private void startUserProfileActivity() {
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }

    private void startViewBig() {
        Intent intent = new Intent(this, CategoryActivity.class);
        startActivity(intent);
    }

    private void startCardActivity() {
        Intent intent = new Intent(this, CardActivity.class);
        startActivity(intent);
    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }







    private void showPopup() {


        final AlertDialog.Builder alert = new AlertDialog.Builder(ViewAll.this);
        View mView = getLayoutInflater().inflate(R.layout.popup_audio_record,null);

        final Button audioYesBtn = (Button)mView.findViewById(R.id.audioYesBtn);
        final Button audioNoBtn = (Button)mView.findViewById(R.id.audioNoBtn);

        alert.setView((mView));

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

//        audioYesBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //오디오 기능 ON
//                //카드화면으로 바로가기
//                startToast("녹음이 시작됩니다.");
//                startCardActivity();
//                alertDialog.dismiss();
//
//            }
//        });
//
        audioNoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //카드화면으로 바로가기
                startCardActivity();
                alertDialog.dismiss();
            }
        });


        audioYesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkPermissionFromDevice()) {
                    pathSave = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"
                            + UUID.randomUUID().toString() + "_audio_record.3gp";
                    setupMediaRecorder();
                    try {
                        mApp.mediaRecorder.prepare();
                        mApp.mediaRecorder.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

//                        audioYesBtn.setEnabled(false);
//                        audioNoBtn.setEnabled(false);

                    mApp.isAudio = true;
                    startToast("녹음이 시작됩니다.");
                    startCardActivity();
                    alertDialog.dismiss();
                }else{
                    requestPermission();
                }

            }
        });


        alertDialog.show();
        alertDialog.getWindow().setLayout(850,1100);
    }

    private void setupMediaRecorder() {
        mApp.mediaRecorder = new MediaRecorder();
        mApp.mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mApp.mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mApp.mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mApp.mediaRecorder.setOutputFile(pathSave);
    }

    private boolean checkPermissionFromDevice() {
        int write_external_storage_result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio_result = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        return write_external_storage_result == PackageManager.PERMISSION_GRANTED &&
                record_audio_result == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this,"Permission Granted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        },REQUEST_PERMISSION_CODE);
    }

    private void startDiaryList() {
        Intent intent = new Intent(this, DiaryList.class);
        startActivity(intent);
    }


}