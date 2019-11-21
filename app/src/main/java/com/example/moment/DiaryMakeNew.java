package com.example.moment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DiaryMakeNew extends AppCompatActivity {

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListner;
    private ImageView selecdatebtn;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_write_new);
        mDisplayDate = (TextView) findViewById(R.id.dateText);
        selecdatebtn = (ImageView) findViewById(R.id.selectdatebtn);

        final Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        findViewById(R.id.savebtn).setOnClickListener(onClickListener);
        findViewById(R.id.add_image_btn).setOnClickListener(onClickListener);

        mDisplayDate.setText(currentDate);
        
        selecdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        DiaryMakeNew.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListner,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });


        mDateSetListner = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                String date = month + "/" + dayOfMonth + "/" + year;
                mDisplayDate.setText(date);

            }
        };
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.savebtn:
                    break;
                case R.id.add_image_btn:
                    ShowSlectImagePopup();
                    break;
            }
        }
    };


    private void ShowSlectImagePopup() {


        final AlertDialog.Builder alert = new AlertDialog.Builder(DiaryMakeNew.this);
        View mView = getLayoutInflater().inflate(R.layout.popup_camera, null);

        final ImageView camerabtn = (ImageView) mView.findViewById(R.id.camerabtn);
        final ImageView picbtn = (ImageView) mView.findViewById(R.id.picbtn);

        alert.setView((mView));

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(true);

        camerabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //카메라가 실행된다
                startToast("카메라가 실행됩니다.");
            }
        });

        picbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //갤러리에서 사진 선택
                startToast("갤러리가 보여집니다.");
            }
        });

        alertDialog.show();
        alertDialog.getWindow().setLayout(800,500);
    }


    //갤러리에서 사진 불러오기
    private void takePhotoFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setType("image/*");
//        startActivityForResult(intent, PICK_IMAGE);
    }


    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
