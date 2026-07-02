package org.study.Service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.Mapper.NewsUserMapper;
import org.study.Service.NewsUserService;
import org.study.entity.result;
import org.study.utils.JwtHelper;
import org.study.entity.NewsUser;
import java.util.HashMap;
import java.util.Map;

@Service
public class NewsUserImp implements NewsUserService {

    @Autowired
    private NewsUserMapper newsUserMapper;

    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public result login(NewsUser User) {
        LambdaQueryWrapper<NewsUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(NewsUser::getUsername, User.getUsername());
        NewsUser user = newsUserMapper.selectOne(wrapper);

        if (user == null) {
            return result.fail(501, "用户名有误");
        }
        if (!user.getUserPwd().equals(User.getUserPwd())) {
            return result.fail(503, "密码有误");
        }

        String token = jwtHelper.createToken(user.getUid().longValue());
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        return result.ok(data);
    }

    @Override
    public result getUser(String token) {

        // 1.判定是否有效期
        if (jwtHelper.isExpiration(token)) {
            // true过期,直接返回未登录
            return result.fail(504, "token已过期");
        }

        // 2.获取token对应的用户
        int userId = jwtHelper.getUserId(token).intValue();

        // 3.查询数据
        NewsUser user = newsUserMapper.selectById(userId);

        if (user != null) {
            user.setUserPwd(null);
            Map data = new HashMap();
            data.put("loginUser", user);
            return result.ok(data);
        }

        return result.fail(504, "token已过期");
    }
    @Override
    public result register(NewsUser newUser) {
        LambdaQueryWrapper<NewsUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(NewsUser::getUsername, newUser.getUsername());
        NewsUser existingUser = newsUserMapper.selectOne(wrapper);

        if (existingUser != null) {
            return result.fail(505, "用户名已存在");
        }

        
        int rowsAffected = newsUserMapper.insert(newUser);
        if (rowsAffected > 0) {
            return result.ok("注册成功");
        } else {
            return result.fail(506, "注册失败");
        }
    }
}
