package com.example.moment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class CardActivity extends AppCompatActivity {

    private ActionMode actionMode;
    String pathSave;
    MediaRecorder mediaRecorder;
    private MomentApplication mApp;
    final int REQUEST_PERMISSION_CODE = 1000;
    private int count =0;
    MomentApplication dad;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twenty_card);

        findViewById(R.id.closebtn).setOnClickListener(onClickListener);
        findViewById(R.id.audioStopbtn).setOnClickListener(onClickListener);
        findViewById(R.id.like).setOnClickListener(onClickListener);
        findViewById(R.id.hate).setOnClickListener(onClickListener);
        mApp = (MomentApplication) getApplication();
        mediaRecorder = mApp.mediaRecorder;

        if (mApp.isAudio!=false){
            ImageButton imageButton = (ImageButton)findViewById(R.id.audioStopbtn);
            imageButton.setImageResource(R.drawable.audio_on);
        }

        pathSave = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath()+
                File.separator+
                new SimpleDateFormat("yyyyMMddhhmmss_SSS").format(new Date())+".wav";


        throwcard();
        /******************************************************************************************************/

        //20번동안
        //랜덤숫자 발생
        //발생한 숫자로 DB에서 정보 읽어오기
        //백그라운드 색깔바꾸고
        //백그라운드 이미지 넣고
        //주제 Text 던져주고
        //주제 TextColor 바꿔주고
        //내용 던져주기.

        };




    View.OnClickListener onClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.audioStopbtn:
                    if (mApp.isAudio!=false){
                        mApp.mediaRecorder.stop();
                        mApp.isAudio =false;
                        ImageButton imageButton = (ImageButton)findViewById(R.id.audioStopbtn);
                        imageButton.setImageResource(R.drawable.audio_off);
                        startToast("녹음이 종료됩니다.");
//                        stopAudio();
                    }else{
                        if (checkPermissionFromDevice()) {
                            pathSave = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"
                                    + UUID.randomUUID().toString() + "_audio_record.3gp";
                            setupMediaRecorder();
                            try {
                                mApp.mediaRecorder.prepare();
                                mApp.mediaRecorder.start();
                                ImageButton imageButton = (ImageButton)findViewById(R.id.audioStopbtn);
                                imageButton.setImageResource(R.drawable.audio_on);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

//                        audioYesBtn.setEnabled(false);
//                        audioNoBtn.setEnabled(false);

                            mApp.isAudio = true;
                            startToast("녹음이 시작됩니다.");
                        }else{
                            requestPermission();
                        }
                    }
                    break;
                case R.id.closebtn:
                    closePopup();
                    break;
                case R.id.like:
                    if (count <= 20){
                        throwcard();
                    }else{
                        showPopupWhenFinished();
                    }break;
                case R.id.hate:
                    if (count <= 20){
                        throwcard();
                    }else {
                        showPopupWhenFinished();
                    }
            }
        }
    };

    private void showPopupWhenFinished() {


        final AlertDialog.Builder alert = new AlertDialog.Builder(CardActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.popup_when_finished, null);

        final Button yescontinue = (Button) mView.findViewById(R.id.yescontinue);
        final Button gotodiary = (Button) mView.findViewById(R.id.gotodiary);

        alert.setView((mView));

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        yescontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startViewAll();
            }
        });

        gotodiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDiaryList();
            }
        });

        alertDialog.show();
        alertDialog.getWindow().setLayout(850,950);


    }


    private void startDiaryList() {
        Intent intent = new Intent(this, DiaryList.class);
        startActivity(intent);
    }

    private void throwcard(){

        int randomvalue = (int)(Math.random()*130+1);
        CardObject cardObject =mApp.dBhelper.selectcard(randomvalue);

        //유저프로파일에서 아빠선택을 받아와서 만약 false 라면
        if (mApp.dad == false){
            while (cardObject.memberexception == "아빠"){
                randomvalue = (int) (Math.random() * 133 + 1);
                cardObject = mApp.dBhelper.selectcard(randomvalue);
            }

        }
//        while (mApp.dad == false && cardObject.memberexception == "아빠") {
//            ////////다른카드 다시 선택.  ->  랜덤카드
//            randomvalue = (int) (Math.random() * 33 + 30);
//            cardObject = mApp.dBhelper.selectcard(randomvalue);
//        }
        //
        Log.d(this.getClass().getName(),"버튼0 : " + mApp.btn_state[0]);
        Log.d(this.getClass().getName(),"버튼1 : " + mApp.btn_state[1]);
        Log.d(this.getClass().getName(),"버튼2 : " + mApp.btn_state[2]);
        Log.d(this.getClass().getName(),"버튼3 : " + mApp.btn_state[3]);
        Log.d(this.getClass().getName(),"버튼4 : " + mApp.btn_state[4]);
        Log.d(this.getClass().getName(),"버튼5 : " + mApp.btn_state[5]);
        Log.d(this.getClass().getName(),"버튼6 : " + mApp.btn_state[6]);
        Log.d(this.getClass().getName(),"버튼7 : " + mApp.btn_state[7]);
        Log.d(this.getClass().getName(),"버튼8 : " + mApp.btn_state[8]);

        if (cardObject.category != 9) {
            Log.d(this.getClass().getName(),"카드 카테고리 번호 : "+ cardObject.category);
            while (mApp.btn_state[cardObject.category] == false){
                randomvalue = (int) (Math.random() * 133 + 1);
                cardObject = mApp.dBhelper.selectcard(randomvalue);
                if (cardObject.category == 9){
                    break;
                }
                Log.d(this.getClass().getName(),"선택되지 않은 카드입니다.");
            }
        }

        String[] category_names = getResources().getStringArray(R.array.category_names);

        TextView categorytext = findViewById(R.id.categoryText);
        categorytext.setText("[ "+category_names[cardObject.category]+" ]");
        TextView contenttext = findViewById(R.id.question);
        contenttext.setText(cardObject.content);
//        if (cardObject.memberexception != "아빠"){
//        };


        switch (cardObject.category) {
            case 0:
                findViewById(R.id.backgroundlayouyColor).setBackgroundColor(getResources().getColor(R.color.color0));
                findViewById(R.id.cardBackground).setBackgroundResource(R.drawable.w_0);
                categorytext.setTextColor(getResources().getColor(R.color.color0));
                break;
            case 1:
                findViewById(R.id.backgroundlayouyColor).setBackgroundColor(getResources().getColor(R.color.color1));
                findViewById(R.id.cardBackground).setBackgroundResource(R.drawable.w_1);
                categorytext.setTextColor(getResources().getColor(R.color.color1));
                break;
            case 2:
                findViewById(R.id.backgroundlayouyColor).setBackgroundColor(getResources().getColor(R.color.color2));
                findViewById(R.id.cardBackground).setBackgroundResource(R.drawable.w_2);
                categorytext.setTextColor(getResources().getColor(R.color.color2));
                break;
            case 3:
                findViewById(R.id.backgroundlayouyColor).setBackgroundColor(getResources().getColor(R.color.color3));
                findViewById(R.id.cardBackground).setBackgroundResource(R.drawable.w_3);
                categorytext.setTextColor(getResources().getColor(R.color.color3));
                break;
            case 4:
                findViewById(R.id.backgroundlayouyColor).setBackgroundColor(getResources().getColor(R.color.color4));
                findViewById(R.id.cardBackground).setBackgroundResource(R.drawable.w_4);
                categorytext.setTextColor(getResources().getColor(R.color.color4));
                break;
            case 5:
                findViewById(R.id.backgroundlayouyColor).setBackgroundColor(getResources().getColor(R.color.color5));
                findViewById(R.id.cardBackground).setBackgroundResource(R.drawable.w_5);
                categorytext.setTextColor(getResources().getColor(R.color.color5));
                break;
            case 6:
                findViewById(R.id.backgroundlayouyColor).setBackgroundColor(getResources().getColor(R.color.color6));
                findViewById(R.id.cardBackground).setBackgroundResource(R.drawable.w_6);
                categorytext.setTextColor(getResources().getColor(R.color.color6));
                break;
            case 7:
                findViewById(R.id.backgroundlayouyColor).setBackgroundColor(getResources().getColor(R.color.color7));
                findViewById(R.id.cardBackground).setBackgroundResource(R.drawable.w_7);
                categorytext.setTextColor(getResources().getColor(R.color.color7));
                break;
            case 8:
                findViewById(R.id.backgroundlayouyColor).setBackgroundColor(getResources().getColor(R.color.color8));
                findViewById(R.id.cardBackground).setBackgroundResource(R.drawable.w_8);
                categorytext.setTextColor(getResources().getColor(R.color.color8));
                break;
            case 9:
                findViewById(R.id.cardBackground).setBackgroundColor(getResources().getColor(R.color.color9));
                findViewById(R.id.cardBackground).setBackgroundResource(R.drawable.w_9);
                categorytext.setTextColor(getResources().getColor(R.color.color9));
                break;
        }
        count ++;
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




    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void closePopup() {

        final AlertDialog.Builder alert = new AlertDialog.Builder(CardActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.popup_close,null);

        final Button yesclosebtn = (Button)mView.findViewById(R.id.YesCloseBtn);
        final Button noclosebtn = (Button)mView.findViewById(R.id.NoCloseBtn);

        alert.setView((mView));

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        yesclosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startToast("앱이 종료됩니다.");
                alertDialog.dismiss();
                finish();
            }
        });

        noclosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


        alertDialog.show();
        alertDialog.getWindow().setLayout(800,600);
    }

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

    private void startViewAll() {
        Intent intent = new Intent(this, ViewAll.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onActionModeStarted(ActionMode mode) {
        super.onActionModeStarted(mode);
        actionMode = mode;
    }

    //좋아요/ 싫어요 버튼에 글씨 넣기
    //좋아요/ 싫어요 버튼 누르면 다음화면으로 가기


}


