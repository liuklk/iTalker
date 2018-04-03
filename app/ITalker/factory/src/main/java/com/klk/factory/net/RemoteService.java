package com.klk.factory.net;

import com.klk.factory.model.RspModel;
import com.klk.factory.model.api.AccountRspModel;
import com.klk.factory.model.api.RegisterModel;

import retrofit2.Call;
import retrofit2.http.POST;

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
    Call<RspModel<AccountRspModel>>  accountRegister(RegisterModel model);
}
