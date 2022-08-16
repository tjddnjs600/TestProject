package com.example.threadandhandler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
//        handler.sendEmptyMessage(0);
        handler.sendEmptyMessageDelayed(0, 1000);
        new MyThread().start();
    }
    int sec = 0;
    int sec2 = 0;

    class MyThread extends Thread {
        @Override
        public void run() {
            while(true){
                sec2++;
                handler.sendEmptyMessage(0);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            tv.setText("안녕! "+sec2);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0); //메시지 제거 방법 what 을 이용
    }
}
