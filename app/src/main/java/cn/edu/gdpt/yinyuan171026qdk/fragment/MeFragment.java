package cn.edu.gdpt.yinyuan171026qdk.fragment;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.gdpt.yinyuan171026qdk.R;
import cn.edu.gdpt.yinyuan171026qdk.activity.LoginActivity;
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
    }

    private void setLoginParams(boolean isLogin) {
    }

    private void setListener() {
        iv_avatar.setOnClickListener(this);
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
}
