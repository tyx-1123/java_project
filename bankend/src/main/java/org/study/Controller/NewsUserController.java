package org.study.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.study.Service.NewsUserService;
import org.study.entity.NewsUser;
import org.study.entity.result;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class NewsUserController {

    @Autowired
    private NewsUserService newsUserService;

    @PostMapping("/login")
    public result login(@RequestBody NewsUser loginUser) {
        return newsUserService.login(loginUser);
    }

    @GetMapping("/getUser")
    public result getUser(@RequestHeader("token") String token) {
        return newsUserService.getUser(token);
    }

    @PostMapping("/register")
    public result register(@RequestBody NewsUser registerUser) {
        return newsUserService.register(registerUser);
    }

    @GetMapping("/checkLogin")
    public result checkLogin() {
        return result.ok(null);
    }
}
