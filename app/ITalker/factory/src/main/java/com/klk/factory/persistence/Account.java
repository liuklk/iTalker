package com.klk.factory.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.klk.factory.Factory;
import com.klk.factory.model.api.AccountRspModel;
import com.klk.factory.model.db.User;
import com.klk.factory.model.db.User_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

/**
 * @Des
 * @Auther Administrator
 * @date 2018/4/10  10:14
 */

public class Account {

    private static final String KEY_PUSH_ID = "KEY_PUSH_ID";
    private static final String KEY_IS_BIND = "KEY_IS_BIND";
    private static final String KEY_TOKEN = "KEY_TOKEN";
    private static final String KEY_ID = "KEY_ID";
    private static final String KEY_ACCOUNT = "KEY_ACCOUNT";


    private static String pushId = "test";

    private static boolean isBind = false;

    private static String token ;

    private static String id;

    private static String account;


    public static String getPushId(){
        return Account.pushId ;
    }

    /**
     * 设置并存储pushId
     */
    public static void setPushId(String pushId){
        Account.pushId = pushId;
        Account.saveToSp(Factory.getApplication());
    }

    /**
     * 将pushId序列化到xml文件
     */
    private static void saveToSp(Context context){
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(),
                Context.MODE_PRIVATE);
        sp.edit().putString(KEY_PUSH_ID,pushId).
                putBoolean(KEY_IS_BIND,isBind).
                putString(KEY_TOKEN,token).
                putString(KEY_ACCOUNT,account).
                putString(KEY_ID,id).
                apply();
    }

    /**
     * 从sp中加载pushId
     */
    public static void loadFromSp(Context context){
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(),
                Context.MODE_PRIVATE);
        pushId = sp.getString(KEY_PUSH_ID,"");
        isBind = sp.getBoolean(KEY_IS_BIND,false);
        token = sp.getString(KEY_TOKEN,"");
        id = sp.getString(KEY_ID,"");
        account = sp.getString(KEY_ACCOUNT,"");
    }
    /**
     * 判断账户是否已经登录
     */
    public static boolean isLogin() {
        return !TextUtils.isEmpty(id)&&
                !TextUtils.isEmpty(token);
    }

    /**
     * 判断信息是否完善
     */
    public static boolean isComplete(){
        // TODO: 2018/4/10
        return isLogin();
    }

    /**
     * 是否已经绑定服务器
     */
    public static boolean isBind() {
        return Account.isBind ;
    }

    public static void setIsBind(boolean isBind){
        Account.isBind = isBind ;
        saveToSp(Factory.getApplication());
    }

    /**
     * 保存用户信息到sp
     * @param model AccountRspModel
     */
    public static void saveAccountInfo(AccountRspModel model){

        Account.token = model.getToken();
        Account.account = model.getAccount();
        Account.id =model.getUser().getId();

        saveToSp(Factory.getApplication());
    }

    /**
     * 快速获取account中user信息的方法
     * @return User
     */
    public static User getUser(){
        return TextUtils.isEmpty(id)?new User(): SQLite
                .select().from(User.class)
                .where(User_Table.id.eq(id))
                .querySingle();
    }
}
