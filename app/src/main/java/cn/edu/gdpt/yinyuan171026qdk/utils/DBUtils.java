package cn.edu.gdpt.yinyuan171026qdk.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cn.edu.gdpt.yinyuan171026qdk.SQLiteHelper;
import cn.edu.gdpt.yinyuan171026qdk.bean.UserBean;

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

    public void saveUserInfo(UserBean bean){
        ContentValues cv=new ContentValues();
        cv.put("userName",bean.getUserName());
        cv.put("nickName",bean.getNickName());
        cv.put("sex",bean.getSex());
        cv.put("signature",bean.getSignature());
        db.insert(SQLiteHelper.U_USERINFO,null,cv);
    }

    public UserBean getUserInfo(String userName){
        String sql=" SELECT * FROM "+SQLiteHelper.U_USERINFO+" WHERE userName=? ";
        Cursor cursor=db.rawQuery(sql,new String[]{userName});
        UserBean bean=null;
        while (cursor.moveToNext()){
            bean=new UserBean();
            bean.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
            bean.setNickName(cursor.getString(cursor.getColumnIndex("nickName")));
            bean.setSex(cursor.getString(cursor.getColumnIndex("sex")));
            bean.setSignature(cursor.getString(cursor.getColumnIndex("signature")));
            bean.setHead(cursor.getString(cursor.getColumnIndex("head")));
        }
        cursor.close();
        return bean;
    }

    public void updateUserInfo(String key,String value,String userName){
        ContentValues cv=new ContentValues();
        cv.put(key,value);
        db.update(SQLiteHelper.U_USERINFO,cv,"userName=?",new String[]{userName});
    }
}
