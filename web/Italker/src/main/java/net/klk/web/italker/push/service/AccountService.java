package net.klk.web.italker.push.service;


import com.google.common.base.Strings;
import net.klk.web.italker.push.bean.api.account.AccountRespModel;
import net.klk.web.italker.push.bean.api.account.LoginModel;
import net.klk.web.italker.push.bean.api.account.RegisterModel;
import net.klk.web.italker.push.bean.base.ResponseModel;
import net.klk.web.italker.push.bean.db.User;
import net.klk.web.italker.push.factory.UserFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * 账户接口
 */
//相当于：http://localhost:8080/api/account
@Path("/account")
public class AccountService extends BaseService {

    //登录的方法
    @POST
    @Path("/login")
    //指定请求和返回的格式为json
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRespModel> login(LoginModel model) {

        if (!LoginModel.chick(model)) {
            return ResponseModel.buildParameterError();
        }
        User user = UserFactory.login(model.getAccount(), model.getPassword());
        if (user != null) {
            //绑定
            if (!Strings.isNullOrEmpty(model.getPushId())) {
                return bind(user, model.getPushId());
            }
            AccountRespModel respModel = new AccountRespModel(user);
            return ResponseModel.buildOk(respModel);
        }

        return ResponseModel.buildLoginError();

    }

    //注册的方法
    @POST
    @Path("/register")
    //指定请求和返回的格式为json
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRespModel> register(RegisterModel model) {

        if (!RegisterModel.chick(model)) {
            return ResponseModel.buildParameterError();
        }

        User user = UserFactory.queryByName(model.getName());
        if (user != null) {
            return ResponseModel.buildHaveNameError();
        }

        user = UserFactory.queryByPhone(model.getAccount());

        if (user != null) {
            return ResponseModel.buildHaveAccountError();
        }

        user = UserFactory.register(model.getName(), model.getPassword(), model.getAccount());

        if (user != null) {
            //绑定
            if (!Strings.isNullOrEmpty(model.getPushId())) {
                return bind(user, model.getPushId());
            }
            AccountRespModel respModel = new AccountRespModel(user);
            return ResponseModel.buildOk(respModel);
        } else {
            return ResponseModel.buildRegisterError();
        }

    }

    //绑定的方法
    @POST
    @Path("/bind/{pushId}")
    //指定请求和返回的格式为json
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRespModel> bind(@PathParam("pushId") String pushId) {

        //1.校验
        if (Strings.isNullOrEmpty(pushId)) {
            return ResponseModel.buildParameterError();
        }

        User user = getSelf();

        //绑定id的操作
        return bind(user, pushId);


    }

    /**
     * 绑定的操作
     *
     * @param self   要绑定的账户
     * @param pushId 设备id
     * @return  ResponseModel
     */
    private ResponseModel<AccountRespModel> bind(User self, String pushId) {
        User user = UserFactory.bindUser(self, pushId);

        if (user != null) {
            AccountRespModel respModel = new AccountRespModel(user, true);
            return ResponseModel.buildOk(respModel);
        } else {
            //返回服务器异常
            return ResponseModel.buildServiceError();
        }
    }
}
