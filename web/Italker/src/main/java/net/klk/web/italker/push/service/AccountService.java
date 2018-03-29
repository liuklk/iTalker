package net.klk.web.italker.push.service;



import net.klk.web.italker.push.bean.api.account.RegisterModel;
import net.klk.web.italker.push.bean.db.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 *
 */
//相当于：http://localhost:8080/api/account
@Path("/account")
public class AccountService {


    @POST
    @Path("/login")
    //指定请求和返回的格式为json
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User post(){
        User user = new User();
        user.setName("hehe");
        user.setSex(2);

        return user;

    }

    @POST
    @Path("/register")
    //指定请求和返回的格式为json
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User register(RegisterModel model){
        User user = new User();
        user.setName(model.getName());
        user.setSex(2);

        return user;
    }
}
