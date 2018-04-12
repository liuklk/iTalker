package com.klk.italker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.igexin.sdk.PushConsts;
import com.klk.factory.Factory;
import com.klk.factory.data.helper.AccountHelper;
import com.klk.factory.persistence.Account;

/**
 * @Des
 * @Auther Administrator
 * @date 2018/4/10  11:33
 */

public class MessageReceiver extends BroadcastReceiver{
    private static final String TAG = "MessageReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent==null){
            return;
        }
        Bundle bundle = intent.getExtras();
        switch (bundle.getInt(PushConsts.CMD_ACTION)){
            case PushConsts.GET_CLIENTID:
                onClientInit(bundle.getString("clientid"));
                Log.i(TAG, "GET_CLIENTID: "+bundle.toString());
                break;

            case PushConsts.GET_MSG_DATA:
                //常规消息送达
                byte[] bytes = bundle.getByteArray("payload");
                if(bytes!=null){
                    String massage = new String(bytes);
                    Log.i(TAG, "GET_MSG_DATA: "+massage);
                    onMassageReceived(massage);
                }
                break;
            default:
                Log.i(TAG, "OTHER "+bundle.toString());
                break;

        }
    }

    /**
     *接收器初始化
     */
    private void onClientInit(String cid){
        Account.setPushId(cid);
        if(!Account.isLogin()){
            //账户登录状态进行一次pushId的绑定
            //没有登录是不能绑定pushId的
            AccountHelper.bindPushId(null);
        }
    }

    /**
     * 当收到消息时
     * @param massage
     */
    private void onMassageReceived(String massage){
        //分发推送的消息
        Factory.dispatchPushMassage(massage);
    }
}
