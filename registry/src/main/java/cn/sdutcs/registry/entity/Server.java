package cn.sdutcs.registry.entity;

import java.util.Date;

public class Server {
    public Date regisTime;
    public Date lastRegisTime;
    public String addr;
    public String status;

    public Server(Date regisTime, Date lastRegisTime, String addr, String status) {
        this.regisTime = regisTime;
        this.lastRegisTime = lastRegisTime;
        this.addr = addr;
        this.status = status;
    }

    public void setLastRegisTime(Date lastRegisTime) {
        this.lastRegisTime = lastRegisTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
