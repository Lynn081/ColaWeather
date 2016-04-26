package com.example.lynn.colaweather.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;


import com.example.lynn.colaweather.R;
import com.example.lynn.colaweather.model.ColaWeatherDB;

import java.util.ArrayList;
import java.util.List;


public class SearchCityActivity extends Activity implements View.OnClickListener {


    private AutoCompleteTextView search_city;
    private Button searchBtn;
    private ArrayAdapter<String> adapter;
    private SQLiteDatabase db;

// 存放从数据库查询出来的数据

    public static List<String> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_city);

        search_city = (AutoCompleteTextView) findViewById(R.id.add_city);
        searchBtn= (Button) findViewById(R.id.search);
        search_city.setThreshold(1);

        db = SQLiteDatabase.openOrCreateDatabase(ColaWeatherDB.DB_PATH + "/" + ColaWeatherDB.DB_NAME, null);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, queryDatas());
        search_city.setAdapter(adapter);
        searchBtn.setOnClickListener(this);

    }


    /**
     * 把三张表的数据查询出来
     *
     * @return
     */

    public ArrayList<String> queryDatas() {
        Cursor cursor = db.rawQuery("select province from province", null);
        while (cursor.moveToNext()) {
            String province_name = cursor.getString(cursor.getColumnIndex("province"));
            dataList.add(province_name);
        }
        Cursor cursor1 = db.rawQuery("select city from city", null);

        while (cursor1.moveToNext()) {
            String city_name = cursor1.getString(cursor1.getColumnIndex("city"));
            dataList.add(city_name);

        }
        Cursor cursor2 = db.rawQuery("select area from area", null);
        while (cursor2.moveToNext()) {
            String area_name = cursor2.getString(cursor2.getColumnIndex("area"));
            dataList.add(area_name);

        }
        return (ArrayList<String>) dataList;
    }

    /**
     * 输入框输入时  判断有没有用户输入的这个城市
     */

    public boolean haveCity(String cityName) {

        if (cityName != null) {

            Cursor cursor = db.rawQuery("select province from province where province='" + cityName+"'", null);
            if (cursor.moveToFirst()) {
                return true;
            } else {
                Cursor cursor1 = db.rawQuery("select city from city where city='" + cityName+"'", null);
                if (cursor1 .moveToFirst()) {
                    return true;
                } else {
                    Cursor cursor2 = db.rawQuery("select area from area where area='" + cityName+"'", null);
                    if (cursor2 .moveToFirst()) {
                        return true;
                    } else
                        return false;
                }

            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        String getCityName=search_city.getText().toString();
        boolean b=haveCity(getCityName);
        if(b){
            Intent intent=new Intent(SearchCityActivity.this,weatherInfo.class);
            intent.putExtra("city_name",getCityName);
            startActivity(intent);
        }else{
            new AlertDialog.Builder(SearchCityActivity.this)
                    .setTitle("提示")
                    .setMessage("您所输入的城市不存在，请重新输入")
                    .setPositiveButton("你说的都对", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setResult(RESULT_OK);
                        }
                    }).show();
        }
    }
}
