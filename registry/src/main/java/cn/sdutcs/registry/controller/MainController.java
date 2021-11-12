package cn.sdutcs.registry.controller;


import cn.sdutcs.registry.cmd.Count;
import cn.sdutcs.registry.store.Resource;
import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 在是一个普通的 Controller 类
 */
@Controller
@CrossOrigin
public class MainController {
    /**
     * 路由 /index
     * 返回 index 这里默认配置自动映射到 templates/index.ftl
     */
    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("welcome", "hello fishpro");
        return "index";
    }

    /**
     * 代理查询实例状态
     */
    @GetMapping("/detail")
    public String detail(Model model, @RequestParam("addr") String addr) {
        if (addr.startsWith("192.168.2.163")) {
            addr = "127.0.0.1" + addr.substring(addr.length() - 5);
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json; charset=UTF-8");

        // 请求实体，加入请求参数和请求头
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> resp = null;

        String remote = String.format("http://%s/serverInfo", addr);
        System.out.println(remote);
        resp = restTemplate.exchange(remote, HttpMethod.GET, requestEntity, String.class);
        // 获取body数据，直接赋值给Map
        Map map = JSON.parseObject(resp.getBody(), Map.class);
        Map cpu = (Map) map.get("cpu");
        Map jvm = (Map) map.get("jvm");
        Map mem = (Map) map.get("mem");
        Map sys = (Map) map.get("sys");

        model.addAttribute("cpu", cpu);
        model.addAttribute("jvm", jvm);
        model.addAttribute("mem", mem);
        model.addAttribute("sys", sys);
        return "detail";
    }

    static ExecutorService service = Executors.newSingleThreadExecutor(); //单一线程

    /**
     * 代理关闭实例
     */
    @GetMapping("/shutdown")
    public String shutdown(Model model, @RequestParam("addr") String addr) {
        if (addr.startsWith("192.168.2.163")) {
            addr = "127.0.0.1" + addr.substring(addr.length() - 5);
        }
        System.out.println("shutdown " + addr);

        String finalAddr = addr;
        Runnable run = new Runnable() {
            @Override
            public void run() {
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders httpHeaders = new HttpHeaders();

                // 请求实体，加入请求参数和请求头
                HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(httpHeaders);

                String remote = String.format("http://%s/shutdown", finalAddr);
                ResponseEntity<String> resp = restTemplate.exchange(remote, HttpMethod.GET, requestEntity, String.class);
                System.out.println("shutdown " + resp);
            }
        };

        service.execute(run);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "index";
    }

    /**
     * 运行任务（传入的cmd）
     */
    @GetMapping("/runWork")
    @ResponseBody
    public String runWork(@RequestParam("cmd") String cmd) {
        System.out.println(cmd);
        String[] run = cmd.split(" ");
        if (run.length < 2) {
            return "指令错误";
        }

        // 执行任务
        if ("count".equals(run[0])) {
            File file = new File(run[1]);
            if (!file.exists()) {
                return "file not exist";
            }

            int success = Count.doWork(file, Resource.Instances.keySet().toArray(new String[0]));
            return String.format("%d success, %d failed", success, Resource.Instances.size() - success);

        } else if ("othercmd".equals(run[0])) {
            // TODO 实现其他类似指令
        }

        return "not supported cmd";
    }
}