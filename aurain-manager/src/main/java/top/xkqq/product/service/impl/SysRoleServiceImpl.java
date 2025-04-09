package top.xkqq.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xkqq.dto.SysRoleDto;
import top.xkqq.entity.system.SysRole;
import top.xkqq.entity.system.SysRoleUser;
import top.xkqq.product.mapper.SysRoleMapper;
import top.xkqq.product.mapper.SysRoleUserMapper;
import top.xkqq.product.service.SysRoleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 接口实现类
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper ;

    // 用于查询权限角色于用户的对应关系
    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

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

    /**
     * 用于实现查询用户所拥有的权限角色信息
     *
     * @param userId
     * @return
     */
    @Override
    public Map<String, Object> findAllRoles(String userId) {
        // 查询所有的用户角色
        List<SysRole> sysRoles = sysRoleMapper.selectList(null);

        // 创建查询 wrapper 查询用户所拥有的权限角色信息
        LambdaQueryWrapper<SysRoleUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleUser::getUserId, userId);

        List<SysRoleUser> sysRoleUsers = sysRoleUserMapper.selectList(queryWrapper);
        System.out.println("sysRoleUsers = " + sysRoleUsers);

        // 创建返回的 Map
        HashMap<String, Object> rolesMap = new HashMap<>();

        rolesMap.put("allRolesList", sysRoles);
        rolesMap.put("sysUserRoles", sysRoleUsers);

        return rolesMap;
    }

}
