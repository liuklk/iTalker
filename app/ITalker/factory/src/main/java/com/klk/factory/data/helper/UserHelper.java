package com.klk.factory.data.helper;

import com.klk.common.factory.data.DataSource;
import com.klk.factory.Factory;
import com.klk.factory.model.RspModel;
import com.klk.factory.model.api.user.UserUpdateModel;
import com.klk.factory.model.card.UserCard;
import com.klk.factory.model.db.User;
import com.klk.factory.net.Network;
import com.klk.factory.net.RemoteService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Des 用户数据处理类
 * @Auther Administrator
 * @date 2018/4/11  15:45
 */

public class UserHelper {
    /**
     * 更新用户信息
     *
     * @param model    UserUpdateModel
     * @param callback DataSource.Callback
     */
    public static void update(UserUpdateModel model, final DataSource.Callback<UserCard> callback) {
        //调用Retrofit对我们的网络请求做代理
        RemoteService service = Network.remote();

        Call<RspModel<UserCard>> call = service.updateUserInfo(model);

        call.enqueue(new Callback<RspModel<UserCard>>() {
            @Override
            public void onResponse(Call<RspModel<UserCard>> call, Response<RspModel<UserCard>> response) {
                //请求成功返回
                RspModel<UserCard> rspModel = response.body();

                if (rspModel.success()) {
                    UserCard userCard = rspModel.getResult();
                    //数据库的操作
                    User user = userCard.build();
                    user.save();
                    //数据加载成功
                    callback.onDataLoaded(userCard);


                } else {
                    //对返回的body的错误code进行解析，返回对应的错误
                    Factory.deCodeRspModel(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<UserCard>> call, Throwable t) {
                if (callback != null) {
                    callback.onDataLoadFailed(com.klk.lang.R.string.data_network_error);
                }
            }
        });

    }
}
