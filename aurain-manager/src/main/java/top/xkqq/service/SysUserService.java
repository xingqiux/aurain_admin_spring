package top.xkqq.service;


import org.springframework.beans.factory.annotation.Autowired;
import top.xkqq.dto.LoginDto;
import top.xkqq.mapper.SysUserMapper;
import top.xkqq.vo.system.LoginVo;

public interface SysUserService {


    /**
     * 根据用户名称查询用户数据
     */
    public abstract LoginVo login(LoginDto loginDto);
}
