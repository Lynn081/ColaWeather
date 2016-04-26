package com.example.lynn.colaweather.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.lynn.colaweather.R;
import com.example.lynn.colaweather.model.ColaWeatherDB;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public ColaWeatherDB colaWeatherDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        colaWeatherDB=new ColaWeatherDB(this);
        colaWeatherDB.openDatabase();
        colaWeatherDB.closeDatabase();
        Timer timer=new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                Intent i=new Intent(MainActivity.this,SearchCityActivity.class);
                startActivity(i);
            }};
        timer.schedule(timerTask,2000);
        }




}
