package com.example.moment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
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

        mApp = (MomentApplication) getApplication();
        ArrayList<DiaryObject> diaryObjects = new ArrayList<>();
        maddDiarybtn = (ImageView) findViewById(R.id.add_diary_btn);
        listView = (ListView) findViewById(R.id.diaryList);

        Log.d(this.getClass().getName(),"어댑터 전000000000000000000000000");
        diaryObjects = mApp.dBhelper.selectdiary();
        DiaryAdapter diaryAdapter = new DiaryAdapter(this,diaryObjects);
        listView.setAdapter(diaryAdapter);

        Log.d(this.getClass().getName(),"어댑터 후000000000000000000000000");
        Log.d(this.getClass().getName(),"어댑터 후000000000000000000000000");

        diaryAdapter.notifyDataSetChanged();


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

    }

    public ListView getListView() {
        return listView;
    }

    private void startDiaryMakeActivity() {
        Intent intent = new Intent(this, DiaryMakeNew.class);
        startActivity(intent);
    }
}
