package com.example.moment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.moment.R;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class BigCardAdapter extends PagerAdapter {

    private Integer[] images = {R.drawable.cate_0,R.drawable.cate_1,R.drawable.cate_2,R.drawable.cate_3,R.drawable.cate_4,R.drawable.cate_5,R.drawable.cate_6,R.drawable.cate_7,R.drawable.cate_8};
    private LayoutInflater layoutInflater;
    private Context context;

    public BigCardAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.old_fragment_big_card,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(images[position]);

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}
