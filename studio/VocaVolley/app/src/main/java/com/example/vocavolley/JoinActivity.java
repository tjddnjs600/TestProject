package com.example.vocavolley;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class JoinActivity extends BaseActivity implements View.OnClickListener, Response.ErrorListener {
    boolean isChk = false;

    EditText idEt;
    EditText passEt1;
    EditText passEt2;
    Button submitBt;
    Button chkBt;
    RadioButton maleRb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        maleRb = findViewById(R.id.maleRb);
        idEt = findViewById(R.id.idEt);
        passEt1 = findViewById(R.id.passEt1);
        passEt2 = findViewById(R.id.passEt2);
        chkBt = findViewById(R.id.chkBt);
        submitBt = findViewById(R.id.submitBt);

        chkBt.setOnClickListener(this);
        submitBt.setOnClickListener(this);

        idEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isChk = false;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean chkValid() {
        boolean result = true;

        if (!isChk) {
            showToast("아이디 중복체크 해주세요");
            result = false;
        } else if (!passEt1.getText().toString().trim().equals(passEt2.getText().toString().trim())) {
            showToast("암호가 일치하지 않습니다");
            result = false;
        } else if (idEt.getText().toString().trim().length() < 1) {
            showToast("아이디를 입력해 주세요");
            result = false;
        } else if (passEt1.getText().toString().trim().length() < 1) {
            showToast("암호를 입력해 주세요");
            result = false;
        }

        return result;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submitBt) {
            if (chkValid()) {
                //회원가입 시도
                String url = "http://heutwo.dothome.co.kr/sample/join.php";
                params.clear();
                params.put("userid", idEt.getText().toString().trim());
                params.put("pass", enc(passEt1.getText().toString().trim()));
                params.put("gender", maleRb.isChecked() ? "m" : "f");
                request(url, joinListener);
            }
        } else if (v.getId() == R.id.chkBt) {
            final String id = idEt.getText().toString().trim();
            if (id.length() < 1) {
                Toast.makeText(this, "아이디를 입력해 주세요", Toast.LENGTH_SHORT).show();
            } else {

                String url = "http://heutwo.dothome.co.kr/sample/duplicate_id.php";
                params.clear();
                params.put("userid", idEt.getText().toString().trim());
                request(url, chkListener);
            }
        }
    }

    Response.Listener<String> chkListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String result = jsonObject.getString("result");
                if(result.equalsIgnoreCase("OK")){
                    isChk = true;
                    showDialog("사용 가능 아이디!");
                }else{
                    showDialog("불가 불가 불가 불가");
                    isChk = false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    Response.Listener<String> joinListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String result = jsonObject.getString("result");
                if(result.equalsIgnoreCase("ok")){
                    Toast.makeText(JoinActivity.this, "회원 가입 성공!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("id",idEt.getText().toString().trim());
                    intent.putExtra("pass", passEt1.getText().toString().trim());
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    AlertDialog.Builder ab = new AlertDialog.Builder(JoinActivity.this);
                    ab.setTitle("회원가입실패!");
                    ab.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
