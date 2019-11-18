package com.example.moment.fragments;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.moment.CardActivity;
import com.example.moment.CategoryActivity;
import com.example.moment.R;
import com.example.moment.ViewAllActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
//화면과 데이터를 설정
public class CardFragment extends Fragment {
    public int chosen_category;
    @Nullable
    @Override
    //inflater는 xml을 화면으로 만들어주는거다.
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_card, null);

        View view1 = view.findViewById(R.id.view);

        Drawable backgound_color = getResources().getDrawable(R.drawable.round);

        int color_id = getResources().getIdentifier("color"+chosen_category, "color", getActivity().getPackageName());//color를 position별로 가져옴
        backgound_color.setColorFilter(getResources().getColor(color_id), PorterDuff.Mode.MULTIPLY);// color값을 colorFilter에 설정해줌
        view1.setBackground(backgound_color); //backgound에 색상을 넣어줌


//        public ViewAllActivity getViewAllActivity(boolean isAudio){
//            ViewAllActivity viewAllActivity = new ViewAllActivity();
//            viewAllActivity.isAudio = isAudio;
//            return viewAllActivity;
//        }
//
//
//
//
//        if (!isAudio){
//            audioStopbtn.setImageResource(R.drawable.audio_off);
//        }else{
//            audioStopbtn.setImageResource(R.drawable.audio_on);
//        }
//        TextView desc = view.findViewById(R.id.desc);

        String[] category_names = getResources().getStringArray(R.array.category_names);
//        String[] category_desc = getResources().getStringArray(R.array.category_desc);

//        category.setText("[ " + category_names[chosen_category] + "]");
//        desc.setText(category_desc[position]);


        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        TextView category = view.findViewById(R.id.categoryText);
        ImageButton audioStopbtn = view.findViewById(R.id.audioStopbtn);


        CardActivity cardActivity = (CardActivity) getActivity();
        return  view;
    }
}
