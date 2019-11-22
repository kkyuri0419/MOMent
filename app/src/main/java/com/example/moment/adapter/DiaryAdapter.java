package com.example.moment.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moment.DiaryObject;
import com.example.moment.MomentApplication;
import com.example.moment.R;

import java.io.File;
import java.util.ArrayList;

public class DiaryAdapter extends BaseAdapter {

    private MomentApplication mApp;
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<DiaryObject> diaryObjects;

    public DiaryAdapter(Context mContext, ArrayList<DiaryObject> data) {
        this.mContext = mContext;
        this.diaryObjects = data;
        this.mLayoutInflater = mLayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return diaryObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.format_listview,null);

        ImageView listviewImage = (ImageView)view.findViewById(R.id.listviewImage);
        ImageView listviewAuidioImage = (ImageView)view.findViewById(R.id.audiobtn);
        TextView listviewTitle = (TextView)view.findViewById(R.id.listviewTitle);
        EditText listviewContent = (EditText)view.findViewById(R.id.editText);
//        TextView listViewContent = (TextView)view.findViewById(R.id.listviewContent);


        listviewTitle.setText(diaryObjects.get(position).d_title);
        listviewContent.setText(diaryObjects.get(position).d_content)
        ;
        File imgFile = new  File(diaryObjects.get(position).d_photo);
        if (imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            listviewImage.setImageBitmap(myBitmap);
        }
        File audioFile = new File(diaryObjects.get(position).d_audio);
        if (audioFile != null){
            listviewAuidioImage.setImageResource(R.drawable.audio_on);
        }else{
            listviewAuidioImage.setImageResource(R.drawable.audio_off);
        }

        return view;
    }
}
