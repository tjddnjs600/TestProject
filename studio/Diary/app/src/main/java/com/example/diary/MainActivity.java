package com.example.diary;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    //데이터 베이스에 월 일 내용 순으로 넣어야함
    static int positon;

    TextView monthTv;
    GridView calendarGv;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        monthTv = findViewById(R.id.monthTv);

        initDb();
        addCal();
        loadDb();

        calendarGv = findViewById(R.id.calendarGv);
        adapter = new MyAdapter(this);
        calendarGv.setAdapter(adapter);

        calendarGv.setOnItemClickListener(this);

    }

    private void initDb(){
        SQLiteDatabase db = null;
        if (db == null) {
            db = openOrCreateDatabase("sqlist_test.db",  Context.MODE_PRIVATE, null);
        }
        db.execSQL("CREATE TABLE IF NOT EXISTS calList("
                + "idx INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "year INTEGER,"
                + "month INTEGER,"
                + "date INTEGER,"
                + "content TEXT"
                + ");");

        db.close();
    }

    private void loadDb(){
        SQLiteDatabase db = null;
        if (db == null) {
            db = openOrCreateDatabase("sqlist_test.db",  Context.MODE_PRIVATE, null);
        }
        Cursor c = db.rawQuery("SELECT * FROM calList", null);

        c.moveToFirst();

        while (c.isAfterLast() == false) {
            int idx = c.getInt(0);
            int year = c.getInt(1);
            int month = c.getInt(2);
            int date = c.getInt(3);
            String content = c.getString(4);
            Log.d("heu",year+"/"+month+"/"+date +"  "+content);

            c.moveToNext();
        }
        c.close();
        db.close();
    }



    private void addCal(){
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.DATE, 1);
        int dow = cal.get(Calendar.DAY_OF_WEEK);
        int dis = dow -1;
        cal.add(Calendar.MONTH, -1);
        int max = cal.getActualMaximum(Calendar.DATE);
        for (int i = 0; i < dis; i++) {
            Storage.calListArr.add(0, new MyCalData(-1,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH), max, ""));
//            arr.add(0, max);
            max--;
        }

        cal.add(Calendar.MONTH, 1);
        max = cal.getActualMaximum(Calendar.DATE);
        for (int i = 0; i < max; i++) {
            Storage.calListArr.add(new MyCalData(-1,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),i+1,""));
//            arr.add(i+1);
        }

        cal.set(Calendar.DATE, max);
        dow = cal.get(Calendar.DAY_OF_WEEK);  /// 이번달 마지막의 요일
        dis = 7 - dow;
        cal.add(Calendar.MONTH, 1);
        for (int i = 0; i < dis; i++) {
            Storage.calListArr.add(new MyCalData(-1,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),i+1,""));
//            arr.add(i+1);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.positon = position;
//        Intent in = new Intent(MainActivity.this, com.example.diary.ListActivity.class);
//        startActivity(in);
    }


    class MyAdapter extends ArrayAdapter {
        LayoutInflater lnf;

        public MyAdapter(Activity context) {
            super(context, R.layout.item, Storage.calListArr);
            lnf = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return Storage.calListArr.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return Storage.calListArr.get(position);
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
                viewHolder.dateHolder = convertView.findViewById(R.id.dateTv);
//                viewHolder.contentHolder = convertView.findViewById(R.id.contentTv);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (RowDataViewHolder)convertView.getTag();
            }

            viewHolder.dateHolder.setText(Storage.calListArr.get(position).date+"");
//            int year = Storage.calListArr.get(position).year;
//            int month = Storage.calListArr.get(position).month;
//            int date = Storage.calListArr.get(position).date;
//            boolean isContent = getContent(year, month, date);

//            if(isContent){
//                viewHolder.contentHolder.setText("  ");
//                viewHolder.contentHolder.setBackgroundColor(Color.RED);
//            }else{
//                viewHolder.contentHolder.setText("");
//            }


            return convertView;
        }
    }

    public class RowDataViewHolder {
        public TextView dateHolder;
//        public TextView contentHolder;

    }

}
