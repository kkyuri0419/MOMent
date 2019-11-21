package com.example.moment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivityTutorial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tutorial);

        findViewById(R.id.startCardButton).setOnClickListener(onClickListener);
        findViewById(R.id.tutorialclosebtn).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.startCardButton:
//                    startToast("튜토리얼이 시작됩니다");
                    startUserProfileActivity();
                    break;
                case R.id.tutorialclosebtn:
//                    startRecyclerView();
                    startUserProfileActivity();
                    break;

            }
        }
    };

        private void startUserProfileActivity() {
            Intent intent = new Intent(this, UserProfileActivity.class);
            startActivity(intent);
        }
        private void startRecyclerView() {
            Intent intent = new Intent(this, MainActivityRecyclerView.class);
            startActivity(intent);
        }

        private void startToast(String msg) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }

//    public void btn_showPopup (View view){
//
//        final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivityTutorial.this);
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

}