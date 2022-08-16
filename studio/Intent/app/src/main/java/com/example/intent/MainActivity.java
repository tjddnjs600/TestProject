package com.example.intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tv;
    EditText inputEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(this);
        tv = findViewById(R.id.tv);
        inputEt = findViewById(R.id.inputEt);
    }

    final int SUB_ACTIVITY = 200;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SUB_ACTIVITY){
            String str = data.getStringExtra("num");
            tv.setText(str);
        }
//        Log.d("kkeee","request : "+requestCode+"result :"+resultCode+" ID"+str);
    }



    @Override
    public void onClick(View v) {
//        Intent intent = new Intent(this, com.example.intent.SubActivity.class);
//
//        intent.putExtra("name","개똥이"); //다른 엑티비티로 값을 던질 때(크기가 작은 간단한 데이터만 넘길때 쓴다  동영상이나 용량이 큰 사진을 넘길때는 쓰지 않는다)
//        intent.putExtra("age",800);

        String str = inputEt.getText().toString();
        Intent intent = new Intent(this, com.example.intent.SubActivity.class);
        intent.putExtra("name",str);
        startActivityForResult(intent,SUB_ACTIVITY);
//        startActivity(intent);
    }
}
