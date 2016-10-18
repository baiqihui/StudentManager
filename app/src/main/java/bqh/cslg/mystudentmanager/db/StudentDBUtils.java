package bqh.cslg.mystudentmanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import bqh.cslg.mystudentmanager.model.Student;

/**
 * Created by baiqihui on 2016/10/13.
 */

public class StudentDBUtils {
    /**
     * 数据库名
     */
    public static final String DB_NAME = "Student_Info";

    /**
     * 数据库版本
     */
    public static final int VERSION = 1;

    private static StudentDBUtils studentDBUtils;

    private SQLiteDatabase db;

    /**
     * 构造方法私有话
     */
    private StudentDBUtils(Context context){
        StudentDatabaseHelper dbHelper = new StudentDatabaseHelper(context,DB_NAME,null,VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取StudentDBUtils实例
     */
    public synchronized static StudentDBUtils getInstance(Context context){
        if (studentDBUtils==null){
            studentDBUtils = new StudentDBUtils(context);
        }
        return studentDBUtils;
    }

    /**
     * 将Student实例存储到数据库
     */
    public boolean saveStudent(Student student){
        if (student!=null){
            ContentValues values = new ContentValues();
            values.put("number",student.getNumber());
            values.put("name",student.getName());
            values.put("gender",student.getGender());
            values.put("birth",student.getBirth());
            values.put("native_place",student.getNative_place());
            values.put("specialty",student.getSpecialty());
            values.put("phone",student.getPhone());
            db.insert("student",null,values);
            return true;
        }else {
            return false;
        }
    }

    /**
     * 从数据库读取所有学生信息
     */
    public List<Student> loadStudent(){
        List<Student> list = new ArrayList<>();
        Cursor cursor = db.query("student",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                Student student = new Student();
                student.setNumber(cursor.getString(cursor.getColumnIndex("number")));
                student.setName(cursor.getString(cursor.getColumnIndex("name")));
                student.setGender(cursor.getString(cursor.getColumnIndex("gender")));
                student.setBirth(cursor.getString(cursor.getColumnIndex("birth")));
                student.setNative_place(cursor.getString(cursor.getColumnIndex("native_place")));
                student.setSpecialty(cursor.getString(cursor.getColumnIndex("specialty")));
                student.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                list.add(student);
            }while (cursor.moveToNext());
        }
        if (cursor!=null){
            cursor.close();
        }
        return list;
    }

    /**
     * 删除指定学生信息
     */
    public boolean deleteStudent(Student student){
        if (student!=null){
            String id = student.getNumber();
            db.delete("student","number = ?",new String[]{id});
            return true;
        }else {
            return false;
        }
    }

    /**
     * 更新指定学生信息
     */
    public boolean updateStudent(Student student){
        if (student!=null){
            ContentValues values = new ContentValues();
            values.put("number",student.getNumber());
            values.put("name",student.getName());
            values.put("gender",student.getGender());
            values.put("birth",student.getBirth());
            values.put("native_place",student.getNative_place());
            values.put("specialty",student.getSpecialty());
            values.put("phone",student.getPhone());
            db.update("student",values,"number = ?",new String[]{student.getNumber()});
            return true;
        }else {
            return false;
        }
    }

    /**
     * 获取所有已有学生的学号
     */
    public List<String> getAllNumbers(){
        List<String> numbers = new ArrayList<>();
        String number = null;
        Cursor cursor = db.query("student",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                number = cursor.getString(cursor.getColumnIndex("number"));
                numbers.add(number);
            }while (cursor.moveToNext());
        }
        if (cursor!=null){
            cursor.close();
        }
        return numbers;
    }

    public Student getStudentByNumber(String number){
        Cursor cursor = db.query("student",null,"number = ?",new String[]{number},null,null,null);
        Student student = new Student();
        if (cursor.moveToNext()){
            student.setNumber(cursor.getString(cursor.getColumnIndex("number")));
            student.setName(cursor.getString(cursor.getColumnIndex("name")));
            student.setGender(cursor.getString(cursor.getColumnIndex("gender")));
            student.setBirth(cursor.getString(cursor.getColumnIndex("birth")));
            student.setNative_place(cursor.getString(cursor.getColumnIndex("native_place")));
            student.setSpecialty(cursor.getString(cursor.getColumnIndex("specialty")));
            student.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
        }
        return student;
    }

    public Cursor getStudentsByNumber(String number){
        Cursor cursor = db.query("student", null, "number like ?", new String[]{"%" + number + "%"}, null, null, null);
        return cursor;
    }

    public Cursor getStudentsByName(String name){
        Cursor cursor = db.query("student", null, "name like ?", new String[]{"%" + name + "%"}, null, null, null);
        return cursor;
    }
}
