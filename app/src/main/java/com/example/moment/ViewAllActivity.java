package com.example.moment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.gridlayout.widget.GridLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.view.View;
import android.os.Bundle;
//import android.widget.GridLayout;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class ViewAllActivity extends AppCompatActivity {

    boolean[] btn_state = {false,false,false,false,false,false,false,false};
    GridLayout mainGrid;
    public String pathSave = "";
    public MediaRecorder mediaRecorder;
//    public MediaPlayer mediaPlayer;
    public boolean isAudio = false;

    final int REQUEST_PERMISSION_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        mainGrid = (GridLayout) findViewById(R.id.mainGrid);


        findViewById(R.id.startButton).setOnClickListener(onClickListener);
        findViewById(R.id.settingButton).setOnClickListener(onClickListener);
        findViewById(R.id.diaryButton).setOnClickListener(onClickListener);

        //set Event
        setToggleEvent(mainGrid);

    }

    private void setToggleEvent(GridLayout mainGrid) { //Loop all child item of mainGrid
        for(int i = 0; i<mainGrid.getChildCount(); i++){
            final CardView cardView = (CardView)mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if(cardView.getCardBackgroundColor().getDefaultColor() != -1){
                    if (btn_state[finalI] == false){
                        cardView.setCardBackgroundColor(Color.parseColor("#FFAFADAD")); //눌러져있지 않은 상태이면 회색으로 바꾼다.
                        btn_state[finalI] = true;
//                        Toast.makeText(MainActivity.this, "selected" , Toast.LENGTH_SHORT).show();
                    }else{
//                        Toast.makeText(ViewAllActivity.this, "color_reset", Toast.LENGTH_SHORT).show();
                        btn_state[finalI] = false;
                        switch (finalI){
                            case 0:
                                cardView.setCardBackgroundColor(Color.parseColor("#2E4E57"));
                                break;
                            case 1:
                                cardView.setCardBackgroundColor(Color.parseColor("#CBF3F2"));
                                break;
                            case 2:
                                cardView.setCardBackgroundColor(Color.parseColor("FCA816"));
                                break;
                            case 3:
                                cardView.setCardBackgroundColor(Color.parseColor("#3980B6"));
                                break;
                            case 4:
                                cardView.setCardBackgroundColor(Color.parseColor("#ECCFD1"));
                                break;
                            case 5:
                                cardView.setCardBackgroundColor(Color.parseColor("#37155F"));
                                break;
                            case 6:
                                cardView.setCardBackgroundColor(Color.parseColor("#E4837C"));
                                break;
                            case 7:
                                cardView.setCardBackgroundColor(Color.parseColor("#9BBDBA"));
                                break;
                            case 8:
                                cardView.setCardBackgroundColor(Color.parseColor("#FBEA90"));
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
        final AlertDialog.Builder alert = new AlertDialog.Builder(ViewAllActivity.this);
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
                            mediaRecorder.prepare();
                            mediaRecorder.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

//                        audioYesBtn.setEnabled(false);
//                        audioNoBtn.setEnabled(false);

                        isAudio = true;
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
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(pathSave);
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






//    public void btn_showPopup (View view){
//
//        final AlertDialog.Builder alert = new AlertDialog.Builder(ViewAllActivity.this);
//        View mView = getLayoutInflater().inflate(R.layout.popup_audio_record,null);
//
//        Button audioYesBtn = (Button)mView.findViewById(R.id.audioYesBtn);
//        Button audioNoBtn = (Button)mView.findViewById(R.id.audioNoBtn);
//
//        alert.setView((mView));
//
//        final AlertDialog alertDialog = alert.create();
//        alertDialog.setCanceledOnTouchOutside(false);
//
//        audioYesBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //오디오 기능 ON
//                //카드화면으로 바로가기
//                alertDialog.dismiss();
//
//            }
//        });
//
//        audioNoBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //카드화면으로 바로가기
//                alertDialog.dismiss();
//            }
//        });
//
//        alertDialog.show();
//
//
//    }
//
}