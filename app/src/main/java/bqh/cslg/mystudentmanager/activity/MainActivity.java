package bqh.cslg.mystudentmanager.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bqh.cslg.mystudentmanager.R;
import bqh.cslg.mystudentmanager.adapter.RecyclerViewItemClickListener;
import bqh.cslg.mystudentmanager.adapter.ShowAdapter;
import bqh.cslg.mystudentmanager.base.BaseActivity;
import bqh.cslg.mystudentmanager.db.StudentDBUtils;
import bqh.cslg.mystudentmanager.model.Student;

/**
 * Created by baiqihui on 2016/10/13.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener{

    SharedPreferences prefs;
    StudentDBUtils dbUtils;
    //导航栏
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    //控件
    ImageView btnMenu;
    ImageView btnSearch;
    RecyclerView studentList;
    FloatingActionButton addStudent;

    //初始化学生信息列表所用
    List<Student> studentsDataList;
    ShowAdapter showAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.act_main);

        prefs = getSharedPreferences("configuration",MODE_PRIVATE);
        drawerLayout = (DrawerLayout) findViewById(R.id.dl_main);
        navigationView = (NavigationView) findViewById(R.id.main_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_my_info:
                        Intent intent1 = new Intent(MainActivity.this,MyInfoActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_password:
                        showChangePassDialog();
                        break;
                    case R.id.nav_search:
                        showSearchTypeDialog();
                        break;
                    case R.id.nav_add:
                        Intent intent = new Intent(MainActivity.this,EditActivity.class);
                        startActivity(intent);
                        break;
                }
            drawerLayout.closeDrawers();
                return true;
            }
        });
        btnMenu = (ImageView) findViewById(R.id.btn_menu);
        btnSearch = (ImageView) findViewById(R.id.btn_serach);
        studentList = (RecyclerView) findViewById(R.id.student_list);
        addStudent = (FloatingActionButton) findViewById(R.id.add_student);
        btnMenu.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        addStudent.setOnClickListener(this);
        //初始化数据库
        dbUtils = StudentDBUtils.getInstance(this);
        //初始化RecyclerView显示学生数据
        initRecyclerView();

    }

    /**
     * 修改密码的dialog
     */
    private void showChangePassDialog() {
        TableLayout tlPassword = (TableLayout) getLayoutInflater().inflate(R.layout.dialog_main_password,null);
        final EditText etOldPassword = (EditText) tlPassword.findViewById(R.id.et_main_old_password);
        final EditText etNewPassword = (EditText) tlPassword.findViewById(R.id.et_main_new_password);
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("修改登录密码")
                .setView(tlPassword)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String oldPassword = prefs.getString("password", "123");
                        if (etOldPassword.getText().toString().equals(oldPassword)){
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("password",etOldPassword.getText().toString());
                            editor.commit();
                            Toast.makeText(MainActivity.this, "修改密码成功！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "原密码错误！", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("取消",null)
                .create()
                .show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //初始化RecyclerView显示学生数据
        initRecyclerView();
    }

    private void initRecyclerView() {
        studentsDataList = dbUtils.loadStudent();
        showAdapter = new ShowAdapter(this,studentsDataList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        studentList.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        studentList.setAdapter(showAdapter);
        //设置增加或删除条目的动画
        studentList.setItemAnimator(new DefaultItemAnimator());

        showAdapter.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                //Toast.makeText(MainActivity.this,data,Toast.LENGTH_SHORT).show();
                int position = Integer.parseInt(data);
                //封装所点击的同学的信息
                Student student = studentsDataList.get(position);
                Intent intent = new Intent(MainActivity.this,EditActivity.class);
                intent.putExtra("studenInfo",student);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, String data) {
                int position = Integer.parseInt(data);
                //封装所点击的同学的信息
                final  Student student = studentsDataList.get(position);
                //创建一个确认对话框来提示用户是否删除
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("确认删除？");
                builder.setMessage("确认删除此学生信息？"+"\n"+"学号："+student.getNumber());
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbUtils.deleteStudent(student);
                        initRecyclerView();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_menu:
                showNavigationView();
                break;
            case R.id.btn_serach:
                showSearchTypeDialog();
                break;
            case R.id.add_student:
                Intent intent = new Intent(MainActivity.this,EditActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 显示导航栏
     */
    private void showNavigationView() {
        drawerLayout.openDrawer(navigationView);
    }

    /**
     * 显示列表对话框，选择搜索的类型
     */
    private void showSearchTypeDialog() {
        String[] type = {"学号","姓名"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("搜索类型");
        builder.setItems(type, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        Intent intent1 = new Intent(MainActivity.this,SearchActivity.class);
                        intent1.putExtra("type","number");
                        startActivity(intent1);
                        break;
                    case 1:
                        Intent intent2 = new Intent(MainActivity.this,SearchActivity.class);
                        intent2.putExtra("type","name");
                        startActivity(intent2);
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
