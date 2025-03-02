package top.xkqq.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.xkqq.entity.system.SysUser;

@Mapper
public interface SysUserMapper {

    /**
     * 根据用户名查询用户数据
     * @param userName
     * @return
     */
    public abstract SysUser selectByUserName(String userName);
}
