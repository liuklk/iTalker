package com.klk.factory.data.helper;

import com.klk.common.factory.data.DataSource;
import com.klk.factory.Factory;
import com.klk.factory.model.RspModel;
import com.klk.factory.model.api.AccountRspModel;
import com.klk.factory.model.api.RegisterModel;
import com.klk.factory.model.db.User;
import com.klk.factory.net.Network;
import com.klk.factory.net.RemoteService;
import com.klk.factory.persistence.Account;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        RemoteService service = Network.getRetrofit().create(RemoteService.class);

        Call<RspModel<AccountRspModel>> call = service.accountRegister(model);

        call.enqueue(new Callback<RspModel<AccountRspModel>>() {
            @Override
            public void onResponse(Call<RspModel<AccountRspModel>> call, Response<RspModel<AccountRspModel>> response) {
                //请求成功返回
                RspModel<AccountRspModel> rspModel = response.body();
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

                        callback.onDataLoaded(user);
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
                //网络请求失败

                callback.onDataLoadFailed(com.klk.lang.R.string.data_network_error);
            }
        });


    }

    /**
     * 对设备id进行绑定操作
     * @param callback
     */
    public static void bindPushId(final DataSource.Callback<User> callback){
        Account.setIsBind(true);
    }
}
