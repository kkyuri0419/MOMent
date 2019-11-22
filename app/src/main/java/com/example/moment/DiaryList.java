package com.example.moment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moment.adapter.DiaryAdapter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class DiaryList extends AppCompatActivity {

    LayoutInflater mLayoutInflater = null;
    ListView listView;
    private MomentApplication mApp;
    private ImageView maddDiarybtn;
    ArrayList<DiaryObject> diaryObjects;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_list);

        maddDiarybtn = (ImageView) findViewById(R.id.add_diary_btn);
        listView = (ListView) findViewById(R.id.diaryList);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DiaryList.this, "You have clicked on", Toast.LENGTH_SHORT).show();
            }
        });

        maddDiarybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDiaryMakeActivity();
            }
        });

        DiaryAdapter diaryAdapter = new DiaryAdapter(this,diaryObjects);
    }

//        View view = mLayoutInflater.inflate(R.layout.format_listview,null);
//
//        ImageView listviewImage = (ImageView)view.findViewById(R.id.listviewImage);
//        ImageView listviewAuidioImage = (ImageView)view.findViewById(R.id.audiobtn);
//        TextView listviewTitle = (TextView)view.findViewById(R.id.listviewTitle);
//        TextView listViewContent = (TextView)view.findViewById(R.id.listviewContent);
//
//
//
//        ArrayList<DiaryObject> diaryObject = mApp.dBhelper.selectdiary();
//
//
//        for (int i = 0; i < diaryObject.size(); i++){
//            listviewTitle.setText(diaryObject.get(i).d_title);
//            listViewContent.setText(diaryObject.get(i).d_content);
//            File imgFile = new  File(diaryObject.get(i).d_photo);
//            if (imgFile.exists()){
//                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                listviewImage.setImageBitmap(myBitmap);
//            }
//            File audioFile = new File(diaryObject.get(i).d_audio);
//            if (audioFile != null){
//                listviewAuidioImage.setImageResource(R.drawable.audio_on);
//            }else{
//                listviewAuidioImage.setImageResource(R.drawable.audio_off);
//            }
//
//        }


    public ListView getListView() {
        return listView;
    }

    private void startDiaryMakeActivity() {
        Intent intent = new Intent(this, DiaryMakeNew.class);
        startActivity(intent);
    }
}
