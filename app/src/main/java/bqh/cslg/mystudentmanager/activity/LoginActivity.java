package bqh.cslg.mystudentmanager.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bqh.cslg.mystudentmanager.R;
import bqh.cslg.mystudentmanager.base.BaseActivity;

/**
 * Created by baiqihui on 2016/10/13.
 */

public class LoginActivity extends BaseActivity {

    EditText password;
    SharedPreferences prefs;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        prefs = getSharedPreferences("configuration",MODE_PRIVATE);
        password = (EditText) findViewById(R.id.et_login_password);
        loginBtn = (Button) findViewById(R.id.btn_login_log_in);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userPass = prefs.getString("password","123");
                if (userPass.equals(password.getText().toString().trim())){
                    Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
