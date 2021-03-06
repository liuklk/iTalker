package net.klk.web.italker.push.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 记录用户关系的Model
 * 用于用户直接进行好友关系
 */
@Entity
@Table(name = "TB_USER_Follow")
public class UserFollow {
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

    //发起人，你关注了某人，这里就是你
    //一个User对应多个UserFollow
    //optional 不可选，必须存储
    @ManyToOne(optional =false)
    //关联字段为originId，对应的是User.id
    @JoinColumn(name = "originId")
    private User origin;

    //把这个列提取到我们的Model 中，不允许为null，不允许更新
    @Column(nullable = false ,updatable = false ,insertable = false)
    private String originId;

    //发起人，你关注了某人，这里就是某人
    //一个User对应多个UserFollow
    //optional 不可选，必须存储
    @ManyToOne(optional =false)
    //关联字段为targetId，对应的是User.id
    @JoinColumn(name = "targetId")
    private User target;


    //把这个列提取到我们的Model 中，不允许为null，不允许更新
    @Column(nullable = false ,updatable = false ,insertable = false)
    private String targetId;

    @Column
    private String alias;

    //创建的时间戳，在创建时已经写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    //创建的时间戳，在创建时已经写入
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getOrigin() {
        return origin;
    }

    public void setOrigin(User origin) {
        this.origin = origin;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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
}
