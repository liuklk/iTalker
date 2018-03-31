package net.klk.web.italker.push.service;

import net.klk.web.italker.push.bean.db.User;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 * Service的基础类
 */
public class BaseService {
    @Context
    protected SecurityContext securityContext;

    protected User getSelf(){
        return (User) securityContext.getUserPrincipal();
    }
}
