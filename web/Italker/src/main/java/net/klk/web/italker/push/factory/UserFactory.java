package net.klk.web.italker.push.factory;

import net.klk.web.italker.push.bean.db.User;
import net.klk.web.italker.push.utils.Hib;
import net.klk.web.italker.push.utils.TextUtil;

import java.util.UUID;

/**
 * 用户数据处理类
 */
public class UserFactory {


    /**
     * 通过电话号码查询
     * @param phone
     * @return
     */
    public static User queryByPhone(String phone){
        return Hib.query(session -> (User) session.createQuery("from User where phone=:inPhone")
                .setParameter("inPhone",phone)
                .uniqueResult());
    }
    /**
     * 通过用户名查询
     * @param name
     * @return
     */
    public static User queryByName(String name){
        return Hib.query(session -> (User) session.createQuery("from User where name=:inName")
                .setParameter("inName",name)
                .uniqueResult());
    }


    /**
     * 通过token查询
     * @param token
     * @return
     */
    public static User queryByToken(String token){
        return Hib.query(session -> (User) session.createQuery("from User where name=:token")
                .setParameter("token",token)
                .uniqueResult());
    }
    /**
     * 用户注册的方法
     * @param name
     * @param password
     * @param account  电话号码就是用户的账户
     * @return
     */
    public static User  register(String name ,String password , String account){
        User user = createUser(name, password.trim(), account.trim());

        if(user!=null){
            login(user);
        }
        return user ;
    }

    /**
     * 使用账号和密码登录
     * @param account
     * @param password
     * @return
     */
    public static User login(String account ,String password){
        
        String mAccount = account.trim();
        String ecodenPassword = ecodenStr(password);

      User user =  Hib.query(session -> (User) session.createQuery("from User where phone=:phone and password=:password")
                .setParameter("phone",mAccount)
                .setParameter("password",ecodenPassword)
                .uniqueResult());
        if(user!=null){
            user = login(user);
        }

        return user ;
    }
    /**
     * 使用user进行登录
     * @param user
     */
    private static User login(User user) {

        String token = UUID.randomUUID().toString();

        String newToken = TextUtil.encodeBase64(token);

        user.setToken(newToken);

        return Hib.query(session -> {
            session.saveOrUpdate(user);
            return user;
        });



    }

    /**
     * 创建一个新的用户
     * @param name
     * @param password
     * @param account
     * @return
     */
    public static User createUser(String name , String password ,String account){
        User user = new User();


        user.setName(name);
        user.setPassword(ecodenStr(password));
        user.setPhone(account.trim());
        return Hib.query(session -> {
            session.save(user);
            return user;
        });
    }

    public static String ecodenStr(String str){
        //去空格
        String trim = str.trim();
        //进行MD5加密
        String md5 = TextUtil.getMD5(trim);
        //进行base64加密
        String base64 = TextUtil.encodeBase64(md5);

        return base64 ;
    }
}
