package net.klk.web.italker.push.factory;

import com.google.common.base.Strings;
import net.klk.web.italker.push.bean.db.User;
import net.klk.web.italker.push.bean.db.UserFollow;
import net.klk.web.italker.push.utils.Hib;
import net.klk.web.italker.push.utils.TextUtil;
import org.hibernate.Session;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 用户数据处理类
 */
public class UserFactory {


    /**
     * 通过电话号码查询
     *
     * @return 查询得到的用户信息
     */
    public static User queryByPhone(String phone) {
        return Hib.query(session -> (User) session.createQuery("from User where phone=:inPhone")
                .setParameter("inPhone", phone)
                .uniqueResult());
    }

    /**
     * 通过用户名查询
     *
     * @return 查询得到的用户信息
     */
    public static User queryByName(String name) {
        return Hib.query(session -> (User) session.createQuery("from User where name=:inName")
                .setParameter("inName", name)
                .uniqueResult());
    }


    /**
     * 通过token查询
     *
     * @return 查询得到的用户信息
     */
    public static User queryByToken(String token) {
        return Hib.query(session -> (User) session.createQuery("from User where token=:token")
                .setParameter("token", token)
                .uniqueResult());
    }
    /**
     * 通过id查询
     *
     * @param id id
     * @return 查询得到的用户信息
     */
    public static User queryById(String id) {
        //直接通过id查询速度比较快
        return Hib.query((Session session) -> session.get(User.class ,id));
    }

    /**
     * 更新信息到数据库
     * @return 此用户的信息
     */
    public static User update(User user) {

        return Hib.query(session -> {
            session.saveOrUpdate(user);
            return user;
        });
    }

    /**
     * 用户注册的方法
     * @param account  电话号码就是用户的账户
     * @return 此用户的信息
     */
    public static User register(String name, String password, String account) {
        User user = createUser(name, password.trim(), account.trim());

        if (user != null) {
            login(user);
        }
        return user;
    }

    /**
     * 使用账号和密码登录
     *
     * @return 此用户的信息
     */
    public static User login(String account, String password) {

        String mAccount = account.trim();
        String ecodenPassword = ecodenStr(password);

        User user = Hib.query(session -> (User) session.createQuery("from User where phone=:phone and password=:password")
                .setParameter("phone", mAccount)
                .setParameter("password", ecodenPassword)
                .uniqueResult());
        if (user != null) {
            user = login(user);
        }

        return user;
    }

    /**
     * 使用user进行登录
     *
     */
    private static User login(User user) {

        String token = UUID.randomUUID().toString();

        String newToken = TextUtil.encodeBase64(token);

        user.setToken(newToken);

        return update(user);


    }

    /**
     * 创建一个新的用户
     *
     * @return 此用户的信息
     */
    private static User createUser(String name, String password, String account) {
        User user = new User();


        user.setName(name);
        user.setPassword(ecodenStr(password));
        user.setPhone(account.trim());
        return update(user);
    }

    /**
     * 绑定  pushId
     * @return 此用户的信息
     */
    public static User bindUser(User user, String pushId) {

        //查询其他账户是否绑定这个pushId ,并且将其解绑
        Hib.queryOnly((Session session) -> {
            @SuppressWarnings("unchecked")
            List<User> userList = (List<User>) session
                    .createQuery("from User Where lower(pushId)=:pushId and id!=:userId")
                    .setParameter("pushId", pushId.toLowerCase())
                    .setParameter("userId", user.getId())
                    .list();
            for (User u : userList) {
                //解绑
                u.setPushId(null);
                session.saveOrUpdate(u);
            }
        });
        if (pushId.equalsIgnoreCase(user.getPushId())) {
            //如果此pushId已经绑定，不需要再次绑定，直接返回
            return user;
        }
        //
        if (Strings.isNullOrEmpty(user.getPushId())) {
            //TODO  退出之前的账户，并给推送一条消息
        }
        //如果之前没有绑定，更新设备Id
        user.setPushId(pushId);
        return update(user);


    }

    /**
     * 对字符串进行加密
     */
    private static String ecodenStr(String str) {
        //去空格
        String trim = str.trim();
        //进行MD5加密
        String md5 = TextUtil.getMD5(trim);
        //进行base64加密
        String base64 = TextUtil.encodeBase64(md5);

        return base64;
    }

    /**
     * 查询联系人列表
     * @param self  自己
     * @return  List<User>
     */
    public static List<User> getContacts(User self){
        return Hib.query(session -> {
            //由于followings为懒加载，需要查询之前进行一次加载
            session.load(self, self.getId());
            //获取我关注人的关系集合
            Set<UserFollow> followings = self.getFollowings();


            return followings.stream()
                    .map(UserFollow::getTarget)
                    .collect(Collectors.toList());
        });
    }

    /**
     * 关注某个人
     * @param originUser  发起者
     * @param targetUser  被关注者
     * @param alis  备注名
     * @return 被关注人的信息
     */
    public static User follow(User originUser ,User targetUser ,String alis){

        UserFollow userfollow = getUserFollow(originUser, targetUser);

        if(userfollow!=null){
            return userfollow.getTarget() ;
        }else{
           return  Hib.query((Hib.Query<User>) session -> {
                 session.load(originUser ,originUser.getId());
                 session.load(targetUser ,targetUser.getId());

                 //添加两条关系信息
                 UserFollow originFollow = new UserFollow();
                 originFollow.setOrigin(originUser);
                 originFollow.setTarget(targetUser);
                 originFollow.setAlias(alis);


                 UserFollow targetFollow = new UserFollow();
                 targetFollow.setOrigin(targetUser);
                 targetFollow.setTarget(originUser);

                 session.save(originFollow);
                 session.save(targetFollow);


                 return targetUser;
             });
        }


    }


    /**
     * 查看是否关注了某个人
     * @param originUser  发起者
     * @param targetUser  被关注者
     * @return 中间类的信息
     */
    public static UserFollow getUserFollow(User originUser ,User targetUser ){
        return Hib.query(session -> (UserFollow) session.
                createQuery("from UserFollow where originId =: originId and targetId =: targetId")
                .setParameter("originId",originUser.getId())
                .setParameter("targetId",targetUser.getId())
                .uniqueResult());
    }

    /**
     * 通过name查询一个人，
     * @param name 可为空，
     * @return  如果name为空则返回最近的20条数据 ,最多返回20条数据
     */
    public static List<User> search(String name) {
        return Hib.query(new Hib.Query<List<User>>() {
            @Override
            public List<User> query(Session session) {

                session.createQuery("from User where name = : name and portrait is not null and decription is not null")
                        .setParameter("name","")
                        .list();
                return null;
            }
        });
    }
}
