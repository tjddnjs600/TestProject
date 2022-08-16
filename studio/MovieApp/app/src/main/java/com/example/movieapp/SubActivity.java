package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SubActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {

    TextView centerTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        centerTv = findViewById(R.id.centerTv);

        Intent getIn = getIntent();
        String code = getIn.getStringExtra("code");

        Log.d("mo",code);



        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String uri = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?key=1b059e84ad938f87ed5259629ebe003a&movieCd="+code;
        StringRequest myReq = new StringRequest(Request.Method.GET, uri, this, this);
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f));
        stringRequest.add(myReq);

        findViewById(R.id.backbt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onResponse(String response) {
        Log.d("gg",response);
        try {
            String str = "";
            JSONObject jsonObject = new JSONObject(response);
            JSONObject jObj = jsonObject.getJSONObject("movieInfoResult");
            JSONObject temp = jObj.getJSONObject("movieInfo");
            Log.d("gg","jObj: "+temp);

            String movieNm = temp.getString("movieNm");
            String showTm = temp.getString("showTm");

//            Log.d("hh","/"+movieNm+"/"+showTm+"/");

            JSONArray jArr = temp.getJSONArray("directors");

            for (int i = 0; i < jArr.length() ; i++) {
                JSONObject tempObj = jArr.getJSONObject(i);

                String peopleNm = tempObj.getString("peopleNm");
                Log.d("hh",peopleNm);
                str = "영화 제목: "+ movieNm +"\n"+" 감독 이름: "+ peopleNm+"\n"+" 상영시간: "+ showTm+"분"+"\n";
            }

            JSONArray jArr2 = temp.getJSONArray("companys");

            for (int i = 0; i < jArr2.length() ; i++) {
                JSONObject tempObj = jArr2.getJSONObject(i);
                String companyNm = tempObj.getString("companyNm");
                str = str+" 영화사: "+companyNm+"\n";
            }

            centerTv.setText(str);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
