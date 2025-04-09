package top.xkqq.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.xkqq.entity.system.SysUser;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser>{

    /**
     * 根据用户名查询用户数据
     * @param userName
     * @return
     */
    SysUser selectByUserName(String userName);
}
