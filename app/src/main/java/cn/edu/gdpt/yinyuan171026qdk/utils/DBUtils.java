package cn.edu.gdpt.yinyuan171026qdk.utils;

import android.content.Context;
import android.database.Cursor;
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

    public String getUserHead(String userName){
        String sql=" SELECT head From "+SQLiteHelper.U_USERINFO+" WHERE userName = ? ";
        Cursor cursor=db.rawQuery(sql,new String[]{userName});
        String head="";
        while (cursor.moveToNext()){
            head=cursor.getString(cursor.getColumnIndex("head"));
        }
        cursor.close();
        return head;
    }
}
