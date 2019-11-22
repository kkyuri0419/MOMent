package com.example.moment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.gridlayout.widget.GridLayout;

public class UserProfileActivity extends AppCompatActivity {
    GridLayout mainGrid;
    boolean[] btn_state2 = {false,false,false,false};
    private MomentApplication mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile2);
        mainGrid = (GridLayout) findViewById(R.id.mainGrid1);

        findViewById(R.id.startCardButton2).setOnClickListener(onClickListener);
        setSelectedEvent(mainGrid);
    }

    private void setSelectedEvent(GridLayout mainGrid) {
        for (int i=0; i<mainGrid.getChildCount(); i++){
            final int finalI = i;
            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btn_state2[finalI] == false){
                        cardView.setCardBackgroundColor(Color.parseColor("#E4837C"));
                        btn_state2[finalI] =true;
                    }else{
                        cardView.setCardBackgroundColor(Color.parseColor("#E2DEDD"));
                        btn_state2[finalI] =false;
                    }
                }
            });

        }
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.startCardButton2:
//                    for(int i=0; i < btn_state2.length ; i++){
//                        if (btn_state2[3] == false){
//                            startToast("가족구성원을 선택하지 않으면 모든 구성원이 포함됩니다.");
//                        }
//                    }
                    startRecyclerView();
            }
        }
    };

    private void startRecyclerView() {
        Intent intent = new Intent(this, MainActivityRecyclerView.class);
        startActivity(intent);
    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

//    private void startCategoryActivity(){
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//    }



}