package com.example.moment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.moment.adapter.ViewPagerAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_activity_category);

        ViewPager viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(viewPagerAdapter);

        findViewById(R.id.startSelect).setOnClickListener(onClickListener);
        findViewById(R.id.settingButton).setOnClickListener(onClickListener);
        findViewById(R.id.diaryButton).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){


        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.startSelect:
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
        Intent intent = new Intent(this, ViewAllActivity.class);
        startActivity(intent);
    }

    private void startDiaryList() {
        Intent intent = new Intent(this, DiaryList.class);
        startActivity(intent);
    }
}