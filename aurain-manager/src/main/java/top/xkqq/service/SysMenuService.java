package top.xkqq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import top.xkqq.entity.system.SysMenu;

import java.util.List;

@Service
public interface SysMenuService extends IService<SysMenu> {
    List<SysMenu> findNodes();
}
