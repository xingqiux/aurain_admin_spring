package top.xkqq.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import top.xkqq.common.exception.AurainException;
import top.xkqq.dto.UserLoginDto;
import top.xkqq.dto.UserRegisterDto;
import top.xkqq.entity.user.UserInfo;
import top.xkqq.mapper.UserInfoMapper;
import top.xkqq.service.UserInfoService;
import top.xkqq.vo.user.UserInfoVo;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;


    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Override
    @Transactional
    public void register(UserRegisterDto userRegisterDto) {
        String username = userRegisterDto.getUsername();
        String password = userRegisterDto.getPassword();
        String nickName = userRegisterDto.getNickName();

        if (username.isEmpty() || password.isEmpty() || nickName.isEmpty()) {
            throw new AurainException(500, "参数错误");
        }

        //校验校验验证码

        String codeValueRedis = redisTemplate.opsForValue().get("phone:code:" + username);

        if (!codeValueRedis.equals(userRegisterDto.getCode())) {
            throw new AurainException(200, "验证码错误");
        }

        // 获取数据库用户信息
        UserInfo userInfo = userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getUsername, username));
        if (null != userInfo) {
            throw new AurainException(200, "用户已存在");
        }

        // 保存用户信息
        userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setNickName(nickName);
        userInfo.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userInfo.setPhone(username);
        userInfo.setStatus(1);
        userInfo.setSex(0);
        userInfo.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");


        userInfoMapper.insert(userInfo);

        redisTemplate.delete("phone:code:" + username);
    }

    @Override
    public String login(UserLoginDto userLoginDto) {
        //校验参数
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();

        if (username.isEmpty() || password.isEmpty()) {
            throw new AurainException(200, "用户名或密码不得为空");
        }

        UserInfo userInfo = userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getUsername, username));

        if (userInfo == null) {
            throw new AurainException(200, "用户不存在");
        }


        //校验密码

        if (!userInfo.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
            throw new AurainException(200, "密码错误");
        }

        if (userInfo.getStatus() == 0) {
            throw new AurainException(200, "账号已被禁用");
        }

        //生成token并处理token
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set("user:login:" + token, JSON.toJSONString(userInfo), 30, TimeUnit.DAYS);

        return token;
    }

    @Override
    public UserInfoVo getCurrentUserInfo(String token) {
        String userInfoStr = redisTemplate.opsForValue().get("user:login:" + token);

        if (userInfoStr == null) {
            throw new AurainException(200, "用户信息不存在");
        }

        // 将 UserInfo 从字符串转换成对象
        UserInfo userInfo = JSON.parseObject(userInfoStr, UserInfo.class);

        UserInfoVo userInfoVo = new UserInfoVo();

        BeanUtils.copyProperties(userInfo, userInfoVo);

        return userInfoVo;
    }
}
