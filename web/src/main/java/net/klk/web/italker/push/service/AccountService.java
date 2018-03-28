package net.klk.web.italker.push.service;



import net.klk.web.italker.push.db.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 *
 */
//相当于：http://localhost:8080/italker/api/account
@Path("/account")
public class AccountService {
    @GET
    @Path("/login")
    public String get(){
        return "You login success!";
    }

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
}
