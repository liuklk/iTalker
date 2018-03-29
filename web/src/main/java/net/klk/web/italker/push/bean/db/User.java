package net.klk.web.italker.push.bean.db;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户实体类
 */
@Entity
@Table(name="TB_USER")
public class User {

    @Id
    //这是一个主键
    @PrimaryKeyJoinColumn
    //主键生成存储的类型为UUID
    @GeneratedValue(generator = "uuid" )
    //把uuid生成器定义为uuid2,uuid2是常规UUID的toString
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    //不允许更改，不允许为null
    @Column(updatable = false ,nullable = false)
    private String id;

    @Column(nullable = false ,length = 128 ,unique = true)
    private String name;

    @Column(nullable = false ,length = 128 ,unique = true)
    private String phone;

    @Column(nullable = false)
    private String password;
    //头像允许为null
    @Column
    private String portrait;

    @Column
    private String decription;

    //性别有初始值，不为空
    @Column(nullable = false)
    private int sex = 0;

    @Column
    private String description;

    @Column(unique = true)
    private String token;

    //用于推送的设备id
    @Column
    private String pushId;


    //创建的时间戳，在创建时已经写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    //创建的时间戳，在创建时已经写入
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

    //最后一次收到消息的时间
    @Column
    private LocalDateTime LastReceiveAt = LocalDateTime.now();

    //我关注人的id的集合
    //对应数据库TB_USER_Follow.originId 字段
    @JoinColumn(name = "originId")
    //定义为懒加载，默认加载user信息的时候，不查询这个集合
    @LazyCollection(LazyCollectionOption.EXTRA)
    //一对多，
    @OneToMany(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    private Set<UserFollow> followings = new HashSet<>();


    //关注我的人的id的集合
    //对应数据库TB_USER_Follow.targetId 字段
    @JoinColumn(name = "targetId")
    //定义为懒加载，默认加载user信息的时候，不查询这个集合
    @LazyCollection(LazyCollectionOption.EXTRA)
    //一对多，
    @OneToMany(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    private Set<UserFollow> followers = new HashSet<>();

    //关注我的人的id的集合
    //对应数据库TB_USER_Follow.targetId 字段
    @JoinColumn(name = "ownerId")
    //定义为懒加载，默认加载user信息的时候，不查询这个集合
    @LazyCollection(LazyCollectionOption.EXTRA)
    //一对多，加载用户信息时不加载群的信息
    @OneToMany(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    private Set<Group> groups = new HashSet<>() ;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public LocalDateTime getLastReceiveAt() {
        return LastReceiveAt;
    }

    public void setLastReceiveAt(LocalDateTime lastReceiveAt) {
        LastReceiveAt = lastReceiveAt;
    }

    public Set<UserFollow> getFollowings() {
        return followings;
    }

    public void setFollowings(Set<UserFollow> followings) {
        this.followings = followings;
    }

    public Set<UserFollow> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<UserFollow> followers) {
        this.followers = followers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }
}
