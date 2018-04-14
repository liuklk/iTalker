package net.klk.web.italker.push.bean.card;

import com.google.gson.annotations.Expose;
import net.klk.web.italker.push.bean.db.User;

import java.time.LocalDateTime;

/**
 * 给客户端提供的user信息
 */
public class UserCard {
    @Expose
    private String id;
    @Expose
    private String name;

    private String phone;
    @Expose
    private String portrait;
    @Expose
    private String decription;
    @Expose
    private int sex =0 ;


    //用户关注人的数量
    @Expose
    private int follows;

    //用户的粉丝数量
    @Expose
    private int followings;

    //我是否关注过此人
    @Expose
    private boolean isFollow;

    //用户最后的更新时间
    @Expose
    private LocalDateTime updateAt ;

    public UserCard(User user) {
        this(user,false);
    }

    public UserCard(User user ,boolean isFollow) {
        this.id = user.getId();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.portrait = user.getPortrait();
        this.decription = user.getDescription();
        this.sex = user.getSex();
        this.updateAt = user.getUpdateAt();

        this.isFollow = isFollow;
        //Todo 粉丝数量 都是懒加载

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getFollows() {
        return follows;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    public int getFollowings() {
        return followings;
    }

    public void setFollowings(int followings) {
        this.followings = followings;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }
}
