package top.xkqq.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson2.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import top.xkqq.common.exception.AurainException;
import top.xkqq.dto.LoginDto;
import top.xkqq.entity.system.SysUser;
import top.xkqq.mapper.SysUserMapper;
import top.xkqq.service.SysUserService;
import top.xkqq.vo.common.ResultCodeEnum;
import top.xkqq.vo.system.LoginVo;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {


    @Autowired
    private  SysUserMapper sysUserMapper;

    //注入 RedisTemplate 对象操作 Redis 数据库
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    // 用户登录
    @Override
    public LoginVo login(LoginDto loginDto) {

        /**
         * 0.1 校验验证码，如果验证码错误，返回错误信息，
         * 0.2如果正确，删除验证码，进入下一步操作
         * 1. 获取 Dto 用户名
         * 2. 根据用户名在 sys_user 表中查询数据
         * 3. 如果查询不到，说明用户不存在，返回错误信息
         * 4. 根据用户名查询到用户信息，用户存在
         * 5. 获取输入的密码，比较输入的密码和数据库中密码是否一致
         * 6. 如果密码一致,登陆成功，密码不一致登陆失败
         * 7. 登录成功生成唯一 token
         * 8. 把登陆成功的用户信息存放在 redis 中
         * 9. 返回 LoginVo
         */

        String captchaKey = loginDto.getCaptchaKey();
        redisTemplate.


        // 1.获取用户名
        String userName = loginDto.getUserName();

        // 2.在表中查询数据是否存在
        SysUser sysUser = sysUserMapper.selectByUserName(userName);

        // 3.查不到，说明用户不存在返回错误信息
        if(sysUser == null){
            //抛出自定义异常
            throw new AurainException(ResultCodeEnum.LOGIN_ERROR);
        }

        //4. 如果查询到数据

        // 5.如果用户存在获取密码，比较与数据库中密码是否一致
        String password = loginDto.getPassword();
        String sysUserPassword = sysUser.getPassword();

        // 使用工具类给获取的密码 进行 md5 加密后进行比较
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        if (!password.equals(sysUserPassword)){
            //6.如果密码错误
            //抛出自定义异常
            throw new AurainException(ResultCodeEnum.LOGIN_ERROR);

        }
        //7.如果密码一致，登录成功，生成唯一 token
        //使用 UUID 生成唯一字符串
        String token = UUID.randomUUID().toString().replaceAll("-", "");

        //8.把登录信息存入到 redis 中
        redisTemplate.opsForValue().set("user:login"+token,
                                         JSON.toJSONString(sysUser),
                                         7,
                                         TimeUnit.DAYS);
        // 第一个参数 key ，第二个参数是 value, 第三个时时间的值，第四个是单位

        // 9.返回 LoginVo 对象
        LoginVo loginVo = new LoginVo();
        loginVo.setAccessToken("Bearer:"+token);
        loginVo.setRefreshToken("");
        loginVo.setTokenType("Bearer");
        loginVo.setExpiresIn(86400);



        return loginVo;
    }

}
