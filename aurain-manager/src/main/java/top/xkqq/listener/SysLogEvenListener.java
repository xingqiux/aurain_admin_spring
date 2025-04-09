package top.xkqq.listener;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import top.xkqq.entity.system.SysOperLog;
import top.xkqq.event.SysLogEvent;
import top.xkqq.product.mapper.SysOperLogMapper;

@Component
@Slf4j
public class SysLogEvenListener {

    @Autowired
    private SysOperLogMapper sysOperLogMapper;


    // 使用事件监听器完成
    @Async
    @EventListener
    public void saveSysLog(SysLogEvent event) {
        SysOperLog sysOperLog = event.getSysOperLog();
        log.info("接收到日志事件：{}", sysOperLog.getTitle());
        sysOperLogMapper.insert(sysOperLog);
    }

}
