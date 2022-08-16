package com.example.loginvoca;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener{

    EditText idEt;
    EditText pewEt;
    EditText genderEt;
    Button chkBt;
    Button memberBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        idEt = findViewById(R.id.idEt);
        pewEt = findViewById(R.id.pewEt);
        genderEt = findViewById(R.id.genderEt);
        chkBt = findViewById(R.id.chkBt);
        memberBt = findViewById(R.id.memberBt);


        chkBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chkRequestPost();
            }
        });

        memberBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logRequestPost();
            }
        });


    }

    private void logRequestPost(){ // 회원가입

        /** Post **/
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String url = "http://heutwo.dothome.co.kr/sample/join.php";

        StringRequest myReq = new StringRequest(Request.Method.POST, url,
                joinListener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userid", idEt.getText().toString());
                params.put("pass", shaPass(pewEt.getText().toString()));
                params.put("gender", genderEt.getText().toString());
                return params;
            }
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f)

        );
        stringRequest.add(myReq);

//        Log.d("dd","sha  "+ shaPass(pewEt.getText().toString()));
    }

    private void chkRequestPost(){ //중복체크
        /** Post **/
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String url = "http://heutwo.dothome.co.kr/sample/duplicate_id.php";

        StringRequest myReq = new StringRequest(Request.Method.POST, url,this
                , this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userid", idEt.getText().toString());
                return params;
            }
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f)

        );
        stringRequest.add(myReq);
    }

    public String shaPass(String str){

        String SHA = "";

        try{
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(str.getBytes());
            byte byteData[] = sh.digest();
            StringBuffer sb = new StringBuffer();
            for(int i = 0 ; i < byteData.length ; i++){
                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
            }
            SHA = sb.toString();

        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            SHA = null;
        }
        return SHA;
    }

    Response.Listener<String> joinListener = new Response.Listener<String>() { //회원가입
        @Override
        public void onResponse(String response) {
            Log.d("cc", "kk: "+response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                String result = jsonObject.getString("result");
                String token = jsonObject.getString("token");
                String msg = jsonObject.getString("msg");

                Log.d("cc","token  " +token);
                Log.d("cc","mag  " +msg);
                Toast.makeText(LoginActivity.this, result +", "+token, Toast.LENGTH_SHORT).show();
                Toast.makeText(LoginActivity.this, result +", "+msg, Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    };

    @Override
    public void onResponse(String response) {
//        Log.d("dd", response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            String result = jsonObject.getString("result");
            String msg = jsonObject.getString("msg");
//            Log.d("dd", msg);

            Toast.makeText(this, result +", "+msg, Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
