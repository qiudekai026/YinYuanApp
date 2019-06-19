package cn.edu.gdpt.yinyuan171026qdk;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION=1;
    private static String DB_NAME="topline.db";
    public static final String U_USERINFO="userinfo";
    public SQLiteHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+U_USERINFO+"("
                +"_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"userName VARCHAR,"
                +"password VAECHAE,"
                +"nickName VAECHAE,"
                +"sex VAECHAE,"
                +"signature VAECHAE,"
                +"head VAECHAE "
                +")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+U_USERINFO);
        onCreate(db);
    }
}
