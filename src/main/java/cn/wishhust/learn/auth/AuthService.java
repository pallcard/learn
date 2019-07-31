package cn.wishhust.learn.auth;

import cn.wishhust.learn.auth.pojo.LoginRepeat;
import cn.wishhust.learn.auth.pojo.LoginRequest;
import cn.wishhust.learn.config.CustomCache;
import cn.wishhust.learn.utils.IJSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Service
public class AuthService {

    @Autowired
    private CustomCache<LoginRepeat> loginRepeatCache;


    public IJSONResult login(LoginRequest request) {
        if (StringUtils.isEmpty(request.getUsername()) || StringUtils.isEmpty(request.getPassword())) {
            return IJSONResult.errorMsg("用户名和密码不能为空");
        }

        LoginRepeat preLoginRepeat = loginRepeatCache.getValue(request.getUsername());
        if (preLoginRepeat.checkExpired()) {
            return IJSONResult.errorMsg("登陆太频繁了，请稍后再试");
        }
        LoginRepeat loginRepeat = preLoginRepeat.increase();
        loginRepeatCache.setValue(request.getUsername(), loginRepeat, TimeUnit.DAYS.toMillis(1));

        return IJSONResult.ok();
    }
}
