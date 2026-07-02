package org.study.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;

import lombok.Data;

@Data
@TableName("news_type")
public class NewsType {
    @TableId(type = IdType.AUTO)
    private Integer tid;
    private String tname;

    @Version
    private Integer version;

    @TableLogic
    private Integer isDeleted;
}
