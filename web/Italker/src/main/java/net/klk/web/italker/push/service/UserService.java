package net.klk.web.italker.push.service;


import com.google.common.base.Strings;
import net.klk.web.italker.push.bean.api.user.UpdateInfoModel;
import net.klk.web.italker.push.bean.base.ResponseModel;
import net.klk.web.italker.push.bean.card.UserCard;
import net.klk.web.italker.push.bean.db.User;
import net.klk.web.italker.push.factory.UserFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

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

    //获取联系人
    @GET
    @Path("/contact")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<UserCard>> getContact() {
        User self = getSelf();
        List<User> users = UserFactory.getContacts(self);
        //将user转成userCard
        List<UserCard> userCards = users.stream()
                .map(user ->
                        new UserCard(user, true))
                .collect(Collectors.toList());

        return ResponseModel.buildOk(userCards);
    }


    //关注某个人
    @PUT
    @Path("/following/{followId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<UserCard> following(@PathParam("followId") String followId) {

        User self = getSelf();
        //followId等于自己的id返回参数错误
        if (self.getId().equalsIgnoreCase(followId) || Strings.isNullOrEmpty(followId)) {
            return ResponseModel.buildParameterError();
        }


        User followUser = UserFactory.queryById(followId);
        if (followUser == null) {
            //如果没找到这个人
            return ResponseModel.buildNotFoundUserError(null);
        }

        //关注此人
        followUser = UserFactory.follow(self, followUser, null);

        if (followUser == null) {
            return ResponseModel.buildServiceError();
        }
        //TODO 通知此人我关注了他

        UserCard userCard = new UserCard(followUser, true);
        return ResponseModel.buildOk(userCard);
    }

    //根据id获取某人的信息
    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<UserCard> getUser(@PathParam("id") String id) {
        if (Strings.isNullOrEmpty(id)) {
            return ResponseModel.buildParameterError();
        }

        User self = getSelf();
        if (self.getId().equalsIgnoreCase(id)) {
            return ResponseModel.buildOk(new UserCard(self, true));
        }

        User user = UserFactory.queryById(id);
        if (user == null) {
                return ResponseModel.buildNotFoundUserError(null);
        }

        boolean isFollow =UserFactory.getUserFollow(self, user)==null;

        return ResponseModel.buildOk(new UserCard(user,isFollow));
    }


    @GET //通过name模糊查询user
    @Path("/search/{name:(.*)?}") //name为任意字符可以为空
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<UserCard>> searchUser(@DefaultValue("")@PathParam("name") String name) {
        User self = getSelf();

        List<User> userList = UserFactory.search(name);
    }


}
