package net.klk.web.italker.push.provider;

import com.google.common.base.Strings;
import net.klk.web.italker.push.bean.base.ResponseModel;
import net.klk.web.italker.push.bean.db.User;
import net.klk.web.italker.push.factory.UserFactory;
import org.glassfish.jersey.server.ContainerRequest;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;

/**
 * 所有请求的过滤器
 */
@Provider
public class AuthRequestFilter implements ContainerRequestFilter{

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {


        String path = ((ContainerRequest) requestContext).getPath(false);

        if(path.startsWith("/account/login")||path.startsWith("/account/register")){
            //如果是登录注册，不需要拦截
            return;
        }

        String token = requestContext.getHeaders().getFirst("token");

        if(!Strings.isNullOrEmpty(token)){

            User self = UserFactory.queryByToken(token);
            if(self!=null){
                requestContext.setSecurityContext(new SecurityContext() {
                    @Override
                    public Principal getUserPrincipal() {
                        return self;
                    }

                    @Override
                    public boolean isUserInRole(String role) {
                        //可以进行管理员权限设置
                        return true;
                    }

                    @Override
                    public boolean isSecure() {
                        return false;
                    }

                    @Override
                    public String getAuthenticationScheme() {
                        return null;
                    }
                });

                return;
            }

        }

        ResponseModel model = ResponseModel.buildAccountError();

        Response response = Response.status(Response.Status.OK).entity(model).build();

        //中断请求，不会走到service
        requestContext.abortWith(response);
    }
}
