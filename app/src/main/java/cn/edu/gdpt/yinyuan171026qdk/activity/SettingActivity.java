package cn.edu.gdpt.yinyuan171026qdk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.edu.gdpt.yinyuan171026qdk.MainActivity;
import cn.edu.gdpt.yinyuan171026qdk.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView kefu;
    private TextView yijian;
    private TextView jubao;
    private TextView guanyu;
    private Button tuichu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        kefu = (TextView) findViewById(R.id.kefu);
        yijian = (TextView) findViewById(R.id.yijian);
        jubao = (TextView) findViewById(R.id.jubao);
        guanyu = (TextView) findViewById(R.id.guanyu);
        tuichu = (Button) findViewById(R.id.tuichu);

        tuichu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tuichu:
                Intent intent=new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
