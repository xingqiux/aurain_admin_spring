package top.xkqq.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.xkqq.entity.system.SysOperLog;

@Mapper
public interface SysOperLogMapper {
    void insert(SysOperLog sysOperLog);


}
