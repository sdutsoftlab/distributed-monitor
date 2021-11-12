package cn.sdutcs.registry.cmd;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字符串计数命令
 */
public class Count {

    /**
     * 开启任务
     */
    public static int doWork(File file, String[] consumer) {
        ArrayList<String> subFiles = new ArrayList<>();
        for (String s : consumer) {
            String sub = file.getParent() + "/" + file.getName() + "-" + s;
            System.out.println(sub);
            subFiles.add(sub);
        }

        split(file, subFiles);

        int successNum = 0;
        for (int i = 0; i < consumer.length; i++) {
            if (sendRequest(consumer[i], subFiles.get(i))) {
                successNum++;
            }
        }
        merge(file, subFiles);

        return successNum;
    }

    /**
     * 分配任务给其他实例
     */
    private static boolean sendRequest(String addr, String file) {
        if (addr.startsWith("192.168.2.163")) {
            addr = "127.0.0.1" + addr.substring(addr.length() - 5);
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json; charset=UTF-8");

        // 请求实体，加入请求参数和请求头
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(httpHeaders);

        String remote = String.format("http://%s/count?file=%s", addr, file);
        ResponseEntity<String> resp = restTemplate.exchange(remote, HttpMethod.GET, requestEntity, String.class);

        String body = resp.getBody();
        return body.equals("\"ok\"");
    }

    /**
     * 文件拆分
     */
    private static void split(File file, ArrayList<String> subFiles) {
        if (file.canRead()) {
            try {
                FileReader read = new FileReader(file);
                BufferedReader br = new BufferedReader(read);
                String row;

                List<FileWriter> fileWriters = new ArrayList<FileWriter>();
                for (int i = 0; i < subFiles.size(); i++) {
                    fileWriters.add(new FileWriter(subFiles.get(i)));
                }

                int rownum = 1;
                while ((row = br.readLine()) != null) {
                    fileWriters.get(rownum % subFiles.size()).append(row).append("\r\n");
                    rownum++;
                }

                for (int i = 0; i < fileWriters.size(); i++) {
                    fileWriters.get(i).close();
                }
                br.close();
                read.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 文件归并
     */
    private static void merge(File file, ArrayList<String> subFiles) {
        Map<String, Integer> countMap = new HashMap<>();
        for (String subFile : subFiles) {
            try {
                FileReader read = new FileReader(subFile);
                BufferedReader br = new BufferedReader(read);

                String row;
                while ((row = br.readLine()) != null) {
                    String[] line = row.split(" ");

                    int add = Integer.parseInt(line[1]);
                    if (countMap.containsKey(line[0])) {
                        Integer integer = countMap.get(line[0]);
                        countMap.put(line[0], integer + add);
                    } else {
                        countMap.put(line[0], add);
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileWriter writer = new FileWriter(file.getAbsoluteFile() + "-result");
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
    }
}
