package com.example.moment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivityRecyclerView extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<MainModelRecyclerView> mainModelRecyclerViews;
    MainAdapterRecyclerView mainAdapterRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_card_category2);

        recyclerView = findViewById(R.id.recyclerView);

        Integer[] langLogo = {R.drawable.cate_0,R.drawable.cate_1,R.drawable.cate_2,R.drawable.cate_3,R.drawable.cate_4,R.drawable.cate_5,R.drawable.cate_6,R.drawable.cate_7,R.drawable.cate_8};

        mainModelRecyclerViews = new ArrayList<>();
        for (int i=0; i<langLogo.length;i++){
            MainModelRecyclerView model = new MainModelRecyclerView(langLogo[i]);
            mainModelRecyclerViews.add(model);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                MainActivityRecyclerView.this, LinearLayoutManager.HORIZONTAL, false
        );
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mainAdapterRecyclerView = new MainAdapterRecyclerView(MainActivityRecyclerView.this, mainModelRecyclerViews);

        recyclerView.setAdapter(mainAdapterRecyclerView);

        findViewById(R.id.startButton).setOnClickListener(onClickListener);
        findViewById(R.id.settingButton).setOnClickListener(onClickListener);
        findViewById(R.id.diaryButton).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){


        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.startButton:
                    startViewAll();
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

    private void startViewAll() {
        Intent intent = new Intent(this, ViewAll.class);
        startActivity(intent);
    }

    private void startDiaryList() {
        Intent intent = new Intent(this, DiaryList.class);
        startActivity(intent);
    }


}
