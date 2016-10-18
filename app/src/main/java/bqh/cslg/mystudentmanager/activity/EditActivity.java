package bqh.cslg.mystudentmanager.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import bqh.cslg.mystudentmanager.R;
import bqh.cslg.mystudentmanager.base.BaseActivity;
import bqh.cslg.mystudentmanager.db.StudentDBUtils;
import bqh.cslg.mystudentmanager.model.Student;

/**
 * Created by baiqihui on 2016/10/13.
 */

public class EditActivity extends BaseActivity implements View.OnClickListener{

    //当前学生信息
    Student currentStudent;
    //控件
    Button btnBack;
    Button btnSave;
    EditText editNumber;
    EditText editName;
    TableRow tbGender;
    TextView tvGender;
    TableRow tbBirth;
    TextView tvBirth;
    EditText editPlace;
    EditText editSpecial;
    EditText editMobile;
    TextView tvDelete;
    //性别列表
    String[] gender_list = {"男","女"};

    //数据库操作
    StudentDBUtils dbUtils;
    //所有学生的学号
    List<String> studentNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.act_edit_info);

        dbUtils = StudentDBUtils.getInstance(this);
        studentNumbers = dbUtils.getAllNumbers();
        initView();
        checkIntent();
    }

    private void initView() {
        btnBack = (Button) findViewById(R.id.btn_edit_back);
        btnSave = (Button) findViewById(R.id.btn_edit_sure);
        editNumber = (EditText) findViewById(R.id.et_edit_number);
        editName = (EditText) findViewById(R.id.et_edit_name);
        tbGender = (TableRow) findViewById(R.id.tr_edit_gender);
        tbBirth = (TableRow) findViewById(R.id.tr_edit_birth);
        editPlace = (EditText) findViewById(R.id.et_edit_native_place);
        editSpecial = (EditText) findViewById(R.id.et_edit_specialty);
        editMobile = (EditText) findViewById(R.id.et_edit_phone);
        tvDelete = (TextView) findViewById(R.id.tv_edit_delete);
        tvGender = (TextView) findViewById(R.id.tv_edit_gender);
        tvBirth = (TextView) findViewById(R.id.tv_edit_birth);

        btnBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        tbGender.setOnClickListener(this);
        tbBirth.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
    }

    /**
     * 判断是点击学生信息进来的还是点击“添加”按钮进来的
     */
    private void checkIntent() {
        Intent intent = getIntent();
        currentStudent = (Student) intent.getSerializableExtra("studenInfo");
        if (currentStudent!=null){
            initData(currentStudent);
        }else {
            //设置“删除”按钮不可见
            tvDelete.setVisibility(View.GONE);
        }
    }

    /**
     * 因为是点击学生进来的，所以要设置学生信息
     */
    private void initData(Student student) {
        editNumber.setText(student.getNumber());
        editName.setText(student.getName());
        tvGender.setText(student.getGender());
        tvBirth.setText(student.getBirth());
        editPlace.setText(student.getNative_place());
        editSpecial.setText(student.getSpecialty());
        editMobile.setText(student.getPhone());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_edit_back:
                //返回按钮
                finish();
                break;
            case R.id.btn_edit_sure:
                //添加按钮
                saveStudent();
                break;
            case R.id.tv_edit_delete:
                //删除按钮
                deleteStudent();
                break;
            case R.id.tr_edit_gender:
                //点击性别按钮
                showGenderDialog();
                break;
            case R.id.tr_edit_birth:
                //点击了生日按钮
                showBirthDialog();
                break;
        }
    }

    //保存学生信息
    private void saveStudent() {
        String newNumber = editNumber.getText().toString().trim();
        String newNname = editName.getText().toString().trim();
        String newGender = tvGender.getText().toString().trim();
        String newBirth = tvBirth.getText().toString().trim();
        String newPlace = editPlace.getText().toString().trim();
        String newSpecial = editSpecial.getText().toString().trim();
        String newPhone =editMobile.getText().toString().trim();
        if (tvDelete.getVisibility() == View.VISIBLE){
            Student oldStudent = (Student) getIntent().getSerializableExtra("studenInfo");
            if (newNumber.equals(oldStudent.getNumber())){
                if (!(TextUtils.isEmpty(newNumber)||TextUtils.isEmpty(newNname)||TextUtils.isEmpty(newGender)||
                        TextUtils.isEmpty(newBirth)||TextUtils.isEmpty(newPlace)||TextUtils.isEmpty(newSpecial)||
                        TextUtils.isEmpty(newPhone))){
                    Student student = new Student();
                    student.setNumber(newNumber);
                    student.setName(newNname);
                    student.setGender(newGender);
                    student.setBirth(newBirth);
                    student.setNative_place(newPlace);
                    student.setSpecialty(newSpecial);
                    student.setPhone(newPhone);
                    dbUtils.saveStudent(student);
                    Toast.makeText(this,"更新成功",Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(this,"所有信息不能为空",Toast.LENGTH_SHORT).show();
                }
            }else {
                if (!(TextUtils.isEmpty(newNumber)||TextUtils.isEmpty(newNname)||TextUtils.isEmpty(newGender)||
                        TextUtils.isEmpty(newBirth)||TextUtils.isEmpty(newPlace)||TextUtils.isEmpty(newSpecial)||
                        TextUtils.isEmpty(newPhone))){
                    if (studentNumbers.contains(newNumber)){
                        Toast.makeText(this,"学号与数据库中学号重复，请检查",Toast.LENGTH_SHORT).show();
                    }else {
                        Student student = new Student();
                        student.setNumber(newNumber);
                        student.setName(newNname);
                        student.setGender(newGender);
                        student.setBirth(newBirth);
                        student.setNative_place(newPlace);
                        student.setSpecialty(newSpecial);
                        student.setPhone(newPhone);
                        dbUtils.saveStudent(student);
                        Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else {
                    Toast.makeText(this,"所有信息不能为空",Toast.LENGTH_SHORT).show();
                }

            }
        }else if (tvDelete.getVisibility() == View.GONE){
            if (!(TextUtils.isEmpty(newNumber)||TextUtils.isEmpty(newNname)||TextUtils.isEmpty(newGender)||
                    TextUtils.isEmpty(newBirth)||TextUtils.isEmpty(newPlace)||TextUtils.isEmpty(newSpecial)||
                    TextUtils.isEmpty(newPhone))){
                if (studentNumbers.contains(newNumber)){
                    Toast.makeText(this,"学号与数据库中学号重复，请检查",Toast.LENGTH_SHORT).show();
                }else {
                    Student student = new Student();
                    student.setNumber(newNumber);
                    student.setName(newNname);
                    student.setGender(newGender);
                    student.setBirth(newBirth);
                    student.setNative_place(newPlace);
                    student.setSpecialty(newSpecial);
                    student.setPhone(newPhone);
                    dbUtils.saveStudent(student);
                    Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }else {
                Toast.makeText(this,"所有信息不能为空",Toast.LENGTH_SHORT).show();
            }

        }
    }

    /**
     * 从数据库中删除当前学生信息
     */
    private void deleteStudent() {
        final Student student = new Student();
        String number = editNumber.getText().toString();
        student.setNumber(number);
        if (number!=null){
            //创建一个确认对话框来提示用户是否删除
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("确认删除？");
            builder.setMessage("确认删除此学生信息？"+"\n"+"学号："+number);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dbUtils.deleteStudent(student);
                    finish();
                    Intent intent = new Intent(EditActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }else {
            Toast.makeText(this,"学号不能为空",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 显示性别选择对话框
     */
    private void showGenderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择性别");
        builder.setItems(gender_list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvGender.setText("   "+gender_list[which]);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * 显示日期选择对话框
     */
    private void showBirthDialog() {
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        Date myDate = new Date();
        cal.setTime(myDate);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tvBirth.setText("   "+year+"年"+(month+1)+"月"+dayOfMonth+"日");
            }
        },year,month,day).show();
    }
}
