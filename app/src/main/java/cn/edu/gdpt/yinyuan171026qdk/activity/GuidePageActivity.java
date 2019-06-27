package cn.edu.gdpt.yinyuan171026qdk.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import cn.edu.gdpt.yinyuan171026qdk.MainActivity;
import cn.edu.gdpt.yinyuan171026qdk.R;

public class GuidePageActivity extends AppCompatActivity {
    private static final  int TIME = 2000;
    private Handler mHander = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            goHome();
        };
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_page);
        mHander.sendEmptyMessageDelayed(0,TIME);
    }
    private void goHome() {
        Intent intent = new Intent(GuidePageActivity.this,MainActivity.class);
        startActivity(intent);
        this.finish();
    }

}
