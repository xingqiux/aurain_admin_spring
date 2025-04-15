package top.xkqq.service;

import org.springframework.stereotype.Service;
import top.xkqq.dto.UserLoginDto;
import top.xkqq.dto.UserRegisterDto;
import top.xkqq.vo.user.UserInfoVo;

@Service
public interface UserInfoService {
    void register(UserRegisterDto userRegisterDto);

    Object login(UserLoginDto userLoginDto);

    UserInfoVo getCurrentUserInfo(String token);
}
