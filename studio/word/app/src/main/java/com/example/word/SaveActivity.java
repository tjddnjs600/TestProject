package com.example.word;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SaveActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    boolean isEdit = false;
    int position = -1;

    Button addBt;
    Button schBt;
    EditText korEt;
    EditText engEt;
    EditText schEt;
    ListView centerLv;
    RelativeLayout playLayout;

    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        centerLv = findViewById(R.id.centerLv);
        addBt = findViewById(R.id.addBt);
        schBt = findViewById(R.id.schBt);
        korEt = findViewById(R.id.korEt);
        engEt = findViewById(R.id.engEt);
        schEt = findViewById(R.id.schEt);
        playLayout = findViewById(R.id.playLayout);

        adapter = new MyAdapter(this);
        centerLv.setAdapter(adapter);

        showVoca();

        addBt.setOnClickListener(this);
        schBt.setOnClickListener(this);
        centerLv.setOnItemClickListener(this);
    }

    private void addVoca() {
        String eng = engEt.getText().toString();
        String kor = korEt.getText().toString();

        saveDb(eng, kor);

        engEt.setText("");
        korEt.setText("");
        showVoca();
    }

    private void saveDb(String eng, String kor){
        SQLiteDatabase db = null;
        if (db == null) {
            db = openOrCreateDatabase("sqlist_test.db",  Context.MODE_PRIVATE, null);
        }
        db.execSQL("INSERT INTO word (eng,kor) VALUES ('"+eng+"','"+kor+"')");
        db.close();
    }

    private void showVoca(){
        SQLiteDatabase db = null;
        if (db == null) {
            db = openOrCreateDatabase("sqlist_test.db", Context.MODE_PRIVATE, null);
        }

        //데이터 가져오기
        Cursor c = db.rawQuery("SELECT * FROM word", null);

        c.moveToFirst();
        Storage.vocaArr.clear();
        while (c.isAfterLast() == false) {
            int idx = c.getInt(0);
            String eng = c.getString(1);
            String kor = c.getString(2);
            Storage.vocaArr.add(new Voca(idx, eng, kor));
            c.moveToNext();
        }
        c.close();

        adapter.notifyDataSetChanged();
    }

    private void update(){
        String eng = engEt.getText().toString();
        String kor = korEt.getText().toString();
        int idx = Storage.vocaArr.get(position).idx;
        Log.d("dd","idx"+idx);
        dbUpdate(idx,eng,kor);

        isEdit = false;
        addBt.setText("추가");
        showVoca();

        engEt.setText("");
        korEt.setText("");
    }

    private void dbUpdate(int idx, String eng, String kor) {
        SQLiteDatabase db = null;
        if (db == null) {
            db = openOrCreateDatabase("sqlist_test.db",  Context.MODE_PRIVATE, null);
        }

        String temp = "UPDATE word SET eng = '"+eng +"', kor = '"+kor+"' WHERE idx = " +idx+" ;";
        db.execSQL(temp) ;
        db.close();
    }

    private void delete(int idx) {
        SQLiteDatabase db = null;
        if (db == null) {
            db = openOrCreateDatabase("sqlist_test.db",  Context.MODE_PRIVATE, null);
        }
        db.execSQL("DELETE FROM word WHERE idx = "+idx+";") ;
        db.close();
    }

    private void selectOne(String sch){
        SQLiteDatabase db = null;
        if (db == null) {
            db = openOrCreateDatabase("sqlist_test.db",  Context.MODE_PRIVATE, null);
        }

        String temp = "SELECT eng FROM word where eng LIKE '%'+sch+'%';";
        db.execSQL(temp) ;
        db.close();
    }

    private void clickSch() {
        korEt.setVisibility(View.INVISIBLE);
        engEt.setVisibility(View.INVISIBLE);
        schEt.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        this.position = position;
        String str = Storage.vocaArr.get(position).eng + " : " + Storage.vocaArr.get(position).kor;
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("무엇을 하시겠습니까?");
        ab.setMessage(str);
        Log.d("dd","position"+Storage.vocaArr.get(position));
        ab.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete(Storage.vocaArr.get(position).idx);
                showVoca();
            }
        });
        ab.setNegativeButton("수정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isEdit = true;
                addBt.setText("수정");
                engEt.setText(Storage.vocaArr.get(position).eng);
                korEt.setText(Storage.vocaArr.get(position).kor);
            }
        });
        ab.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addBt:
                if (!isEdit) {
                    addVoca();
                } else {
                    update();
                }
                break;
            case R.id.schBt:
                
                break;
        }
    }
}

class MyAdapter extends ArrayAdapter {
    LayoutInflater lnf;

    public MyAdapter(Activity context) {
        super(context, R.layout.item, Storage.vocaArr);
        lnf = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return Storage.vocaArr.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return Storage.vocaArr.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        RowDataViewHolder viewHolder;
        if(convertView == null) {
            convertView = lnf.inflate(R.layout.item, parent, false);
            viewHolder = new RowDataViewHolder();
            viewHolder.engTvHolder = convertView.findViewById(R.id.engTv);
            viewHolder.korTvHolder = convertView.findViewById(R.id.korTv);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (RowDataViewHolder)convertView.getTag();
        }

//            if(position%2 ==0){
//                viewHolder.idxHolder.setTextColor(Color.RED);
//            }else{
//                viewHolder.idxHolder.setTextColor(Color.BLACK);
//            }


        viewHolder.engTvHolder.setText(Storage.vocaArr.get(position).eng);
        viewHolder.korTvHolder.setText(Storage.vocaArr.get(position).kor);


        return convertView;
    }

    public class RowDataViewHolder {
        public TextView engTvHolder;
        public TextView korTvHolder;

    }
}



