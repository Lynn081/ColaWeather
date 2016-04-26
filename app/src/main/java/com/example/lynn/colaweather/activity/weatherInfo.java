package com.example.lynn.colaweather.activity;

import android.app.Activity;
import android.content.Entity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.example.lynn.colaweather.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class weatherInfo extends Activity {

    public static final String KEY = "6438d5ec3a301c8d08e1767c76ca0409";
    public String cityName1;
    private TextView current_temp;
    private TextView publish_time;
    private TextView city_humidity;
    private TextView today_temper;
    private TextView tomorrow_temper;
    private TextView today_weather;
    private TextView tomorrow_weather;
    private TextView the_city_name;
    String temp;
    String time;
    String humidity;
    String today_tmp;
    String today_weatherInfo;
    String tomorrow_temp;
    String tomorrow_weatherInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_view);
        current_temp = (TextView) findViewById(R.id.current_temp);
        publish_time = (TextView) findViewById(R.id.publish_time);
        city_humidity = (TextView) findViewById(R.id.humidity);
        today_weather = (TextView) findViewById(R.id.today_weathers);
        tomorrow_weather = (TextView) findViewById(R.id.tomorrow_weathers);
        today_temper = (TextView) findViewById(R.id.today_temp);
        tomorrow_temper = (TextView) findViewById(R.id.tomorrow_temp);
        the_city_name= (TextView) findViewById(R.id.the_city_name);


        Intent intent = getIntent();
        cityName1 = intent.getStringExtra("city_name");
        the_city_name.setText(cityName1);


        sendRequestWithHttpClient();


    }


    private void sendRequestWithHttpClient() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet("http://v.juhe.cn/weather/index?format=2&cityname=" + cityName1 + "&key=" + KEY);
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        HttpEntity entity = httpResponse.getEntity();
                        String reponse = EntityUtils.toString(entity, "utf-8");

                        parseJSONWithJSONObject(reponse);
                    }


                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void parseJSONWithJSONObject(String response) {
        try {
            JSONObject json = new JSONObject(response);
            JSONObject result = json.getJSONObject("result");
            JSONObject sk = result.getJSONObject("sk");
            JSONObject today = result.getJSONObject("today");
            JSONArray array = result.getJSONArray("future");
            JSONObject tomorrow = array.getJSONObject(1);
            temp = sk.getString("temp");
            time = sk.getString("time");
            humidity = sk.getString("humidity");
            today_tmp = today.getString("temperature");
            today_weatherInfo = today.getString("weather");
            tomorrow_temp = tomorrow.getString("temperature");
            tomorrow_weatherInfo = tomorrow.getString("weather");


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    current_temp.setText(temp);
                    publish_time.setText(time);
                    city_humidity.setText(humidity);
                    today_temper.setText(today_tmp);
                    tomorrow_temper.setText(tomorrow_temp);
                    today_weather.setText(today_weatherInfo);
                    tomorrow_weather.setText(tomorrow_weatherInfo);
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
