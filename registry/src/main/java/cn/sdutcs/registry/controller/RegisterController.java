package cn.sdutcs.registry.controller;

import cn.sdutcs.registry.entity.Server;
import cn.sdutcs.registry.store.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RegisterController {

    /**
     * 实例服务注册
     */
    @GetMapping("register")
    public Map register(HttpServletRequest request) {
        String addr = request.getHeader("server-register");

        if (Resource.Instances.containsKey(addr)) {
            Resource.Instances.get(addr).setLastRegisTime(new Date());
            Resource.Instances.get(addr).setStatus("OK");
        } else {
            Server server = new Server(new Date(), new Date(), addr, "OK");
            Resource.Instances.put(addr, server);
        }

        Map<String, Object> resp = new HashMap<String, Object>();
        resp.put("code", "200");
        return resp;
    }

    /**
     * 拉取所有实例
     */
    @GetMapping("instances")
    public Map getInstances(HttpServletRequest request) {
        Resource.Instances.forEach((key, value) -> {
            Date now = new Date();
            if (now.getTime() - value.lastRegisTime.getTime() >= 10000) {
                value.setStatus("Dead");
            }
        });

        return Resource.Instances;
    }
}
