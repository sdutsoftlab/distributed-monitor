package cn.sdutcs.sysmon.controller;

import cn.sdutcs.sysmon.config.RegistryConfig;
import cn.sdutcs.sysmon.core.RuntimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

@Component
@EnableScheduling
public class HeartBeat {

    @Autowired
    private RegistryConfig registryConfig;

    @Autowired
    Environment env;

    // cron 格式：[秒][分][小时][日][月][周][年]
    // 这里是每秒执行一次
    @Scheduled(cron = "*/1 * * * * ?")
    private void requestTasks() {
        String localHost = RuntimeUtils.getHostIP();
        String localPort = env.getProperty("local.server.port");
        String registryHost = registryConfig.getHost();
        String registryPort = registryConfig.getPort();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
        httpHeaders.add("server-register", localHost + ":" + localPort);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<Map> resp = null;

        String remote = String.format("http://%s:%s/register", registryHost, registryPort);
        resp = restTemplate.exchange(remote, HttpMethod.GET, requestEntity, Map.class);
        Map body = resp.getBody();
        System.out.println("HeartBeat: " + LocalDateTime.now() + " to " + remote + " resp: " + body);
    }
}
