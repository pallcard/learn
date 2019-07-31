package cn.wishhust.learn.auth;

import cn.wishhust.learn.auth.pojo.UserToken;
import cn.wishhust.learn.config.CustomCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class TokenManager {

    private Logger logger = LoggerFactory.getLogger(TokenManager.class);

    @Autowired
    private Key jwtKeySpec;

    private CustomCache<UserToken>

}
