package cn.edu.gdpt.yinyuan171026qdk.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.edu.gdpt.yinyuan171026qdk.R;

public class ShenFenZhengActivity extends AppCompatActivity {

    private EditText et_xingming;
    private EditText shenfenzheng;
    TextView show_area, show_sex, show_bitrhday, show_reason;
    public static final String LOG_TAG = ShenFenZhengActivity.class.getSimpleName();
    public static final String INFO_URL = "http://apis.juhe.cn/idcard/index?cardno=";
    public static final String INPUT_URL_M = "&dtype=json&key=ce51f9256e1280e5885ca20e4ac50b29";
    String area;
    String sex;
    String birthday;
    int resultcode;
    String reason;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shen_fen_zheng);
        initView();
    }

    private void initView() {
        et_xingming = (EditText) findViewById(R.id.et_xingming);
        shenfenzheng = (EditText) findViewById(R.id.shenfenzheng);
        show_area = (TextView) findViewById(R.id.show_area);
        show_sex = (TextView) findViewById(R.id.show_sex);
        show_bitrhday = (TextView) findViewById(R.id.show_birthday);
        show_reason = (TextView) findViewById(R.id.show_reason);
    }

    public void search(View view) {
        makeHttpRequest();
        show_area.setText("");
        show_sex.setText("");
        show_reason.setText("");
        show_bitrhday.setText("");
    }

    private void makeHttpRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                String id = shenfenzheng.getText().toString();
                try {
                    URL url = new URL(INFO_URL + id + INPUT_URL_M);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    showResponse(response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private void showResponse(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FunctionJson(s);
            }
        });
    }

    private void FunctionJson(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            resultcode = jsonObject.optInt("resultcode");
            JSONObject jsonObject2 = jsonObject.getJSONObject("result");
            area = jsonObject2.optString("area").toString();
            sex = jsonObject2.optString("sex").toString();
            birthday = jsonObject2.optString("birthday").toString();
            reason = jsonObject.optString("reason").toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (resultcode == 200) {
            show_area.setText("户籍：" + area);
            show_sex.setText("性别：" + sex);
            show_bitrhday.setText("出生年月：" + birthday);
            show_reason.setText("认证成功");
        } else {
            show_reason.setText("认证失败,请输入正确的身份证号");
        }
    }


    private void submit() {
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


