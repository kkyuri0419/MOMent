package com.example.moment.adapter;

import android.content.Context;

import com.example.moment.R;
import com.example.moment.fragments.CategoryFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
//Context는 디바이스의 상태값.

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Context mcontext;
    public ViewPagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mcontext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        CategoryFragment categoryFragment = new CategoryFragment();
        categoryFragment.position = position;
        return categoryFragment;
    }

    @Override
    public int getCount() {
        return mcontext.getResources().getStringArray(R.array.category_names).length;
    }



}
