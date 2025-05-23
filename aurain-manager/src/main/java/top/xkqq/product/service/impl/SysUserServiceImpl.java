package top.xkqq.product.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import top.xkqq.common.exception.AurainException;
import top.xkqq.dto.AssginRoleDto;
import top.xkqq.dto.LoginDto;
import top.xkqq.dto.SysUserDto;
import top.xkqq.entity.system.SysRoleUser;
import top.xkqq.entity.system.SysUser;
import top.xkqq.product.mapper.SysRoleUserMapper;
import top.xkqq.product.mapper.SysUserMapper;
import top.xkqq.product.service.SysUserService;
import top.xkqq.vo.common.ResultCodeEnum;
import top.xkqq.vo.system.LoginVo;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {


    @Autowired
    private  SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    //注入 RedisTemplate 对象操作 Redis 数据库
    @Autowired
    private RedisTemplate redisTemplate;

    // 用户登录
    @Override
    public LoginVo login(LoginDto loginDto) {

        /**
         * 0.1 校验验证码，如果验证码错误，返回错误信息，
         * 0.2 如果正确，删除验证码，进入下一步操作
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

        String captchaKey = loginDto.getCodeKey();

        String captchaCode = (String) redisTemplate.opsForValue().get("user:validate" + captchaKey);

        // 检查用户输入的验证码是否为空或不匹配服务器生成的验证码
        if(StrUtil.isEmpty(captchaCode) || !StrUtil.equalsIgnoreCase(captchaCode,loginDto.getCaptcha())){
            // 如果这个验证码不存在或者验证码错误 -> 抛出验证码错误异常
            throw new AurainException(ResultCodeEnum.CAPTCHA_ERROR);
        }
        // 删除这个验证码
        redisTemplate.delete("user:validate" + captchaKey);


        // 1.获取用户名
        String userName = loginDto.getUserName();
        System.out.println(userName);

        // 2.在表中查询数据是否存在
        SysUser sysUser = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, userName));
//        SysUser sysUser = sysUserMapper.selectByUserName(userName);

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
                                       sysUser,         //这里可以直接存储 sysUser对象,因为在 redis 的配置类中设置了值为 Json 的序列化存储
                                         7,
                                         TimeUnit.DAYS);
        // 第一个参数 key ，第二个参数是 value, 第三个时时间的值，第四个是单位

        // 9.返回 LoginVo 对象
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);

        return loginVo;
    }


    /**
     * 根据用户的 toke 获取用户信息
     * 1. 根据用户的 token 在 redis 中查找对应的用户信息
     * @param token
     * @return
     */
    @Override
    public SysUser getUserInfo(String token) {
        return (SysUser)redisTemplate.opsForValue().get("user:login" + token);
    }


    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login" + token);
    }

    @Override
    public Page<SysUser> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto) {

        // 分页配置
        Page<SysUser> sysUserPage = new Page<>(pageNum, pageSize);

        // 创建 Wrapper 设定查询
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();

        if (sysUserDto != null) {
            queryWrapper.like(StrUtil.isNotEmpty(sysUserDto.getKeyword()), SysUser::getUsername, sysUserDto.getKeyword());
            // 如果开始时间不空，就查询开始时间之后的数据
            queryWrapper.ge(StrUtil.isNotEmpty(sysUserDto.getCreateTimeBegin()), SysUser::getCreateTime, sysUserDto.getCreateTimeBegin());
            // 如果结束时间不空，就查询结束时间之前的数据
            queryWrapper.le(StrUtil.isNotEmpty(sysUserDto.getCreateTimeEnd()), SysUser::getCreateTime, sysUserDto.getCreateTimeEnd());
        }


        // 进行分页查询
        return sysUserMapper.selectPage(sysUserPage, queryWrapper);
    }

    // 实现存储用户与权限角色信息
    @Override
    @Transactional
    public void doAssgin(AssginRoleDto assginRoleDto) {
        // 删除之前的所有的用户所对应的角色数据
        sysRoleUserMapper.delete(new LambdaQueryWrapper<SysRoleUser>().eq(SysRoleUser::getUserId, assginRoleDto.getUserId()));


        // 分配新的角色数据
        // 获取DTO中的角色ID列表
        List<Long> roleIdList = assginRoleDto.getRoleIdList();

        // 遍历角色ID列表，为用户分配每个角色
        roleIdList.forEach(roleId -> {
            // 调用SysRoleUserMapper的doAssign方法，为指定用户分配角色
            sysRoleUserMapper.doAssign(assginRoleDto.getUserId(), roleId);
        });
    }

}
