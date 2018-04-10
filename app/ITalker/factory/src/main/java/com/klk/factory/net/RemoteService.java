package com.klk.factory.net;

import com.klk.factory.model.RspModel;
import com.klk.factory.model.api.AccountRspModel;
import com.klk.factory.model.api.LoginModel;
import com.klk.factory.model.api.RegisterModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @Des
 * @Auther Administrator
 * @date 2018/4/3  14:52
 */

public interface RemoteService {
    /**
     * 注册的接口
     * @param model   RegisterModel
     * @return  RspModel<AccountRspModel>
     */
    @POST("account/register")
    Call<RspModel<AccountRspModel>>  accountRegister(@Body RegisterModel model);

    /**
     * 登录的接口
     * @param model   LoginModel
     * @return  RspModel<AccountRspModel>
     */
    @POST("account/login")
    Call<RspModel<AccountRspModel>>  accountLogin(@Body LoginModel model);

    /**
     * 绑定的接口
     * @param pushId   pushId
     * @return  RspModel<AccountRspModel>
     */
    @POST("account/bind/{pushId}")
    Call<RspModel<AccountRspModel>>  accountBind(@Path(encoded = true, value = "pushId")String pushId);
}
