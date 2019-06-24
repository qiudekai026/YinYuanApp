package cn.edu.gdpt.yinyuan171026qdk.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.edu.gdpt.yinyuan171026qdk.R;

public class ShenFenZhengActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_xingming;
    private EditText shenfenzheng;
    private Button send_shenfenzheng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shen_fen_zheng);
        initView();
    }

    private void initView() {
        et_xingming = (EditText) findViewById(R.id.et_xingming);
        shenfenzheng = (EditText) findViewById(R.id.shenfenzheng);
        send_shenfenzheng = (Button) findViewById(R.id.send_shenfenzheng);

        send_shenfenzheng.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_shenfenzheng:

                break;
        }
    }

    private void submit() {
        // validate
        String xingming = et_xingming.getText().toString().trim();
        if (TextUtils.isEmpty(xingming)) {
            Toast.makeText(this, "请输入您的姓名", Toast.LENGTH_SHORT).show();
            return;
        }

        String shenfenzhengString = shenfenzheng.getText().toString().trim();
        if (TextUtils.isEmpty(shenfenzhengString)) {
            Toast.makeText(this, "请输入您的身份证", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}
