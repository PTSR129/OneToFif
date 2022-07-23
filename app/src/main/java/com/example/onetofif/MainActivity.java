package com.example.onetofif;

import static com.example.onetofif.gameEngine.getCurrentNumber;
import static com.example.onetofif.gameEngine.getGameMap;
import static com.example.onetofif.gameEngine.getGoalNumber;
import static com.example.onetofif.gameEngine.getNextBtnText;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txt_currentNumber;

    Button btn_start, btn_stop;

    Chronometer chronometer_gameTime;

    static int[] resId = new int[25];
    static int gameRunningStatus = 0; // 0 = stop, 1 = running

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start = findViewById(R.id.btn_start);
        btn_stop = findViewById(R.id.btn_stop);

        txt_currentNumber = findViewById(R.id.txt_number);
        chronometer_gameTime = findViewById(R.id.chronometer_gameTime);

        for (int i = 0; i < 25; i++) {
            resId[i] = getResources().getIdentifier("btn" + (i + 1), "id", getPackageName());
            ((Button) findViewById(resId[i])).setOnClickListener(this);
        }

        btn_start.setOnClickListener(new View.OnClickListener() {
            //게임 start
            @Override
            public void onClick(View view) {
                if (gameRunningStatus == 0) {
                    gameEngine.init();
                    chronometer_gameTime.setBase(SystemClock.elapsedRealtime());
                    chronometer_gameTime.start();
                    putNumber(1);
                    gameRunningStatus = 1;
                }
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            //게임 stop
            @Override
            public void onClick(View view) {
                if (gameRunningStatus == 1) {
                    gameEngine.init();
                    chronometer_gameTime.stop();
                    chronometer_gameTime.setBase(SystemClock.elapsedRealtime());
                    putNumber(0);
                    txt_currentNumber.setText(Integer.toString(gameEngine.getCurrentNumber()));
                    gameRunningStatus = 0;
                }
            }
        });
    }

    public void putNumber(int status) {// 0 : 전부 비어있도록, 1 : 랜덤
        //버튼에 숫자 삽입
        if (status == 1) {//gameMap을 불러와서 대입
            int[][] gameMap = gameEngine.getGameMap();
            for (int i = 0; i < 25; i++)
                ((Button) findViewById(resId[i])).setText(Integer.toString(gameMap[i / 5][i % 5]));
        }
        if (status == 0) {//Empty String 대입
            for (int i = 0; i < 25; i++)
                ((Button) findViewById(resId[i])).setText("");
        }
    }

    @Override
    public void onClick(View view) {
        if(gameRunningStatus == 0)return; // 게임이 멈춰 있을 때는 무시
        Button btn = (Button) view;
        if(btn.getText() == "" || gameEngine.getCurrentNumber() != Integer.parseInt(btn.getText().toString())){
            //gameOver
            putNumber(0);
            chronometer_gameTime.stop();
            gameRunningStatus = 0;

            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("time", getTime());
            chronometer_gameTime.setBase(SystemClock.elapsedRealtime());
            intent.putExtra("result", "failed (" + (getGoalNumber() - getCurrentNumber() + 1) + "left)");
            
            gameEngine.init();//진행도를 표시하기 위해 초기화를 putExtra 뒤에 함
            txt_currentNumber.setText(Integer.toString(gameEngine.getCurrentNumber()));
            startActivity(intent);
        }
        else {
            if(gameEngine.lastCurrentNumber()){
                //game clear
                gameEngine.init();
                txt_currentNumber.setText(Integer.toString(gameEngine.getCurrentNumber()));
                putNumber(0);
                chronometer_gameTime.stop();
                gameRunningStatus = 0;

                Intent intent = new Intent(this, ResultActivity.class);
                intent.putExtra("time", getTime());
                chronometer_gameTime.setBase(SystemClock.elapsedRealtime());
                intent.putExtra("result", "success");
                startActivity(intent);
            }
            else{
                btn.setText(gameEngine.getNextBtnText());
                gameEngine.increaseCurrentNumber();
                txt_currentNumber.setText(Integer.toString(gameEngine.getCurrentNumber()));
            }
        }
    }
    public double getTime(){
        double time = (SystemClock.elapsedRealtime() - chronometer_gameTime.getBase()) / 10;
        time = time / 100.0;//소수점 둘째짜리 까지
        return time;
    }
}