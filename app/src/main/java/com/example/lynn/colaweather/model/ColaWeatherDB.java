package com.example.lynn.colaweather.model;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import com.example.lynn.colaweather.R;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by Administrator on 2016/4/21.
 */
public class ColaWeatherDB {




    public static final String DB_NAME="test"; //数据库的名字

    public static final String PACKAGE_NAME="com.example.lynn.colaweather";
    public static final String DB_PATH ="/data"+ Environment.getDataDirectory().getAbsolutePath()
            +"/"+PACKAGE_NAME;                      //手机存放数据库的地方
    public final int BUFF_SIZE=400000;




    /**
     * 数据库版本
     */
    private SQLiteDatabase database;
    private Context context;



    public ColaWeatherDB(Context context){
       this.context=context;
    }
    public void openDatabase(){
        this.database=this.openDatabase(DB_PATH+"/"+DB_NAME);

    }

    private SQLiteDatabase openDatabase(String dbFile){
        try{
            if(!(new File(dbFile)).exists()){      //如果数据库文件不存在就就开始导入
                InputStream in=this.context.getResources().openRawResource(R.raw.city);
                FileOutputStream fOut=new FileOutputStream(dbFile);
                byte[] buffer=new byte[BUFF_SIZE];
                int count=0;
                while((count=in.read(buffer))>0){
                        fOut.write(buffer,0,count);

                }
                fOut.close();
                in.close();
            }
            SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(dbFile,null);
            return db;


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void closeDatabase(){
        this.database.close();
    }



}
