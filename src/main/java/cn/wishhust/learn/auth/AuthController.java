package cn.wishhust.learn.auth;


import cn.wishhust.learn.auth.pojo.LoginRequest;
import cn.wishhust.learn.auth.pojo.LoginResponse;
import cn.wishhust.learn.utils.IJSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    public IJSONResult login(@RequestBody LoginRequest loginRequest) {
        return IJSONResult.ok(authService.login(loginRequest));
    }

    @GetMapping("test")
    public String test() {
        return "test";
    }
}
