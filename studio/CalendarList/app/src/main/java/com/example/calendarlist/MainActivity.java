package com.example.calendarlist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    GridView calGv;
    TextView infoTv;
    EditText inputEt;
    Button submitBt;

    int year;
    int month;
    int date;
    MyAdapter adapter;
    ArrayList<MyData> arr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calGv = findViewById(R.id.calGv);
        infoTv = findViewById(R.id.infoTv);
        inputEt = findViewById(R.id.inputEt);
        submitBt = findViewById(R.id.submitBt);

        initDb();
        show();
        loadDb();

        adapter = new MyAdapter(this);
        calGv.setAdapter(adapter);
        calGv.setOnItemClickListener(this);

        submitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void show(){

        setCal();
    }

    private void save(){
        String content = inputEt.getText().toString();
        SQLiteDatabase db = null;
        if (db == null) {
            db = openOrCreateDatabase("sqlist_test.db",  Context.MODE_PRIVATE, null);
        }
        db.execSQL("INSERT INTO sch (year,month,date,content) VALUES " +
                "("+ year+","
                + month+ ", "
                + date + ", "
                +"'"+ content + "'"
                + ")");
        db.close();
    }

    private void loadDb(){
        SQLiteDatabase db = null;
        if (db == null) {
            db = openOrCreateDatabase("sqlist_test.db",  Context.MODE_PRIVATE, null);
        }
        Cursor c = db.rawQuery("SELECT * FROM sch", null);

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

    private boolean getContent(int year, int month, int date){
        boolean result = false;
        SQLiteDatabase db = null;
        if (db == null) {
            db = openOrCreateDatabase("sqlist_test.db",  Context.MODE_PRIVATE, null);
        }
        Cursor c = db.rawQuery("SELECT * FROM sch WHERE year="+year+" and month="+month+" and date="+date, null);

        c.moveToFirst();

        while (c.isAfterLast() == false) {
            result = true;
//            result = c.getString(4);
            c.moveToNext();
        }
        c.close();
        db.close();
        return result;
    }

    private void initDb(){
        SQLiteDatabase db = null;
        if (db == null) {
            db = openOrCreateDatabase("sqlist_test.db",  Context.MODE_PRIVATE, null);
        }
        db.execSQL("CREATE TABLE IF NOT EXISTS sch("
                + "idx INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "year INTEGER,"
                + "month INTEGER,"
                + "date INTEGER,"
                + "content TEXT"
                + ");");

        db.close();
    }

    private void setCal(){
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.DATE, 1);
        int dow = cal.get(Calendar.DAY_OF_WEEK);
        int dis = dow -1;
        cal.add(Calendar.MONTH, -1);
        int max = cal.getActualMaximum(Calendar.DATE);
        for (int i = 0; i < dis; i++) {
            arr.add(0, new MyData(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH), max, ""));
//            arr.add(0, max);
            max--;
        }

        cal.add(Calendar.MONTH, 1);
        max = cal.getActualMaximum(Calendar.DATE);
        for (int i = 0; i < max; i++) {
            arr.add(new MyData(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),i+1,""));
//            arr.add(i+1);
        }

        cal.set(Calendar.DATE, max);
        dow = cal.get(Calendar.DAY_OF_WEEK);  /// 이번달 마지막의 요일
        dis = 7 - dow;
        cal.add(Calendar.MONTH, 1);
        for (int i = 0; i < dis; i++) {
            arr.add(new MyData(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),i+1,""));
//            arr.add(i+1);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        year = arr.get(position).year;
        month = arr.get(position).month;
        date = arr.get(position).date;
        infoTv.setText(arr.get(position).year+"/"
                +(arr.get(position).month+1)+"/"
                +arr.get(position).date);
    }

    class MyAdapter extends ArrayAdapter {
        LayoutInflater lnf;

        public MyAdapter(Activity context) {
            super(context, R.layout.item, arr);
            lnf = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return arr.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return arr.get(position);
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
                viewHolder.contentHolder = convertView.findViewById(R.id.contentTv);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (RowDataViewHolder)convertView.getTag();
            }

            viewHolder.dateHolder.setText(arr.get(position).date+"");
            int year = arr.get(position).year;
            int month = arr.get(position).month;
            int date = arr.get(position).date;
            boolean isContent = getContent(year, month, date);

            if(isContent){
                viewHolder.contentHolder.setText("  ");
                viewHolder.contentHolder.setBackgroundColor(Color.RED);
            }else{
                viewHolder.contentHolder.setText("");
            }


            return convertView;
        }
    }

    public class RowDataViewHolder {
        public TextView dateHolder;
        public TextView contentHolder;

    }
}
