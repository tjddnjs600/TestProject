package com.example.voca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initDb();

        findViewById(R.id.addBt).setOnClickListener(this);
        findViewById(R.id.studyBt).setOnClickListener(this);
        findViewById(R.id.quitBt).setOnClickListener(this);

    }

    //db에서 데이터 가져와서 Storage에 넣기
    private void initDb() {
        SQLiteDatabase db = null;
        if (db == null) {
            db = openOrCreateDatabase("sqlist_test.db", Context.MODE_PRIVATE, null);
        }
        db.execSQL("CREATE TABLE IF NOT EXISTS voca("
                + "idx INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "eng TEXT,"
                + "kor TEXT" + ");");

        //데이터 가져오기
        Cursor c = db.rawQuery("SELECT * FROM voca", null);

        c.moveToFirst();
        String str = "";
        Storage.vocaArr.clear();
        while (c.isAfterLast() == false) {
            int idx = c.getInt(0);
            String eng = c.getString(1);
            String kor = c.getString(2);
            Storage.vocaArr.add(new Voca(idx, eng, kor));
            c.moveToNext();
        }
        c.close();
        db.close();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addBt:
                startActivity(new Intent(this, com.example.voca.MainActivity.class));
                break;
            case R.id.studyBt:
                startActivity(new Intent(this, com.example.voca.VocaStudyActivity.class));
                break;
            case R.id.quitBt:
                finish();
                break;

        }
    }
}

