package org.study.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.study.entity.NewsHeadline;
import org.study.entity.PortalVo;

@Mapper
public interface HeadlineMapper extends BaseMapper<NewsHeadline> {
    IPage<Map<String, Object>> selectPageMap(IPage<NewsHeadline> page,
        @Param("portalVo") PortalVo portalVo);

    Map<String, Object> selectDetailMap(@Param("hid") Integer hid);

    int updatePageViews(@Param("hid") Integer hid);

}

