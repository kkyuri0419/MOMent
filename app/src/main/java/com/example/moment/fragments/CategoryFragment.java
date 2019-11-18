package com.example.moment.fragments;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moment.CategoryActivity;
import com.example.moment.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
//화면과 데이터를 설정
public class CategoryFragment extends Fragment {
    public int position;
    @Nullable
    @Override
    //inflater는 xml을 화면으로 만들어주는거다.
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_category, null);

        View rootView=view.findViewById(R.id.rootview);

        Drawable backgound_color = getResources().getDrawable(R.drawable.round);

        int color_id = getResources().getIdentifier("color"+position, "color", getActivity().getPackageName());//color를 position별로 가져옴
        backgound_color.setColorFilter(getResources().getColor(color_id), PorterDuff.Mode.MULTIPLY);// color값을 colorFilter에 설정해줌
        rootView.setBackground(backgound_color); //backgound에 색상을 넣어줌

        TextView title = view.findViewById(R.id.title);
        TextView desc = view.findViewById(R.id.desc);

        String[] category_names = getResources().getStringArray(R.array.category_names);
        String[] category_desc = getResources().getStringArray(R.array.category_desc);

        title.setText(category_names[position]);
        desc.setText(category_desc[position]);


        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        CategoryActivity categoryActivity = (CategoryActivity) getActivity();
        return  view;
    }
}
