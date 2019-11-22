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
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

public class DiaryMakeNew extends AppCompatActivity {

//    private static final String TAG = ;
    private TextView mDisplayDate;

    private DatePickerDialog.OnDateSetListener mDateSetListner;
    private ImageView selecdatebtn;
    private MomentApplication mApp;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;
    private Boolean isCamera = false;
    private TextView dateText;
    private EditText editText;
    private ImageView add_image_btn;
    private File tempFile;
//    private String imagepath;



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
        dateText = (TextView)findViewById(R.id.dateText);
        add_image_btn = (ImageView)findViewById(R.id.add_image_btn);
        editText = (EditText)findViewById(R.id.editText);
        mApp = (MomentApplication) getApplication();


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
                    savediary();
                    break;
                case R.id.add_image_btn:
                    ShowSlectImagePopup();
                    break;
            }
        }
    };


    //  인덱스 랜덤으로 만들어서 넣어주면 카드오브ㅈ
//        CardObject cardObject=mApp.dBhelper.selectcard(0);
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


    private void savediary(){
        DiaryObject diaryObject = new DiaryObject();

        diaryObject.d_title = dateText.getText().toString();
        diaryObject.d_content = editText.getText().toString();
        diaryObject.d_photo = mApp.imagepath;


//        diaryObject.d_audio = findViewById(R.id.dateText).toString();

        Log.d(this.getClass().getName(),"로그 : "+ mApp.dBhelper);


        long result = mApp.dBhelper.insertdiary(diaryObject);
        if(result >0){
            //정상 다음화면
            goToDiaryList();
            startToast("기록이 저장되었습니다");
        }else{
            startToast("저장불가능");
            //문제가 있음을 사용자에게 toast
        }
        mApp.imagepath = "";

    }

    private void setImage() {

        ImageView imageView = findViewById(R.id.add_image_btn);

        ImageResizeUtils.resizeFile(tempFile,tempFile,1280);

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);

        imageView.setImageBitmap(originalBm);

    }


    private void goToDiaryList() {
        Intent intent = new Intent(this, DiaryList.class);
        startActivity(intent);
    }

    private void ShowSlectImagePopup() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                Log.d(TAG, "권한 설정 완료");
            } else {
//                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(DiaryMakeNew.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }


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
                takePhoto();
            }
        });

        picbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //갤러리에서 사진 선택
                takePhotoFromGallery();
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
        alertDialog.getWindow().setLayout(900,650);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {

            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();

            if(tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
//                        Log.e(TAG, tempFile.getAbsolutePath() + " 삭제 성공");
                        Log.d(this.getClass().getName(),this.tempFile.getAbsolutePath() + " 삭제 성공");
                        tempFile = null;
                    }
                }
            }

            return;
        }

        if (requestCode == PICK_FROM_ALBUM) {
            Uri photoUri = data.getData();
            Cursor cursor = null;

            try {
                //Uri 스키마를 content:///에서 file:///로 변경한다.

                String[] proj = {MediaStore.Images.Media.DATA};

                assert photoUri != null;
                cursor = getContentResolver().query(photoUri, proj, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                if(cursor.getString(column_index) != mApp.imageNull || cursor.getString(column_index) != null) {
                    Log.d(this.getClass().getName(),"사진이 저장됨");
                    mApp.imagepath = cursor.getString(column_index);
                }else{
                    Log.d(this.getClass().getName(),"사진이 저장되지 않음");
                    mApp.imagepath = String.valueOf(R.drawable.like_ala);
                }

                Log.d("aaa", cursor.getString(column_index));

                tempFile = new File(cursor.getString(column_index));



            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

            setImage();

        } else if (requestCode == PICK_FROM_CAMERA) {
            setImage();
        }

    }



    //갤러리에서 사진 불러오기
    private void takePhotoFromGallery() {

        isCamera = false;

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//        intent.setType("image/*");
        startActivityForResult(intent, PICK_FROM_ALBUM);

    }

    private void takePhoto() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        isCamera = true;

        try {
            tempFile = createImageFile();
        } catch (IOException e) {
            Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            finish();
            e.printStackTrace();
        }
        if (tempFile != null) {

            Uri photoUri = Uri.fromFile(tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, PICK_FROM_CAMERA);
        }
    }

    private File createImageFile() throws IOException {

        // 이미지 파일 이름 ( blackJin_{시간}_ )
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "blackJin_" + timeStamp + "_";

        // 이미지가 저장될 폴더 이름 ( blackJin )
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/blackJin/");
        if (!storageDir.exists()) storageDir.mkdirs();

        // 빈 파일 생성
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        return image;
    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }
}
