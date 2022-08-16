package com.example.word;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.addBt).setOnClickListener(this);
        findViewById(R.id.studyBt).setOnClickListener(this);
        findViewById(R.id.quitBt).setOnClickListener(this);

        initDb();
    }

    private void initDb() {
        SQLiteDatabase db = null;
        if (db == null) {
            db = openOrCreateDatabase("sqlist_test.db", Context.MODE_PRIVATE, null);
        }
        db.execSQL("CREATE TABLE IF NOT EXISTS word("
                + "idx INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "eng TEXT,"
                + "kor TEXT" + ");");

        //데이터 가져오기
        Cursor c = db.rawQuery("SELECT * FROM word", null);

        c.moveToFirst();
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
        switch (v.getId()){
            case R.id.addBt:
                startActivity(new Intent(this, com.example.word.SaveActivity.class));
                break;
            case R.id.studyBt:
                startActivity(new Intent(this, com.example.word.StudyActivity.class));
                break;
            case R.id.quitBt:
                finish();
                break;
        }
    }
}
