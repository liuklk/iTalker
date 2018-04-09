package com.klk.factory;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.klk.common.app.MyApplication;
import com.klk.common.factory.data.DataSource;
import com.klk.factory.model.RspModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Des
 * @Auther Administrator
 * @date 2018/3/27  15:23
 */

public class Factory {
    private static final Factory instance;
    private final ExecutorService executor;
    private  final Gson gson ;
    static {
        instance = new Factory();
    }

    /**
     * 构造方法
     */
    private Factory(){
        executor = Executors.newFixedThreadPool(4);//创建一个含有4个线程的线程池
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                //TODO
                //.setExclusionStrategies()
                .create();
    }

    /**
     * 获取全局Application的方法
     * @return
     */
    public static Application getApplication(){
        return MyApplication.getInstance();
    }

    /**
     * 异步执行的方法
     * @param runnable
     */
    public static void runOnAysnc(Runnable runnable){
        instance.executor.execute(runnable);
    }

    public static Gson getGson(){
        return instance.gson;
    }

    public static void deCodeRspModel(RspModel model , DataSource.FailCallback callback){
        if(model==null){
            return;
        }

        int code = model.getCode();
        switch (code){
            case RspModel.SUCCEED:
                return;
            case RspModel.ERROR_SERVICE:
                decodeRspCode(R.string.data_rsp_error_service, callback);
                break;
            case RspModel.ERROR_NOT_FOUND_USER:
                decodeRspCode(R.string.data_rsp_error_not_found_user, callback);
                break;
            case RspModel.ERROR_NOT_FOUND_GROUP:
                decodeRspCode(R.string.data_rsp_error_not_found_group, callback);
                break;
            case RspModel.ERROR_NOT_FOUND_GROUP_MEMBER:
                decodeRspCode(R.string.data_rsp_error_not_found_group_member, callback);
                break;
            case RspModel.ERROR_CREATE_USER:
                decodeRspCode(R.string.data_rsp_error_create_user, callback);
                break;
            case RspModel.ERROR_CREATE_GROUP:
                decodeRspCode(R.string.data_rsp_error_create_group, callback);
                break;
            case RspModel.ERROR_CREATE_MESSAGE:
                decodeRspCode(R.string.data_rsp_error_create_message, callback);
                break;
            case RspModel.ERROR_PARAMETERS:
                decodeRspCode(R.string.data_rsp_error_parameters, callback);
                break;
            case RspModel.ERROR_PARAMETERS_EXIST_ACCOUNT:
                decodeRspCode(R.string.data_rsp_error_parameters_exist_account, callback);
                break;
            case RspModel.ERROR_PARAMETERS_EXIST_NAME:
                decodeRspCode(R.string.data_rsp_error_parameters_exist_name, callback);
                break;
            case RspModel.ERROR_ACCOUNT_TOKEN:
                MyApplication.Toast(R.string.data_rsp_error_account_token);
                instance.logout();
                break;
            case RspModel.ERROR_ACCOUNT_LOGIN:
                decodeRspCode(R.string.data_rsp_error_account_login, callback);
                break;
            case RspModel.ERROR_ACCOUNT_REGISTER:
                decodeRspCode(R.string.data_rsp_error_account_register, callback);
                break;
            case RspModel.ERROR_ACCOUNT_NO_PERMISSION:
                decodeRspCode(R.string.data_rsp_error_account_no_permission, callback);
                break;
            case RspModel.ERROR_UNKNOWN:
            default:
                decodeRspCode(R.string.data_rsp_error_unknown, callback);
                break;
        }

    }

    public static void decodeRspCode(int resId ,DataSource.FailCallback callback){
        if(callback!=null){
            callback.onDataLoadFailed(resId);
        }
    }

    /**
     * 账户退出的方法
     */
    private static void logout(){

    }
}
