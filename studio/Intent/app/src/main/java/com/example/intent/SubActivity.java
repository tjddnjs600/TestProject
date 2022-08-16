package com.example.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity implements View.OnClickListener{

    EditText inputEt;
    TextView centerTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        centerTv = findViewById(R.id.centerTv);
        inputEt = findViewById(R.id.inputEt);
        findViewById(R.id.btn).setOnClickListener(this);

//        Intent getIntent = getIntent();
//        String name = getIntent.getStringExtra("name");
//        int age = getIntent.getIntExtra("age",-1);
//
//        centerTv.setText("name : "+name +"\n" + "age : " + age);

        Intent data = getIntent();
        String dataStr = data.getStringExtra("name");
        centerTv.setText(dataStr);

    }

    @Override
    public void onClick(View v) {
        String str = inputEt.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("num", str);
        setResult(10, intent);
        finish(); //현재 잇는 엑티비티를 종료한다
//        Intent intent = new Intent(this, com.example.intent.MainActivity.class);
//        startActivity(intent);
    }
}
