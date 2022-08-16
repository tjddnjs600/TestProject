package com.example.voca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class VocaStudyActivity extends AppCompatActivity implements View.OnClickListener {
    int idx = 0;
    int score = 0;
    int sec;
    boolean isEng = true;
    boolean isChk = false;

    /**
     * 단어 게임 레이아웃
     **/
    Button submitBt;
    EditText answerEt;
    TextView modeTv;
    TextView indexTv;
    TextView scoreTv;
    TextView guideTv;
    TextView questionTv;
    TextView secTv;
    RelativeLayout addLayout;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voca_study);
        modeTv = findViewById(R.id.modeTv);
        submitBt = findViewById(R.id.submitBt);
        answerEt = findViewById(R.id.answerEt);
        indexTv = findViewById(R.id.indexTv);
        scoreTv = findViewById(R.id.scoreTv);
        guideTv = findViewById(R.id.guideTv);
        questionTv = findViewById(R.id.questionTv);
        addLayout = findViewById(R.id.addLayout);
        secTv = findViewById(R.id.secTv);
        progressBar = findViewById(R.id.progressBar);

//        Storage.vocaArr.add(new Voca("dog", "개"));
//        Storage.vocaArr.add(new Voca("cat", "고양이"));
//        Storage.vocaArr.add(new Voca("duck", "오리새끼"));

        sec = 10;

        showQuestion();

        submitBt.setOnClickListener(this);
        findViewById(R.id.swapBt).setOnClickListener(this);

        handler.sendEmptyMessageDelayed(0,1000);

    }


    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            sec--;
            progressBar.setProgress(sec);
            handler.sendEmptyMessageDelayed(0,1000);

            if(sec == 0){
                nextQuestion();
                sec = 10;
            }

            secTv.setText("남은 시간 "+ sec + "초");
        }
    };


    private void showQuestion() {
        int size = Storage.vocaArr.size();
        secTv.setText("남은 시간 "+ sec + "초");
        scoreTv.setText("점수: " + score + " 점");
        indexTv.setText("문제 " + (idx + 1) + "/" + size);

        if(isEng) {
            questionTv.setText(Storage.vocaArr.get(idx).eng);
        }else{
            questionTv.setText(Storage.vocaArr.get(idx).kor);
        }
    }

    private void checkAnswer() {
        String myAnswer = answerEt.getText().toString();
        answerEt.setText("");
        String rightAnswer = Storage.vocaArr.get(idx).kor;
        if(!isEng){
            rightAnswer = Storage.vocaArr.get(idx).eng;
        }

        if (myAnswer.equals(rightAnswer)) {
            score = score + 10;
            Toast.makeText(this, "정답!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "오답! 바보오오오오오오야", Toast.LENGTH_SHORT).show();
        }

        sec = 10;
        handler.removeMessages(0);
        handler.sendEmptyMessageDelayed(0, 1000);
        nextQuestion();

    }

    private void nextQuestion(){
        idx++;
        if (idx >= Storage.vocaArr.size()) {
            idx--;
            Toast.makeText(this, "문제 없음!", Toast.LENGTH_SHORT).show();
            handler.removeMessages(0);
        } else {
            showQuestion();
        }
    }

    private void swap(){
        isEng = !isEng;
        idx = 0;
        score = 0;
        answerEt.setText("");
        sec = 10;
        handler.removeMessages(0);
        handler.sendEmptyMessageDelayed(0, 1000);
        showQuestion();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitBt:
                checkAnswer();
                break;
            case R.id.swapBt:
                swap();
                break;
        }
    }
}

