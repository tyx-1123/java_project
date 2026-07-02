package org.study.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;

import lombok.Data;

@Data
@TableName("news_user")
public class NewsUser {
    @TableId(type = IdType.AUTO)
    private Integer uid;
    private String username;
    private String userPwd;
    private String nickName;

    @Version
    private Integer version;

    @TableLogic
    private Integer isDeleted;

}
