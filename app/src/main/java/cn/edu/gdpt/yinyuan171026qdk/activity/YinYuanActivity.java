package cn.edu.gdpt.yinyuan171026qdk.activity;

import android.graphics.Color;
import android.os.Build;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.*;


import cn.edu.gdpt.yinyuan171026qdk.R;

public class YinYuanActivity extends AppCompatActivity {
    public Spinner constellation;
    public Spinner time;
    public String selclt;
    public String seltime;
    public TextView tvresult;
    public ImageView bingpicimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yin_yuan);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_yin_yuan);
        bingpicimg=(ImageView)findViewById(R.id.bing_pic_img);
        bingpicimg.setAlpha(0.6f);
         tvresult = (TextView) findViewById(R.id.tvresult);
        constellation = (Spinner) findViewById(R.id.spinner1);
        time = (Spinner) findViewById(R.id.spinner2);
        loadBingPic();
        constellation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] clt = getResources().getStringArray(R.array.constellation);
                selclt = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] ti = getResources().getStringArray(R.array.time);
                seltime = ti[i];
                //seltime=adapterView.getItemAtPosition(i).toString();
                Toast.makeText(YinYuanActivity.this, seltime, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void onClick(View view) {
        if (view.getId() == R.id.send_request) {
            loadBingPic();
            sendRequestWithOkHttp();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadBingPic();
    }

    @Override
    protected void onStop() {
        super.onStop();
        deleteFilesByDirectory(new File("/data/data/"
                + getApplicationContext().getPackageName() + "/cache/image_manager_disk_cache"));
        Toast.makeText(YinYuanActivity.this, "stop", Toast.LENGTH_LONG).show();

    }

    public void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
                    formBody.add("consName", selclt);//传递键值对参数
                    formBody.add("type", seltime);
                    formBody.add("key", "33475929dff1fdaeb8266750f0444eba");
                    Request request = new Request.Builder()
                            .url("http://web.juhe.cn:8080/constellation/getAll")
                            .post(formBody.build())
                            .build();
                    Response response = client.newCall(request).execute();
                    String responsedata = response.body().string();
                    Gson gson = new Gson();
                    GetResult getResult = gson.fromJson(responsedata, GetResult.class);
                    show(getResult, seltime);
                    Log.d("gg", responsedata);
                    //showdata(responsedata);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    public void show(final GetResult getResult, final String time) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (time) {
                    case "today":
                        tvresult.setText("\n" + "星座:" + getResult.getName() + "\n" + "\n"
                                + "日期:" + getResult.getDatetime() + "\n" + "\n"
                                + "综合指数:" + getResult.getAll() + "\n" + "\n"
                                + "幸运色:" + getResult.getColor() + "\n" + "\n"
                                + "健康指数：" + getResult.getHealth() + "\n" + "\n"
                                + "爱情指数:" + getResult.getLove() + "\n" + "\n"
                                + "速配星座:" + getResult.getQFriend() + "\n" + "\n"
                                + "今日概述:" + getResult.getSummary() + "\n" + "\n");
                        break;
                    case "tomorrow":
                        tvresult.setText("\n" + "星座:" + getResult.getName() + "\n" + "\n"
                                + "日期:" + getResult.getDatetime() + "\n"
                                + "\n" + "综合指数:" + getResult.getAll() + "\n" + "\n"
                                + "幸运色:" + getResult.getColor() + "\n" + "\n"
                                + "健康指数：" + getResult.getHealth() + "\n" + "\n"
                                + "爱情指数:" + getResult.getLove() + "\n" + "\n"
                                + "速配星座:" + getResult.getQFriend() + "\n" + "\n"
                                + "今日概述:" + getResult.getSummary() + "\n" + "\n");
                        break;
                    case "week":
                        tvresult.setText("\n" + "星座:" + getResult.getName() + "\n" + "\n"
                                + "日期:" + getResult.getDate() + "\n" + "\n"
                                + "第" + getResult.getWeekth() + "周" + "\n" + "\n"
                                + getResult.getLove() + "\n" + "\n");
                        break;
                    case "month":
                        tvresult.setText("\n" + "星座:" + getResult.getName() + "\n" + "\n"
                                + "日期:" + getResult.getDate() + "\n" + "\n"
                                + "综合运势:" + getResult.getAll() + "\n" + "\n"
                                + "健康运势：" + getResult.getHealth() + "\n" + "\n"
                                + "爱情运势:" + getResult.getLove() + "\n" + "\n");
                        break;
//                            case "year":
//                                tvresult.setText(getResult.getName());
//                                break;
                }

            }
        });
    }

    private void loadBingPic() {
        String url = "https://www.dujin.org/sys/bing/1920.php";
        Glide.with(YinYuanActivity.this).load(url).centerCrop().into(bingpicimg);
    }


    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

}
