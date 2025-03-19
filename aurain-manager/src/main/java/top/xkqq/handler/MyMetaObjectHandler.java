package top.xkqq.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component // 必须交给 Spring 管理
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "isDeleted", Integer.class, 0); // 逻辑删除默认值
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新时自动填充字段
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
    }
}
