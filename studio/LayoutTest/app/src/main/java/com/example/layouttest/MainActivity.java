package com.example.layouttest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.layout);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    private void showDialog(){
        LayoutInflater lnf;
        lnf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = lnf.inflate(R.layout.item, null, false);
        ImageView profileIv = convertView.findViewById(R.id.profileIv);
        TextView nameTv = convertView.findViewById(R.id.nameTv);
        TextView ageTv = convertView.findViewById(R.id.ageTv);
        TextView addressTv = convertView.findViewById(R.id.addressTv);
        profileIv.setImageResource(R.mipmap.ic_launcher);
        nameTv.setText("이름: 개똥이 "+i);
        ageTv.setText("나이: 3200 "+i);
        addressTv.setText("주소: 히말랴야 "+i);

        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("타이틀");
        ab.setView(convertView);
        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        ab.show();
    }

    int i = 0;
    @Override
    public void onClick(View v) {
        showDialog();

        i++;
//        LayoutInflater lnf;
//        lnf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View convertView = lnf.inflate(R.layout.item, null, false);
//        ImageView profileIv = convertView.findViewById(R.id.profileIv);
//        TextView nameTv = convertView.findViewById(R.id.nameTv);
//        TextView ageTv = convertView.findViewById(R.id.ageTv);
//        TextView addressTv = convertView.findViewById(R.id.addressTv);
//        profileIv.setImageResource(R.mipmap.ic_launcher);
//        nameTv.setText("이름: 개똥이 "+i);
//        ageTv.setText("나이: 3200 "+i);
//        addressTv.setText("주소: 히말랴야 "+i);
//        layout.addView(convertView);


//        TextView tv = new TextView(this);
//        tv.setBackgroundColor(Color.parseColor("#ff0000"));
//        tv.setText("생겨난 버튼 "+ i);
//        tv.setTextSize(30f);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT, 200);
//        tv.setLayoutParams(params);
//        layout.addView(tv);
    }
}
