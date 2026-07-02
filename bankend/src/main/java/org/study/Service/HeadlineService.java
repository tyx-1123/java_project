package org.study.Service;

import org.study.entity.NewsHeadline;
import org.study.entity.PortalVo;
import org.study.entity.result;

public interface HeadlineService {
    result findNewPage(PortalVo portalVo);

    result showHeadlineDetail(Integer hid);

    result publish(NewsHeadline headline, Integer userId);

    result findHeadlineByHid(Integer hid);

    result update(NewsHeadline headline, Integer userId);

    result removeByHid(Integer hid);
}
