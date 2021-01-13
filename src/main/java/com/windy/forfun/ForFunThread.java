package com.windy.forfun;


import com.windy.forfun.utils.GetSomethingUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


  public class ForFunThread extends Thread {
    private File[] fs;
    private RedisTemplate<String, String> redisTemplate;

    public ForFunThread(File[] fs, RedisTemplate<String, String> redisTemplate, String baseUrl, String writePath) {
/*  18 */     this.fs = fs;
/*  19 */     this.redisTemplate = redisTemplate;
/*  20 */     this.baseUrl = baseUrl;
/*  21 */     this.writePath = writePath;
    }
    private String baseUrl; private String writePath;
    public void run() {
/*  25 */     synchronized (this) {
/*  26 */       for (int i = 0; i < this.fs.length; i++) {
/*  27 */         System.out.println("第 " + i + "  个文件");
/*  28 */         File f = this.fs[i];
/*  29 */         String parentFolderName = f.getParent();
/*  30 */         parentFolderName = parentFolderName.substring(parentFolderName.lastIndexOf(File.separator) + 1);
/*  31 */         String basePath = parentFolderName + File.separator + f.getName();
/*  32 */         if (this.fs[i].isDirectory()) {
/*  33 */           String folderName = this.fs[i].getName();
/*  34 */           String jsonString = GetSomethingUtils.sendRequest(this.baseUrl + folderName);
/*  35 */           Map<String, Object> maps = GetSomethingUtils.parseJsonString(jsonString);

/*  37 */           if (maps != null) {



/*  41 */             List<Map<String, String>> list = (List<Map<String, String>>)maps.get("stars");
/*  42 */             for (int j = 0; j < list.size(); j++) {
/*  43 */               this.redisTemplate.boundValueOps("stars:" + (String)((Map)list.get(j)).get("name")).set((list.get(j)).get("img"));
              }


/*  47 */             String studio = (String)maps.get("studio");
/*  48 */             this.redisTemplate.boundSetOps("studio").add(studio);



/*  52 */             List<String> listTags = (List<String>)maps.get("tags");
/*  53 */             for (int k = 0; k < listTags.size(); k++) {
/*  54 */               String tag = listTags.get(k);
/*  55 */               this.redisTemplate.boundSetOps("tags").add(tag);
              }



/*  60 */             File coverFolder = new File(f.getAbsolutePath() + File.separator + "cover");
/*  61 */             if (!coverFolder.exists()) {
/*  62 */               String cover = (String)maps.get("cover");
/*  63 */               BufferedInputStream bis = new BufferedInputStream(GetSomethingUtils.sendRequestStream(cover));
/*  64 */               String fullPath = this.writePath + basePath  + File.separator;
/*  65 */               GetSomethingUtils.fileUtils(bis, f, fullPath, "cover.jpg");

/*  67 */               maps.put("coverLocalPath", basePath + File.separator + "cover.jpg");
              }


/*  71 */             File screenshotsFolder = new File(f.getAbsolutePath() + File.separator + "screenshots");
/*  72 */             if (!screenshotsFolder.exists()) {
/*  73 */               List<String> screenshotsList = (List<String>)maps.get("screenshots");
/*  74 */               List<String> screenshotsLocalPathList = new ArrayList<>();
/*  75 */               for (int n = 0; n < screenshotsList.size(); n++) {
/*  76 */                 BufferedInputStream bis2 = new BufferedInputStream(GetSomethingUtils.sendRequestStream(screenshotsList.get(n)));
/*  77 */                 String fullPath = this.writePath + basePath + File.separator + "screenshots" + File.separator;
/*  78 */                 GetSomethingUtils.fileUtils(bis2, f, fullPath, n + ".jpg");
/*  79 */                 screenshotsLocalPathList.add(basePath + File.separator + "screenshots" + File.separator + n + ".jpg");
                }
/*  81 */               maps.put("screenshotsLocalPath", screenshotsLocalPathList);
              }


/*  85 */             File starsFolder = new File(f.getAbsolutePath() + File.separator + "stars");
/*  86 */             if (!starsFolder.exists()) {
/*  87 */               List<Map<String, String>> starsList = (List<Map<String, String>>)maps.get("stars");
/*  88 */               for (int n = 0; n < starsList.size(); n++) {
/*  89 */                 BufferedInputStream bis2 = new BufferedInputStream(GetSomethingUtils.sendRequestStream((String)((Map)starsList.get(n)).get("img")));
/*  90 */                 String starName = (String)((Map)starsList.get(n)).get("name");
/*  91 */                 String fullPath = this.writePath + basePath + File.separator + "stars" + File.separator;
/*  92 */                 GetSomethingUtils.fileUtils(bis2, f, fullPath, starName + ".jpg");
/*  93 */                 ((Map<String, String>)starsList.get(n)).put("localPath", basePath + File.separator + "stars" + File.separator + starName + ".jpg");
                }

/*  96 */               maps.put("stars", starsList);
              }



/* 101 */             File[] files = f.listFiles();

/* 103 */             List<String> l = new ArrayList<>();
/* 104 */             for (int m = 0; m < files.length; m++) {
/* 105 */               if (files[m].isFile()) {
/* 106 */                 String parent = files[m].getParent();
/* 107 */                 parent = parent.substring(parent.lastIndexOf(File.separator) + 1);
/* 108 */                 if (files[m].getName().contains("mp4") || files[m].getName().contains("mkv") || files[m].getName().contains("avi")) {
/* 109 */                   l.add(parentFolderName + File.separator + parent + File.separator + files[m].getName());
                  }
                }
              }
/* 113 */             maps.put("fileName", l);

/* 115 */             String jsonFileName = this.writePath + basePath + File.separator + "info.json";
/* 116 */             File jsonFile = new File(jsonFileName);
              try {
/* 118 */               BufferedWriter bw = new BufferedWriter(new FileWriter(jsonFile));
/* 119 */               bw.write(maps.toString());
/* 120 */               bw.flush();
/* 121 */             } catch (IOException e) {
/* 122 */               e.printStackTrace();
              }

/* 125 */             String main_key = "main-key:" + folderName + "=" + maps.get("cover");
/* 126 */             this.redisTemplate.boundValueOps(main_key).set(maps.toString());


/* 129 */             String main_value = "main-value:" + maps.toString();
/* 130 */             this.redisTemplate.boundValueOps(main_value).set(main_key);
            } else {

/* 133 */             System.out.println(f.getName() + "    没有爬取到数据");
            }
          }
        }
      }
    }
  }


/* Location:              C:\Users\justl\Desktop\ROOT\WEB-INF\classes\!\com\yellow\forfun\com.yellow.forfun.ForFunThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */