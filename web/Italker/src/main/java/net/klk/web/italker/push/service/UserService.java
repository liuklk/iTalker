package net.klk.web.italker.push.service;


import net.klk.web.italker.push.bean.api.user.UpdateInfoModel;
import net.klk.web.italker.push.bean.base.ResponseModel;
import net.klk.web.italker.push.bean.card.UserCard;
import net.klk.web.italker.push.bean.db.User;
import net.klk.web.italker.push.factory.UserFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * User接口
 */
@Path("/user")
public class UserService extends BaseService {


    //用户信息修改
    @PUT //不需要路径
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<UserCard> update(UpdateInfoModel model) {
        if (!UpdateInfoModel.check(model)) {
            return ResponseModel.buildAccountError();
        }


        User self = getSelf();

        self = model.updateToUser(self);
        UserFactory.update(self);

        UserCard userCard = new UserCard(self, true);
        return ResponseModel.buildOk(userCard);

    }
}
