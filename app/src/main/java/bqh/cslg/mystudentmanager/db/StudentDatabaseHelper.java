package bqh.cslg.mystudentmanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by baiqihui on 2016/10/13.
 */

public class StudentDatabaseHelper extends SQLiteOpenHelper {

    public static String CREATE_STUDENTINFO = "create table student ("
            + "number text primary key, "
            + "gender text , "
            + "name text,"
            + "birth text,"
            + "native_place text,"
            + "specialty text,"
            + "phone text)";

    public StudentDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENTINFO);
        //预置一些数据
        ContentValues values = new ContentValues();
        values.put("number","092113101");
        values.put("gender","男");
        values.put("name","白齐辉");
        values.put("birth","1993-09-26");
        values.put("native_place","江苏省");
        values.put("specialty","软件工程");
        values.put("phone","18852951127");
        db.insert("student",null,values);

        values.clear();
        values.put("number","092113102");
        values.put("gender","男");
        values.put("name","诚意");
        values.put("birth","1993-09-26");
        values.put("native_place","江苏省");
        values.put("specialty","软件工程");
        values.put("phone","18852951127");
        db.insert("student",null,values);

        values.clear();
        values.put("number","092113103");
        values.put("gender","男");
        values.put("name","郭世昌");
        values.put("birth","1993-09-26");
        values.put("native_place","江苏省");
        values.put("specialty","软件工程");
        values.put("phone","18852951127");
        db.insert("student",null,values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
