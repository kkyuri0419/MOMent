package com.example.moment.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.moment.CardObject;
import com.example.moment.DiaryObject;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {

    public DBhelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE card (" +
                    "c_index	INTEGER," +
                    "content	TEXT," +
                    "category	INTEGER," +
                    "memberexception	TEXT," +
                    "PRIMARY KEY(c_index))");
        }catch (android.database.sqlite.SQLiteException e){
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public CardObject selectcard (int idx){
        Log.d(this.getClass().getName(),"카드가 선택됨");
        Cursor cursor=getReadableDatabase().query("card", null, "c_index=?", new String[]{String.valueOf(idx)},null,null,null);
        Log.d(this.getClass().getName(),"인덱스 : " + idx);
        if (cursor.moveToNext()) {
            Log.d(this.getClass().getName(),"카드 인덱스 : ");
            CardObject cardObject= new CardObject();
            cardObject.c_index = cursor.getInt(cursor.getColumnIndex("c_index"));
            cardObject.category = cursor.getInt(cursor.getColumnIndex("category"));
            cardObject.content = cursor.getString(cursor.getColumnIndex("content"));
            cardObject.memberexception = cursor.getString(cursor.getColumnIndex("memberexception"));
            Log.d(this.getClass().getName(),"카드 인덱스 : " + cardObject.c_index);
            return  cardObject;
        }
        return  null;
    }
    public ArrayList<DiaryObject> selectdiary (){
        Log.d(this.getClass().getName(),"ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ줄1");
        Cursor cursor=getReadableDatabase().query("diary", null, null, null,null,null,null);
        Log.d(this.getClass().getName(),"ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ줄2");
        ArrayList<DiaryObject> diaryObjects = new ArrayList<>();

        while (cursor.moveToNext()) {
//            Log.d(this.getClass().getName(),"줄4");
//            여러개를 불러올땐 while문을 써서 arraylist에 add해서 리턴/
            DiaryObject diaryObject= new DiaryObject();
            diaryObject.d_index = cursor.getInt(cursor.getColumnIndex("d_index"));
            diaryObject.d_content = cursor.getString(cursor.getColumnIndex("d_content"));
            diaryObject.d_title = cursor.getString(cursor.getColumnIndex("d_title"));
            diaryObject.d_audio = cursor.getString(cursor.getColumnIndex("d_audio"));
            diaryObject.d_photo = cursor.getString(cursor.getColumnIndex("d_photo"));
            diaryObjects.add(diaryObject);
        }
        return diaryObjects;
    }

    public long insertdiary(DiaryObject diaryObject){
        ContentValues contentValues = new ContentValues();
        contentValues.put("d_title",diaryObject.d_title);
        contentValues.put("d_content",diaryObject.d_content);
        contentValues.put("d_photo",diaryObject.d_photo);
        contentValues.put("d_audio",diaryObject.d_audio);

        long result = getWritableDatabase().insert("diary",null,contentValues);
        Log.d(this.getClass().getName(),"로그 RESULT = "+result);
        return  result;
    }

    public long updatediary(DiaryObject diaryObject){
        ContentValues contentValues = new ContentValues();
        contentValues.put("d_title",diaryObject.d_title);
        contentValues.put("d_content",diaryObject.d_content);
        contentValues.put("d_photo",diaryObject.d_photo);
        contentValues.put("d_audio",diaryObject.d_audio);
        long result = getWritableDatabase().update("diary",contentValues, "d_index=?", new String[]{String.valueOf(diaryObject.d_index)});
        return  result;
    }

    public long deletediary(int d_index){
        long result = getWritableDatabase().delete("diary", "d_index=?", new String[]{String.valueOf(d_index)});
        return  result;
    }

}
