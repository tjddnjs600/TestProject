package com.example.intentnew;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StaticArea.engArr.add("ㅇㅇㅇ");

        findViewById(R.id.btn).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         String str = data.getStringExtra("userID");
        Log.d("heu","request: "+requestCode + "  result: "+resultCode+"  id: "+str);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, com.example.intentnew.SubActivity.class);
        intent.putExtra("name","개똥이");
        intent.putExtra("age",8000);
//        startActivity(intent);
        startActivityForResult(intent, 2000);
    }
}
