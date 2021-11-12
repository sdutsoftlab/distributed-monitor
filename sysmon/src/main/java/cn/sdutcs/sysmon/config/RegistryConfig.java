package cn.sdutcs.sysmon.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RegistryConfig {
    @Value("${registry.host}")
    private String host;

    @Value("${registry.port}")
    private String port;

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }
}
