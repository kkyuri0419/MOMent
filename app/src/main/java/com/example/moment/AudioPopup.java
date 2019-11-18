package com.example.moment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class AudioPopup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_audio_record);

    }

    public void btn_showPopup (View view){

        final AlertDialog.Builder alert = new AlertDialog.Builder(AudioPopup.this);
        View mView = getLayoutInflater().inflate(R.layout.popup_audio_record,null);

        Button audioYesBtn = (Button)mView.findViewById(R.id.audioYesBtn);
        Button audioNoBtn = (Button)mView.findViewById(R.id.audioNoBtn);

        alert.setView((mView));

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        audioYesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //오디오 기능 ON
                //카드화면으로 바로가기
                alertDialog.dismiss();

            }
        });

        audioNoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //카드화면으로 바로가기
                alertDialog.dismiss();
            }
        });

    }

}