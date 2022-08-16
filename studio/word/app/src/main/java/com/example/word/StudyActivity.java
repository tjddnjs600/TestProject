package com.example.word;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class StudyActivity extends AppCompatActivity implements View.OnClickListener{

    int idx = 0;
    int score = 0;
    int sec;
    boolean isEng = true;

    Button submitBt;
    Button swapBt;
    Button menuBtn;
    Button reGameBtn;
    EditText answerEt;
    TextView modeTv;
    TextView indexTv;
    TextView scoreTv;
    TextView guideTv;
    TextView questionTv;
    TextView secTv;
    TextView resultTv;
    RelativeLayout playLayout;
    RelativeLayout scoreLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        modeTv = findViewById(R.id.modeTv);
        submitBt = findViewById(R.id.submitBt);
        swapBt = findViewById(R.id.swapBt);
        menuBtn = findViewById(R.id.menuBtn);
        reGameBtn = findViewById(R.id.reGameBtn);
        answerEt = findViewById(R.id.answerEt);
        indexTv = findViewById(R.id.indexTv);
        scoreTv = findViewById(R.id.scoreTv);
        guideTv = findViewById(R.id.guideTv);
        questionTv = findViewById(R.id.questionTv);
        playLayout = findViewById(R.id.playLayout);
        scoreLayout = findViewById(R.id.scoreLayout);
        secTv = findViewById(R.id.secTv);
        resultTv = findViewById(R.id.resultTV);
        progressBar = findViewById(R.id.progressBar);

        sec = 10;

        showQuestion();

        submitBt.setOnClickListener(this);
        menuBtn.setOnClickListener(this);
        reGameBtn.setOnClickListener(this);
        swapBt.setOnClickListener(this);

        handler.sendEmptyMessageDelayed(0, 1000);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            sec--;
            progressBar.setProgress(sec);
            handler.sendEmptyMessageDelayed(0,1000);

            if (sec == 0 ){
                nextQuestion();
                sec = 10;
            }

            secTv.setText("남은 시간 "+ sec + "초");
        }
    };

    private void showQuestion(){
        int size = Storage.vocaArr.size();
        secTv.setText("남은 시간 "+ sec + "초");
        scoreTv.setText("점수: " + score + " 점");
        indexTv.setText("문제 " + (idx + 1) + "/" + size);

        if (isEng){
            questionTv.setText(Storage.vocaArr.get(idx).eng);
        } else {
            questionTv.setText(Storage.vocaArr.get(idx).kor);
        }

    }

    private void nextQuestion(){
        idx++;
        try {
            if (idx >= Storage.vocaArr.size()) {
                scoreLayout.setVisibility(View.VISIBLE);
                playLayout.setVisibility(View.INVISIBLE);
                swapBt.setVisibility(View.INVISIBLE);

                handler.removeMessages(0);
            } else {
                showQuestion();
            }
        }catch (Exception e) {
            e.printStackTrace();
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
            score = score + (100/Storage.vocaArr.size());
            Toast.makeText(this, "정답!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "오답!", Toast.LENGTH_SHORT).show();
        }

        resultTv.setText(score+"점");
        sec = 10;
        nextQuestion();

    }

    private void reStart(){
        scoreLayout.setVisibility(View.INVISIBLE);
        playLayout.setVisibility(View.VISIBLE);
        swapBt.setVisibility(View.VISIBLE);

        idx = 0;
        score = 0;
        answerEt.setText("");
        sec = 10;
        handler.removeMessages(0);
        handler.sendEmptyMessageDelayed(0, 1000);
        showQuestion();
    }

    private void swap(){
        isEng = !isEng;
        Log.d("dd", "iseng : "+ isEng);
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
            case R.id.menuBtn:
                finish();
                break;
            case R.id.reGameBtn:
                reStart();
                break;
            case R.id.swapBt:
                swap();
                break;
        }
    }
}
