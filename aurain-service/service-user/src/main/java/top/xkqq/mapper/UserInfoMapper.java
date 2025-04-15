package top.xkqq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.xkqq.entity.user.UserInfo;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}
