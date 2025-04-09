package top.xkqq.event;

import org.springframework.context.ApplicationEvent;
import top.xkqq.entity.system.SysOperLog;


// 创建日志事件
public class SysLogEvent extends ApplicationEvent {


    private final SysOperLog sysOperLog;

    public SysLogEvent(Object source, SysOperLog sysOperLog) {
        super(source);
        this.sysOperLog = sysOperLog;
    }

    public SysOperLog getSysOperLog() {
        return sysOperLog;
    }


}
