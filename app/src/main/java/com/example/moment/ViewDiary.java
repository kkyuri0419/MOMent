package com.example.moment;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moment.helper.DBhelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

public class ViewDiary extends AppCompatActivity {
    ArrayList<DiaryObject> diaryObjects;
    //    private static final String TAG = ;
    private TextView mDisplayDate;

    private DatePickerDialog.OnDateSetListener mDateSetListner;
    private ImageView selecdatebtn;
    private MomentApplication mApp;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;
    private Boolean isCamera = false;
    private TextView dateText;
    private TextView textcontent;
    private EditText editText;
    private ImageView add_image_btn;
    private ImageView editdiarybtn;
    private File tempFile;
    private String imagepath;
    private  ImageView deletediarybtn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_view);
        mApp = (MomentApplication) getApplication();
        ArrayList<DiaryObject> diaryObjects = new ArrayList<>();

        diaryObjects = mApp.dBhelper.selectdiary();

        mDisplayDate = (TextView) findViewById(R.id.dateText);
        selecdatebtn = (ImageView) findViewById(R.id.selectdatebtn);


        final Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        findViewById(R.id.add_image_btn).setOnClickListener(onClickListener);
        dateText = (TextView) findViewById(R.id.dateText);
        add_image_btn = (ImageView) findViewById(R.id.add_image_btn);
        textcontent = (TextView) findViewById(R.id.textcontent);
        editdiarybtn = (ImageView)findViewById(R.id.editbtn);
        deletediarybtn = (ImageView)findViewById(R.id.deletebtn);




        Log.d(this.getClass().getName(),"다이어리 인덱스 : "+mApp.selectedDiaryIndex);
        Log.d(this.getClass().getName(),"다이어리 타이틀: "+mApp.selecteddiary.d_title);
        Log.d(this.getClass().getName(),"다이어리 내용: "+mApp.selecteddiary.d_content);
        Log.d(this.getClass().getName(),"다이어리 인덱스: "+mApp.selecteddiary.d_index);

//        dateText.setText(diaryObjects.get(mApp.selectedDiaryIndex).d_title);
//        textcontent.setText(diaryObjects.get(mApp.selectedDiaryIndex).d_content);

        dateText.setText(mApp.selecteddiary.d_title);
        textcontent.setText(mApp.selecteddiary.d_content);

        File imgFile = new  File(mApp.selecteddiary.d_photo);
        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        add_image_btn.setImageBitmap(myBitmap);



//        mDisplayDate.setText(currentDate);

        selecdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ViewDiary.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListner, year, month, day);
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
            switch (v.getId()) {
                case R.id.editbtn:
                    break;
                case R.id.deletebtn:
                    deleteDiary(mApp.selecteddiary.d_index);
                    break;
            }
        }
    };

    private void deleteDiary(int d_index) {
//        DiaryObject diaryObject = new DiaryObject();
        mApp.dBhelper.deletediary(d_index);
    }
}
