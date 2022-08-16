package com.example.voca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class SwapActivity extends AppCompatActivity implements View.OnClickListener {

    RadioButton koSwap;
    RadioButton enSwap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap);

        findViewById(R.id.chkBtn).setOnClickListener(this);
        koSwap = findViewById(R.id.koSwap);
        enSwap = findViewById(R.id.enSwap);

    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        if (enSwap.isChecked()){
            intent.putExtra("isSwap", false);
        } else if(koSwap.isChecked()){
            intent.putExtra("isSwap", true);
        }
        setResult(10, intent);
        finish();
    }
}
