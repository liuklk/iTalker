package com.klk.factory.net;

import com.klk.factory.model.RspModel;
import com.klk.factory.model.api.account.AccountRspModel;
import com.klk.factory.model.api.account.LoginModel;
import com.klk.factory.model.api.account.RegisterModel;
import com.klk.factory.model.api.user.UserUpdateModel;
import com.klk.factory.model.card.UserCard;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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


    /**
     * 更改用户信息
     * @param model   UserUpdateModel
     * @return  RspModel<UserCard>
     */
    @PUT("user")
    Call<RspModel<UserCard>>  updateUserInfo(@Body UserUpdateModel model);

    /**
     * 用name进行模糊查询用户
     * @param name name
     * @return Call<RspModel<List<UserCard>>>
     */
    @GET("user/search/{name}")
    Call<RspModel<List<UserCard>>> searchContact(@Path(value = "name", encoded = true) String name);

    /**
     * 根据id关注某人的接口
     * @param followId 被关注人的id
     * @return Call<RspModel<UserCard>>
     */
    @PUT("user/following/{followId}")
    Call<RspModel<UserCard>> userFollow(@Path(value = "followId", encoded = true) String followId);

}
