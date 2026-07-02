package org.study.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.study.Service.HeadlineService;
import org.study.Service.NewsTypeService;
import org.study.entity.NewsType;
import org.study.entity.PortalVo;
import org.study.entity.result;

@RestController
@RequestMapping("/portal")
@CrossOrigin
public class PortalController {

    @Autowired
    private NewsTypeService newsTypeService;

    @Autowired
    private HeadlineService headlineService;

    @GetMapping("findAllTypes")
    public result findAllTypes() {
        List<NewsType> list = newsTypeService.findAllTypes();
        return result.ok(list);
    }

    @PostMapping("findNewsPage")
    public result findNewPage(@RequestBody PortalVo portalVo) {
        return headlineService.findNewPage(portalVo);
    }

    @PostMapping("showHeadlineDetail")
    public result showHeadlineDetail(@RequestParam("hid") Integer hid) {
        return headlineService.showHeadlineDetail(hid);
    }
}
