package com.windy.forfun;


import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
public class FirstController {
    int pageSize = 15;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Value("${getInfoFilePath}")
    String getInfoFilePath;
    @Value("${writePath}")
    private String writePath;
    @Value("${getInfoBaseUrl}")
    String getInfoBaseUrl;

    @GetMapping({"/first"})
    public String first() {
        Set<String> keys = this.redisTemplate.keys("*");
        Iterator<String> iterator = keys.iterator();

        while (iterator.hasNext()) {
            String next = iterator.next();
            System.out.println(next);
        }

        return null;
    }

    @GetMapping({"/list"})
    public ForfunVo list(String keyWord,String pageNo) {
        String keyword_mark = null;
        ForfunVo forfunVo = new ForfunVo();
        if ("undefined".equals(pageNo) || pageNo == null || "null".equals(pageNo)) {
            pageNo = "1";
        }

        System.out.println(pageNo);
        try {
            keyWord = new String(keyWord.getBytes("iso-8859-1"), "utf-8");
            keyword_mark = keyWord;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("查询参数为" + keyWord);
        long start = System.currentTimeMillis();
        List<String> list = new ArrayList<>();
        Set<String> keys = null;
        if ("undefined".equals(keyWord) || keyWord == null || "null".equals(keyWord)) {
            keyWord = "main-key";
            keys = this.redisTemplate.keys("*" + keyWord + "*");
        } else {
            Set<String> values = this.redisTemplate.keys("main-value*" + keyWord + "*");

            Iterator<String> iterator1 = values.iterator();
            keys = new HashSet<>();
            while (iterator1.hasNext()) {
                String key = iterator1.next();
                String s = (String) this.redisTemplate.boundValueOps(key).get();
                keys.add(s);
            }
        }


        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {

            String next = iterator.next();
            String s = (String) this.redisTemplate.boundValueOps(next).get();
            list.add(s);

        }
        System.out.println(list.size());
        long end = System.currentTimeMillis();
        System.out.println("查询耗时：  " + (end - start));

        String[] arr = (String[]) list.stream().toArray(x$0 -> new String[x$0]);

        for (int i = arr.length - 1; i > 0; i--) {

            for (int j = 0; j < i; j++) {
                long fir = stringToLong(arr[j]).longValue();
                long sec = stringToLong(arr[j + 1]).longValue();
                if (fir < sec) {
                    swap(arr, j, j + 1);
                }
            }
        }
        int pageno = Integer.parseInt(pageNo);
        String arrb[] = Arrays.copyOfRange(arr, pageno*pageSize-5, pageno*pageSize);
        forfunVo.setArr(arrb);
        forfunVo.setNext ( pageno + 1);
        forfunVo.setPre(pageno == 1 ? 1 : pageno + 1);
        forfunVo.setKeyWord ( keyword_mark);
        forfunVo.setTotalCount(arr.length);
        return forfunVo;
    }

    static Long stringToLong(String mapString) {
        Map<String, String> map = (Map<String, String>) JSON.parse(mapString);
        String releaseDate = "2020-10-01";
        if (map != null) {
            releaseDate = map.get("releaseDate");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long date = 0L;
        try {
            Date parse = sdf.parse(releaseDate);
            date = parse.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Long.valueOf(date);
    }

    static void swap(String[] arr, int i, int j) {
        String temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    @GetMapping({"getInfo"})
    public String getInfo() {
        try {
            File file = new File(this.getInfoFilePath);

            File[] fs = file.listFiles();
            ForFunThread forfun = new ForFunThread(fs, this.redisTemplate, this.getInfoBaseUrl, this.writePath);

            forfun.start();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return "任务已运行";
    }

    @GetMapping("/oo")
    public String xxoo() {
        Set<String> keys = this.redisTemplate.keys("main-key*");
        Iterator<String> iterator1 = keys.iterator();

        while (iterator1.hasNext()) {
            String key = iterator1.next();
            //根据key找到value
            String value = (String) this.redisTemplate.boundValueOps(key).get();
            if (value.contains("cover/cover")) {
                value = value.replaceAll("cover/cover", "cover");
                redisTemplate.boundValueOps(key).set(value);
            }
            //System.out.println(value);
        }
        keys.size();
        return keys.size() + "";
    }


}

