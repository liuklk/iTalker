package com.klk.factory.data.helper;

import android.text.TextUtils;
import android.util.Log;

import com.klk.common.factory.data.DataSource;
import com.klk.factory.Factory;
import com.klk.factory.model.RspModel;
import com.klk.factory.model.api.account.AccountRspModel;
import com.klk.factory.model.api.account.LoginModel;
import com.klk.factory.model.api.account.RegisterModel;
import com.klk.factory.model.db.User;
import com.klk.factory.net.Network;
import com.klk.factory.net.RemoteService;
import com.klk.factory.persistence.Account;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * @Des  账户数据处理类
 * @Auther Administrator
 * @date 2018/4/3  9:43
 */

public class AccountHelper {
    /**
     * 注册的接口，异步调用
     * @param model  注册的实体
     * @param callback  成功与失败的回调
     */
    public static void register(RegisterModel model, final DataSource.Callback<User> callback){
        //调用Retrofit对我们的网络请求做代理
        RemoteService service = Network.remote();

        Call<RspModel<AccountRspModel>> call =service.accountRegister(model);

        call.enqueue(new AccountCallback(callback));

    }

    /**
     * 对设备id进行绑定操作
     */
    public static void bindPushId(final DataSource.Callback<User> callback){
        String pushId = Account.getPushId();
        if(TextUtils.isEmpty(pushId)){
            return;
        }
        //调用Retrofit对我们的网络请求做代理
        RemoteService service = Network.remote();

        Call<RspModel<AccountRspModel>> call = service.accountBind(pushId);

        call.enqueue(new AccountCallback(callback));
    }

    /**
     * 登录的入口
     */
    public static void login(LoginModel model, final DataSource.Callback<User> callback){

        //调用Retrofit对我们的网络请求做代理
        RemoteService service = Network.remote();

        Call<RspModel<AccountRspModel>> call = service.accountLogin(model);
        //异步请求
        call.enqueue(new AccountCallback(callback));
    }

    private static final String TAG = "AccountHelper";
    /**
     * 请求回调的封装
     */
    private static class AccountCallback implements Callback<RspModel<AccountRspModel>>{
        private DataSource.Callback<User> callback ;
        public AccountCallback(DataSource.Callback<User> callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<RspModel<AccountRspModel>> call, Response<RspModel<AccountRspModel>> response) {
            //请求成功返回
            RspModel<AccountRspModel> rspModel = response.body();
            Log.i(TAG, "onResponse: response        :" +response);
            Log.i(TAG, "onResponse: rspModel        :" +rspModel);
            if(rspModel.success()){
                //取出实体
                AccountRspModel accountModel = rspModel.getResult();

                //取出user
                User user = accountModel.getUser();
                //数据库存储
                //第一种存储方式
                user.save();
                       /*
                       //第二种存储方式可以存储多个user
                        FlowManager.getModelAdapter(User.class).save(user);
                        //第三种放在事务中进行存储
                        DatabaseDefinition definition = FlowManager.getDatabase(AppDatabase.class);
                        definition.beginTransactionAsync(new ITransaction() {
                            @Override
                            public void execute(DatabaseWrapper databaseWrapper) {
                                user.save();
                            }
                        }).execute();
                        */
                Account.saveAccountInfo(accountModel);
                if(accountModel.isBind()){
                    //设置绑定状态为true
                    Account.setIsBind(true);
                    if(callback!=null){
                        callback.onDataLoaded(user);
                    }

                }else {
                    //如果没有绑定，需要绑定
                    bindPushId(callback);
                }

            }else{
                //对返回的body的错误code进行解析，返回对应的错误
                Factory.deCodeRspModel(rspModel,callback);
            }
        }

        @Override
        public void onFailure(Call<RspModel<AccountRspModel>> call, Throwable t) {
            if(callback!=null){
                callback.onDataLoadFailed(com.klk.lang.R.string.data_network_error);
            }
        }
    }
}
