package com.example.firstday;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WordListActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Voca> arr = new ArrayList<>();

    RelativeLayout wordTxt;
    RelativeLayout wordGame;
    TextView centerTv;
    TextView qWord;
    TextView point;
    TextView quizNum;
    Button clickBt;
    Button removeBt;
    EditText inputEn;
    EditText inputKo;
    EditText qAns;
    Button swapBt;
    Button isBack;
    Button ansBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_word);

        centerTv = findViewById(R.id.centerTv);//이 선언문은 여기에 쓴다
        wordTxt = findViewById(R.id.wordTxt);
        wordGame = findViewById(R.id.wordGame);
        clickBt = findViewById(R.id.clickBt);
        qWord = findViewById(R.id.qWord);
        point = findViewById(R.id.point);
        quizNum = findViewById(R.id.quizNum);
        inputEn = findViewById(R.id.inputEn);
        inputKo = findViewById(R.id.inputKo);
        qAns = findViewById(R.id.qAns);
        removeBt = findViewById(R.id.removeBt);
        swapBt = findViewById(R.id.swapBt);
        isBack = findViewById(R.id.isBack);
        ansBt = findViewById(R.id.ansBt);


        clickBt.setOnClickListener(this);
        removeBt.setOnClickListener(this);
        swapBt.setOnClickListener(this);
        isBack.setOnClickListener(this);
        ansBt.setOnClickListener(this);
        findViewById(R.id.backBtn).setOnClickListener(this);

        wordGame.setVisibility(View.INVISIBLE);

    }

    public void add() {
        String eng = inputEn.getText().toString();
        String kor = inputKo.getText().toString();
        arr.add(new Voca(eng.trim(), kor.trim()));
        inputEn.setText("");
        inputKo.setText("");
        String word = "";
        for (int i = 0; i < arr.size(); i++) {
            word = word + arr.get(i).getEng() + " : " + arr.get(i).getKor() + "\n";
        }

        centerTv.setText(word);

    }


    public void remove() {
        int idx = Integer.parseInt(inputEn.getText().toString());
        int temp = idx - 1;
        arr.remove(temp);
        String word = "";
        for (int i = 0; i < arr.size(); i++) {
            word = word + arr.get(i).getEng() + " : " + arr.get(i).getKor() + "\n";
        }

        centerTv.setText(word);
    }

    int score = 0;
    int idx = 0;

    private void show() { //안드로이드 스튜디오는 스탑상태여서 굳이 포문을 안돌려도 됨
        int size = arr.size();
        point.setText("점수 : " + score + " 점");
        quizNum.setText("문제 " + (idx + 1) + " / " + arr.size());
        qWord.setText(arr.get(idx).getEng());
    }

    private void answer() {
        String qAnswer = qAns.getText().toString();
        String rAnswer = arr.get(idx).getKor();

        if (qAnswer.equals(rAnswer)) {
            score += 10;
            Toast.makeText(this, "정답!", Toast.LENGTH_SHORT).show(); //토스트 메세지 창
        } else {
            Toast.makeText(this, "땡!", Toast.LENGTH_SHORT).show();
        }

        idx++;

        qAns.setText("");
        if (idx >= arr.size()) {
            idx--;
            Toast.makeText(this, "문제끝!", Toast.LENGTH_SHORT).show();
        } else {
            show();
        }
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.clickBt) {
            add();
        } else if (v.getId() == R.id.removeBt) {//버튼 클릭을 할 때  보이고 사라지고를 정한다.
            wordTxt.setVisibility(View.INVISIBLE);
            wordGame.setVisibility(View.VISIBLE);
            score = 0;
            idx = 0;
            show();
        } else if (v.getId() == R.id.isBack) {
            wordGame.setVisibility(View.INVISIBLE);
            wordTxt.setVisibility(View.VISIBLE);
        } else if (v.getId() == R.id.ansBt) {
            answer();
        } else if(v.getId() == R.id.backBtn){
            finish();
        }
    }
}
