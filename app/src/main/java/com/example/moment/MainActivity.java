package com.example.moment;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.moment.adapter.Adapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_base);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        Adapter adapter = new Adapter(this);

        viewPager.setAdapter(adapter);

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
}
