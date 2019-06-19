package cn.edu.gdpt.yinyuan171026qdk.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdpt.yinyuan171026qdk.R;
import cn.edu.gdpt.yinyuan171026qdk.utils.MD5Utils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_user_name;
    private EditText et_password;
    private Button btn_login;
    private TextView tv_register;
    private String userName,psw,spPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        tv_register = (TextView) findViewById(R.id.tv_register);

        btn_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                userName=et_user_name.getText().toString().trim();
                psw=et_password.getText().toString().trim();
                String md5Psw= MD5Utils.md5(psw);
                spPsw=readPsw(userName);
                if (TextUtils.isEmpty(userName)){
                    Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(psw)){
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if (md5Psw.equals(spPsw)){
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                    sqvrLoginStatus(true,userName);
                    Intent data=new Intent();
                    data.putExtra("isLogin",true);
                    setResult(RESULT_OK,data);
                    LoginActivity.this.finish();
                    return;
                }else if ((!TextUtils.isEmpty(spPsw)&&!md5Psw.equals(spPsw))){
                    Toast.makeText(this, "输入的用户名与密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Toast.makeText(this, "此用户名不存在", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_register:
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,1);
                break;
        }
    }

    private String readPsw(String userName){
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        return sp.getString(userName,"");
    }
    private void sqvrLoginStatus(boolean status,String userName){
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("isLogin",status);
        editor.putString("LoginUserName",userName);
        editor.commit();
    }
}
