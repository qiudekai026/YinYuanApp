package cn.edu.gdpt.yinyuan171026qdk.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

import cn.edu.gdpt.yinyuan171026qdk.R;
import cn.edu.gdpt.yinyuan171026qdk.utils.MD5Utils;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_user_name;
    private EditText et_password;
    private Button btn_register;
    private String userName,psw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_register = (Button) findViewById(R.id.btn_register);

        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                userName=et_user_name.getText().toString().trim();
                psw=et_password.getText().toString().trim();
                if (TextUtils.isEmpty(userName)){
                    Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(psw)){
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if (isExistUserName(userName)){
                    Toast.makeText(this, "此用户名已存在", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                    saveRegisterInfo(userName,psw);
                    Intent data=new Intent();
                    data.putExtra("userName",userName);
                    setResult(RESULT_OK,data);
                    RegisterActivity.this.finish();
                }
                break;
        }
    }
    private boolean isExistUserName(String userName){
        boolean has_userName=false;
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        String spPsw=sp.getString(userName,"");
        if (!TextUtils.isEmpty(spPsw)){
            has_userName=true;
        }
        return has_userName;
    }

    private void saveRegisterInfo(String userName,String psw){
        String md5Psw= MD5Utils.md5(psw);
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(userName,md5Psw);
        editor.commit();
    }
}
