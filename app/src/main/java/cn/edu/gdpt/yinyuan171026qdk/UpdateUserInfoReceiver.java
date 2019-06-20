package cn.edu.gdpt.yinyuan171026qdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UpdateUserInfoReceiver extends BroadcastReceiver {
    public interface  ACTION{
        String UPDATE_USERINFO="update_userinfo";
    }

    public interface INTENT_TYPE{
        String TYPE_NAME="intent_name";
        String UPDATE_HEAD="update_head";
    }

    private BaseOnReceiveMsgListenter onReceiveMsgListenter;
    public UpdateUserInfoReceiver(BaseOnReceiveMsgListenter onReceiveMsgListenter){
        this.onReceiveMsgListenter=onReceiveMsgListenter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }

    public interface BaseOnReceiveMsgListenter{
        void onReceiveMsg(Context context,Intent intent);
    }
}
