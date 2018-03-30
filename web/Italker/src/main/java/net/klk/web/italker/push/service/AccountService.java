package net.klk.web.italker.push.service;



import net.klk.web.italker.push.bean.api.account.AccountRespModel;
import net.klk.web.italker.push.bean.api.account.LoginModel;
import net.klk.web.italker.push.bean.api.account.RegisterModel;
import net.klk.web.italker.push.bean.base.ResponseModel;
import net.klk.web.italker.push.bean.card.UserCard;
import net.klk.web.italker.push.bean.db.User;
import net.klk.web.italker.push.factory.UserFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 *
 */
//相当于：http://localhost:8080/api/account
@Path("/account")
public class AccountService {

    //登录的方法
    @POST
    @Path("/login")
    //指定请求和返回的格式为json
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRespModel> login(LoginModel model){

        if(!LoginModel.chick(model)){
            return ResponseModel.buildParameterError();
        }
       User user = UserFactory.login(model.getAccount(), model.getPassword());
        if(user!=null){
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
    public ResponseModel<AccountRespModel> register(RegisterModel model){

        if(!RegisterModel.chick(model)){
            return ResponseModel.buildParameterError();
        }

       User user = UserFactory.queryByName(model.getName());
        if(user!=null){
            return ResponseModel.buildHaveNameError();
        }

        user = UserFactory.queryByPhone(model.getAccount());

        if(user!=null){
            return ResponseModel.buildHaveAccountError();
        }

        user = UserFactory.register(model.getName(), model.getPassword(), model.getAccount());

        if(user!=null){
            AccountRespModel respModel = new AccountRespModel(user);
            return ResponseModel.buildOk(respModel);
        }else{
            return ResponseModel.buildRegisterError() ;
        }



    }
}
