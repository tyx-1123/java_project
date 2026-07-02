package org.study.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.study.Service.HeadlineService;
import org.study.entity.NewsHeadline;
import org.study.entity.result;
import org.study.utils.JwtHelper;

@RestController
@RequestMapping("/headline")
@CrossOrigin
public class HeadlineController {

    @Autowired
    private HeadlineService headlineService;

    @Autowired
    private JwtHelper jwtHelper;

    @PostMapping("/publish")
    public result publish(@RequestBody NewsHeadline headline, @RequestHeader("token") String token) {
        Integer userId = jwtHelper.getUserId(token).intValue();
        return headlineService.publish(headline, userId);
    }

    @PostMapping("/findHeadlineByHid")
    public result findHeadlineByHid(@RequestParam("hid") Integer hid) {
        return headlineService.findHeadlineByHid(hid);
    }

    @PostMapping("/update")
    public result update(@RequestBody NewsHeadline headline, @RequestHeader("token") String token) {
        Integer userId = jwtHelper.getUserId(token).intValue();
        return headlineService.update(headline, userId);
    }

    @PostMapping("/removeByHid")
    public result removeByHid(@RequestParam("hid") Integer hid) {
        return headlineService.removeByHid(hid);
    }
}
