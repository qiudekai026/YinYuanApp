package cn.edu.gdpt.yinyuan171026qdk.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.gdpt.yinyuan171026qdk.R;
import cn.edu.gdpt.yinyuan171026qdk.UpdateUserInfoReceiver;
import cn.edu.gdpt.yinyuan171026qdk.activity.LoginActivity;
import cn.edu.gdpt.yinyuan171026qdk.utils.DBUtils;
import cn.edu.gdpt.yinyuan171026qdk.utils.UtilsHelper;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment implements View.OnClickListener {
    private CircleImageView iv_avatar;
    private boolean isLogin=false;
    private View view;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private IntentFilter filter;
    private UpdateUserInfoReceiver updateUserInfoReceiver;

    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_me, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        iv_avatar=(CircleImageView)view.findViewById(R.id.iv_avatar);
        collapsingToolbarLayout=(CollapsingToolbarLayout)view.findViewById(R.id.collapsing_tool_bar);
        isLogin= UtilsHelper.readLoginStatus(getActivity());
        setLoginParams(isLogin);
        setListener();
        receiver();
    }

    private void receiver() {
        updateUserInfoReceiver=new UpdateUserInfoReceiver(new UpdateUserInfoReceiver.BaseOnReceiveMsgListenter() {
            @Override
            public void onReceiveMsg(Context context, Intent intent) {
                String action=intent.getStringExtra(UpdateUserInfoReceiver.ACTION.UPDATE_USERINFO);
                if (UpdateUserInfoReceiver.INTENT_TYPE.UPDATE_HEAD.equals(action)){
                    String type=intent.getStringExtra(UpdateUserInfoReceiver.INTENT_TYPE.TYPE_NAME);
                    if (UpdateUserInfoReceiver.INTENT_TYPE.UPDATE_HEAD.equals(type)){
                        String head=intent.getStringExtra("head");
                        Bitmap bt= BitmapFactory.decodeFile(head);
                        if (bt!=null){
                            Drawable drawable=new BitmapDrawable(bt);
                            iv_avatar.setImageDrawable(drawable);
                        }else {
                            iv_avatar.setImageResource(R.drawable.touxiang);
                        }
                    }
                }
            }
        });
        filter=new IntentFilter(UpdateUserInfoReceiver.ACTION.UPDATE_USERINFO);
        getActivity().registerReceiver(updateUserInfoReceiver,filter);
    }

    private void setLoginParams(boolean isLogin) {
        if (isLogin){
            String userName=UtilsHelper.readLoginUserName(getActivity());
            collapsingToolbarLayout.setTitle(userName);
            String head= DBUtils.getInstance(getActivity()).getUserHead(userName);
            Bitmap bt=BitmapFactory.decodeFile(head);
            if (bt!=null){
                Drawable drawable=new BitmapDrawable(bt);
                iv_avatar.setImageDrawable(drawable);
            }else {
                iv_avatar.setImageResource(R.drawable.touxiang);
            }
        }else {
            iv_avatar.setImageResource(R.drawable.touxiang);
            collapsingToolbarLayout.setTitle("点击登录");
        }
    }

    private void setListener() {
        iv_avatar.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (updateUserInfoReceiver != null){
            getActivity().unregisterReceiver(updateUserInfoReceiver);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_avatar://
                Intent intent=new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            boolean isLogin=data.getBooleanExtra("isLogin",false);
            setLoginParams(isLogin);
            this.isLogin=isLogin;
        }
    }
}
