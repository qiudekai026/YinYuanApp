package cn.edu.gdpt.yinyuan171026qdk.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class UtilsHelper {
    public static boolean readLoginStatus(Context context){
        SharedPreferences sp=context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        boolean isLogin=sp.getBoolean("isLogin",false);
        return isLogin;
    }

    public static String readLoginUserName(Context context){
        SharedPreferences sp=context.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        String userName=sp.getString("loginUserName","");
        return userName;
    }
}
