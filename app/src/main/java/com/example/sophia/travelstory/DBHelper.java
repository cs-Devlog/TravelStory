package com.example.sophia.travelstory;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sophia on 2017. 5. 20..
 */

public class DBHelper extends SQLiteOpenHelper {
    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USER (_id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, passwd TEXT);");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String email, String passwd) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO USER VALUES(null, '" + email + "', '" + passwd + "');");
        db.close();
    }

    public void delete() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM USER;");
        db.close();
    }


    public String getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM USER", null);
        while (cursor.moveToNext()) {
            result += cursor.getString(0)
                    + " : "
                    + cursor.getString(1)
                    + " 이메일 "
                    + cursor.getString(2)
                    + "비밀번호 \n";
        }
        return result;
    }

    public boolean accessLogin(String email, String passwd) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM USER", null);
        String curName, curPwd;
        while (cursor.moveToNext()) {
            curName = cursor.getString(1);
            curPwd = cursor.getString(2);
            if (curName.equals(email) & curPwd.equals(passwd))
                return true;
        }
        return false;
    }
}