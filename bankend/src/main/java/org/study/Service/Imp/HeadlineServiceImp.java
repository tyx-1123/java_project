package org.study.Service.Imp;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.Mapper.HeadlineMapper;
import org.study.Service.HeadlineService;
import org.study.entity.NewsHeadline;
import org.study.entity.PortalVo;
import org.study.entity.result;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Service
public class HeadlineServiceImp implements HeadlineService {
    @Autowired
    private HeadlineMapper headlineMapper;

    @Override
    public result findNewPage(PortalVo portalVo) {
        IPage<NewsHeadline> page = new Page<>(portalVo.getPageNum(), portalVo.getPageSize());
        IPage<Map<String, Object>> pageMap = headlineMapper.selectPageMap(page, portalVo);

        Map<String, Object> pageInfo = new HashMap<>();
        pageInfo.put("pageData", pageMap.getRecords());
        pageInfo.put("pageNum", pageMap.getCurrent());
        pageInfo.put("pageSize", pageMap.getSize());
        pageInfo.put("totalPage", pageMap.getPages());
        pageInfo.put("totalSize", pageMap.getTotal());

        Map<String, Object> pageInfoMap = new HashMap<>();
        pageInfoMap.put("pageInfo", pageInfo);
        return result.ok(pageInfoMap);
    }

    @Override
    public result showHeadlineDetail(Integer hid) {
        if (hid == null) {
            return result.fail(501, "头条不存在");
        }

        headlineMapper.updatePageViews(hid);
        Map<String, Object> headLineDetail = headlineMapper.selectDetailMap(hid);
        if (headLineDetail == null) {
            return result.fail(501, "头条不存在");
        }

        Map<String, Object> pageInfoMap = new HashMap<>();
        pageInfoMap.put("headline", headLineDetail);
        return result.ok(pageInfoMap);
    }

    @Override
    public result publish(NewsHeadline headline, Integer userId) {
        headline.setPublisher(userId);
        headline.setPageViews(0);
        headline.setCreateTime(LocalDateTime.now());
        headline.setUpdateTime(LocalDateTime.now());
        int rows = headlineMapper.insert(headline);
        if (rows > 0) {
            return result.ok(null);
        }
        return result.fail(506, "发布失败");
    }

    @Override
    public result findHeadlineByHid(Integer hid) {
        if (hid == null) {
            return result.fail(501, "头条不存在");
        }
        NewsHeadline headline = headlineMapper.selectById(hid);
        if (headline == null) {
            return result.fail(501, "头条不存在");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("headline", headline);
        return result.ok(data);
    }

    @Override
    public result update(NewsHeadline headline, Integer userId) {
        if (headline.getHid() == null) {
            return result.fail(501, "头条不存在");
        }
        NewsHeadline dbHeadline = headlineMapper.selectById(headline.getHid());
        if (dbHeadline == null) {
            return result.fail(501, "头条不存在");
        }
        headline.setPublisher(userId);
        headline.setCreateTime(dbHeadline.getCreateTime());
        headline.setPageViews(dbHeadline.getPageViews());
        headline.setVersion(dbHeadline.getVersion());
        headline.setIsDeleted(dbHeadline.getIsDeleted());
        headline.setUpdateTime(LocalDateTime.now());
        int rows = headlineMapper.updateById(headline);
        if (rows > 0) {
            return result.ok(null);
        }
        return result.fail(506, "修改失败");
    }

    @Override
    public result removeByHid(Integer hid) {
        if (hid == null) {
            return result.fail(501, "头条不存在");
        }
        int rows = headlineMapper.deleteById(hid);
        if (rows > 0) {
            return result.ok(null);
        }
        return result.fail(501, "头条不存在");
    }
}
