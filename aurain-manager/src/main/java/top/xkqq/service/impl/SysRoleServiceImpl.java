package top.xkqq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xkqq.dto.SysRoleDto;
import top.xkqq.entity.system.SysRole;
import top.xkqq.mapper.SysRoleMapper;
import top.xkqq.service.SysRoleService;

// 接口实现类
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper ;

    @Override
    public Page<SysRole> findByPage(SysRoleDto sysRoleDto, Integer pageNum, Integer pageSize) {
        // 设置分页的页码和每页显示的条数
        Page<SysRole> page = new Page<>(pageNum, pageSize);

        // 构建查询条件
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        if (sysRoleDto != null && sysRoleDto.getRoleName() != null && !sysRoleDto.getRoleName().isEmpty()) {
            queryWrapper.like(SysRole::getRoleName, sysRoleDto.getRoleName());
        }

        return sysRoleMapper.selectPage(page, queryWrapper);
    }

}
