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


import cn.edu.gdpt.yinyuan171026qdk.R;

public class SuanMingActivity extends AppCompatActivity implements View.OnClickListener {
    TextView yangli1, yinli1, wuxing1, chongsha1, baiji1, jishen1, yi1, xiongshen1, ji1;
    private Button send;
    private EditText name;
    public static final String LOG_TAG = ShenFenZhengActivity.class.getSimpleName();
    public static final String INFO_URL = "http://v.juhe.cn/laohuangli/d?date=";
    public static final String INPUT_URL_M = "&key=6e7b1ceafae4194a4307a4d40e0c525f";
    String yinli;
    String wuxing;
    String chongsha;
    String baiji;
    String jishen;
    String yi;
    String xiongshen;
    String ji;
    int resultcode;
    String reason;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suan_ming);
        initView();
    }

    private void initView() {
        yangli1 = (TextView) findViewById(R.id.yangli);
        yinli1= (TextView) findViewById(R.id.yinli);
        wuxing1 = (TextView) findViewById(R.id.wuxing);
        chongsha1 = (TextView) findViewById(R.id.chongsha);
        baiji1 = (TextView) findViewById(R.id.baiji);
        jishen1 = (TextView) findViewById(R.id.jishen);
        yi1 = (TextView) findViewById(R.id.yi);
        xiongshen1 = (TextView) findViewById(R.id.xiongshen);
        ji1 = (TextView) findViewById(R.id.ji);
        send = (Button) findViewById(R.id.send);
        name = (EditText) findViewById(R.id.name);

        send.setOnClickListener(this);

    }


    private void makeHttpRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                String id = name.getText().toString();
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

    private void FunctionJson(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            resultcode = jsonObject.optInt("resultcode");
            JSONObject jsonObject1 = jsonObject.getJSONObject("result");
            yinli = jsonObject1.optString("yinli").toString();
            wuxing = jsonObject1.optString("wuxing").toString();
            chongsha = jsonObject1.optString("chongsha").toString();
            baiji = jsonObject1.optString("baiji").toString();
            jishen= jsonObject1.optString("jishen").toString();
            yi= jsonObject1.optString("yi").toString();
            xiongshen = jsonObject1.optString("xiongshen").toString();
            ji = jsonObject1.optString("ji").toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (resultcode==0){
            yinli1.setText("阴历："+yinli);
            wuxing1.setText("五行："+wuxing);
            chongsha1.setText("冲煞："+chongsha);
            baiji1.setText("彭祖百忌："+baiji);
            jishen1.setText("吉神宜趋："+jishen);
            yi1.setText("宜："+yi);
            xiongshen1.setText("凶神宜忌："+xiongshen);
            ji1.setText("忌："+ji);
        }else if (resultcode!=0){
            yangli1.setText("查询失败");
        }
    }

    @Override
    public void onClick(View v) {
            makeHttpRequest();
            yangli1.setText("");
            yinli1.setText("");
            wuxing1.setText("");
            chongsha1.setText("");
            baiji1.setText("");
            jishen1.setText("");
            yi1.setText("");
            xiongshen1.setText("");
            ji1.setText("");

    }
}

