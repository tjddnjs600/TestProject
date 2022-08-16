package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    ArrayList<String> arr2;

    ListView mainLv;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLv = findViewById(R.id.mainLv);

        arr2 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            arr2.add("하하하 "+i);
        }
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arr2);
        mainLv.setAdapter(adapter);
        mainLv.setOnItemClickListener(this);
        findViewById(R.id.btn).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        arr2.add("안녕!");
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("heu","pos: "+ arr2.get(position));
    }
}
