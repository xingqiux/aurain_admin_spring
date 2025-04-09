package top.xkqq.product.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.xkqq.dto.AssginRoleDto;
import top.xkqq.dto.LoginDto;
import top.xkqq.dto.SysUserDto;
import top.xkqq.entity.system.SysUser;
import top.xkqq.vo.system.LoginVo;

public interface SysUserService extends IService<SysUser> {


    /**
     * 根据用户名称查询用户数据
     */
    LoginVo login(LoginDto loginDto);

    /**
     * 根据 token 获取用户信息
     */
    SysUser getUserInfo(String Authorization);

    /**
     * 根据用户id删除用户信息
     */
    void logout(String token);

    Page<SysUser> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto);

    void doAssgin(AssginRoleDto assginRoleDto);
}

