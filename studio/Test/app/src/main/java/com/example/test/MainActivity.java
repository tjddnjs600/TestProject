package com.example.test;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(this);
    }

    int select = 0;
    @Override
    public void onClick(View v) {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        final String[] items = {"감자","고구마","배추","부추","고추","상추"};
        ab.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("ch","ch : "+which);
                select = which;
            }
        });
        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("ch2","선택한 아이템 : "+ items[select]);
            }
        });
        ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });



//        ab.setIcon(R.mipmap.ic_launcher);
//        ab.setTitle("제목");
//        ab.setMessage("내용이 들어갑니다.");
//        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                dialog.dismiss();
//            }
//        });
//        ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();//메세지창을 닫아준다.
//            }
//        });
//        ab.setCancelable(false);//버튼으로만 종료할 수 있게 한다.(기본값 true <- 어딜눌러도 종료됨)

        ab.show();
    }
}
