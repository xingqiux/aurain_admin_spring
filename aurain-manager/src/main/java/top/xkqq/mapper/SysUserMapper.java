package top.xkqq.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.xkqq.entity.system.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser>{

    /**
     * 根据用户名查询用户数据
     * @param userName
     * @return
     */
    public abstract SysUser selectByUserName(String userName);
}
