package cn.sdutcs.sysmon.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 计数控制器
 */

@RestController
public class CountController {

    @GetMapping("count")
    public String count(@RequestParam("file") String file) {
        System.out.println("Handle " + file);
        Map<String, Integer> countMap = new HashMap<>();

        // 统计计数
        try {
            FileReader read = new FileReader(file);
            BufferedReader br = new BufferedReader(read);

            String row;
            while ((row = br.readLine()) != null) {
                if (countMap.containsKey(row)) {
                    Integer integer = countMap.get(row);
                    countMap.put(row, integer + 1);
                } else {
                    countMap.put(row, 1);
                }
            }

            br.close();
            read.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 结果写回源文件
        System.out.println(countMap);
        try {
            FileWriter writer = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(writer);
            countMap.forEach((key, value) -> {
                try {
                    bw.write(key + " " + value + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "ok";
    }
}
