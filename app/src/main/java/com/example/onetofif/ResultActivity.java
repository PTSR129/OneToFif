package com.example.onetofif;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    TextView txt_result, txt_time;
    Button btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);

        txt_result = findViewById(R.id.txt_result);
        txt_time = findViewById(R.id.txt_time);
        btn_ok = findViewById(R.id.btn_ok);

        Intent intent = getIntent();
        txt_result.setText(intent.getStringExtra("result"));
        txt_time.setText(intent.getDoubleExtra("time", 0) + "s");

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
