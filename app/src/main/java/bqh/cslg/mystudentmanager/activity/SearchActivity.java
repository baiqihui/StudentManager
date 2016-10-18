package bqh.cslg.mystudentmanager.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import bqh.cslg.mystudentmanager.R;
import bqh.cslg.mystudentmanager.adapter.SearchAdapter;
import bqh.cslg.mystudentmanager.adapter.ShowAdapter;
import bqh.cslg.mystudentmanager.base.BaseActivity;
import bqh.cslg.mystudentmanager.db.StudentDBUtils;

/**
 * Created by baiqihui on 2016/10/15.
 */

public class SearchActivity extends BaseActivity {

    Button goBack;
    EditText searchText;
    RecyclerView searchList;
    //搜索的类型
    String type;

    //数据库操作
    StudentDBUtils dbUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.act_search);

        dbUtils = StudentDBUtils.getInstance(this);
        initView();
        checkIntent();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        goBack = (Button) findViewById(R.id.btn_serach_front);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        searchText = (EditText) findViewById(R.id.et_search);
        searchList = (RecyclerView) findViewById(R.id.rv_search);
        searchList.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                refreshRecyclerView(s+"");
            }
        });
    }

    private void refreshRecyclerView(String s) {
        ArrayList<String> number = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();

        Cursor mCursor = null;
        switch (type){
            case "number":
                mCursor = dbUtils.getStudentsByNumber(s);
                break;
            case "name":
                mCursor = dbUtils.getStudentsByName(s);
        }
        int size = mCursor.getCount()< SearchAdapter.maxSize?mCursor.getCount():SearchAdapter.maxSize;
        while (true) {
            if (size-- == 0)
                break;
            mCursor.moveToNext();
            number.add(mCursor.getString(mCursor.getColumnIndex("number")));
            name.add(mCursor.getString(mCursor.getColumnIndex("name")));
        }
        mCursor.close();

        searchList.setAdapter(new SearchAdapter(SearchActivity.this,number,name));
    }

    /**
     * 根据Intent来判断搜索类型
     */
    private void checkIntent() {
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        switch (type){
            case "number":
                searchText.setHint("请输入你要搜索的学生学号");
                break;
            case "name":
                searchText.setHint("请输入你要搜索的学生姓名");
        }
    }
}
