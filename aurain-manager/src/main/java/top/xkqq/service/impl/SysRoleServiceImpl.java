package top.xkqq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.xkqq.dto.SysRoleDto;
import top.xkqq.entity.system.SysRole;
import top.xkqq.mapper.SysRoleMapper;
import top.xkqq.service.SysRoleService;

import java.util.List;

// 接口实现类
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper ;

    @Override
    public PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer pageNum, Integer pageSize) {
        // 设置分页的页码和每页显示的条数
        PageHelper.startPage(pageNum , pageSize) ;
        List<SysRole> sysRoleList = sysRoleMapper.findByPage(sysRoleDto) ;
        PageInfo<SysRole> pageInfo = new PageInfo(sysRoleList) ;
        return pageInfo;
    }

}
