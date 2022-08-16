package com.example.vocavolley;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class BaseActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {
    RelativeLayout loadingLayout;
    Map<String, String> params = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    public void showToast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public String enc(String originalStr) {
        try {

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(originalStr.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }


            originalStr = hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return originalStr;
    }

    public void request(String url, Response.Listener<String> listener){
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        StringRequest myReq = new StringRequest(Request.Method.POST, url,
                listener
                , this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                return params;
            }
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f)

        );
        stringRequest.add(myReq);
    }

    public void showDialog(String msg){
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle(msg);
        ab.show();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if(loadingLayout != null){
            loadingLayout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onResponse(String response) {

    }
}