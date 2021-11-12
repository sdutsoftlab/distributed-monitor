package cn.sdutcs.sysmon.controller;

import cn.sdutcs.sysmon.core.ServerInfo;
import cn.sdutcs.sysmon.core.RuntimeInfo;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;

@RestController
@CrossOrigin
public class SystemInfoController implements ApplicationContextAware {

    @GetMapping("/runtimeInfo")
    public RuntimeInfo GetRuntimeInfo() throws UnknownHostException {
        return new RuntimeInfo();
    }

    /**
     * 获取服务器监控信息
     */
    @GetMapping("/serverInfo")
    public ServerInfo getServerInfo() throws Exception {
        ServerInfo server = new ServerInfo();
        server.copyTo();
        return server;
    }

    // 强制关闭Springboot进程
    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    @GetMapping("/shutdown")
    public String shutDownContext() {
        ConfigurableApplicationContext ctx = (ConfigurableApplicationContext) context;
        ctx.close();
        return "context is shutdown";
    }
}
