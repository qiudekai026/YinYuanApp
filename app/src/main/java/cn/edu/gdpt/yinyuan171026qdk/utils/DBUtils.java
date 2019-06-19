package cn.edu.gdpt.yinyuan171026qdk.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import cn.edu.gdpt.yinyuan171026qdk.SQLiteHelper;

public class DBUtils {
    private static DBUtils instance=null;
    private static SQLiteHelper helper;
    private static SQLiteDatabase db;
    public DBUtils(Context context){
        helper=new SQLiteHelper(context);
        db=helper.getWritableDatabase();
    }
    public static DBUtils getInstance(Context context){
        if (instance==null){
            instance=new DBUtils(context);
        }
        return instance;
    }
}
