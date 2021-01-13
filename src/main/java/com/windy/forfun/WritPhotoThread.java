package com.windy.forfun;/*    */
/*    */ 
/*    */

import com.windy.forfun.utils.GetSomethingUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*    */
/*    */ public class WritPhotoThread
/*    */   extends Thread {
/*    */   private Map<String, Object> maps;
/*    */   private File f;
/*    */   
/*    */   public WritPhotoThread(Map<String, Object> maps, File f, String writePath, String basePath, String parentFolderName) {
/* 19 */     this.maps = maps;
/* 20 */     this.f = f;
/* 21 */     this.writePath = writePath;
/* 22 */     this.basePath = basePath;
/* 23 */     this.parentFolderName = parentFolderName;
/*    */   }
/*    */   String basePath; String writePath;
/*    */   String parentFolderName;
/*    */   
/*    */   public void run() {
/* 29 */     File coverFolder = new File(this.f.getAbsolutePath() + File.separator + "cover");
/* 30 */     if (!coverFolder.exists()) {
/* 31 */       String cover = (String)this.maps.get("cover");
/* 32 */       BufferedInputStream bis = new BufferedInputStream(GetSomethingUtils.sendRequestStream(cover));
/* 33 */       String fullPath = this.writePath + this.basePath + File.separator + "cover" + File.separator;
/* 34 */       GetSomethingUtils.fileUtils(bis, this.f, fullPath, "cover.jpg");
/*    */       
/* 36 */       this.maps.put("coverLocalPath", this.basePath + File.separator + "cover.jpg");
/*    */     } 
/*    */ 
/*    */     
/* 40 */     File screenshotsFolder = new File(this.f.getAbsolutePath() + File.separator + "screenshots");
/* 41 */     if (!screenshotsFolder.exists()) {
/* 42 */       List<String> screenshotsList = (List<String>)this.maps.get("screenshots");
/* 43 */       List<String> screenshotsLocalPathList = new ArrayList<>();
/* 44 */       for (int i = 0; i < screenshotsList.size(); i++) {
/* 45 */         BufferedInputStream bis2 = new BufferedInputStream(GetSomethingUtils.sendRequestStream(screenshotsList.get(i)));
/* 46 */         String fullPath = this.writePath + this.basePath + File.separator + "screenshots" + File.separator;
/* 47 */         GetSomethingUtils.fileUtils(bis2, this.f, fullPath, i + ".jpg");
/* 48 */         screenshotsLocalPathList.add(this.basePath + File.separator + "screenshots" + File.separator + i + ".jpg");
/*    */       } 
/* 50 */       this.maps.put("screenshotsLocalPath", screenshotsLocalPathList);
/*    */     } 
/*    */ 
/*    */     
/* 54 */     File starsFolder = new File(this.f.getAbsolutePath() + File.separator + "stars");
/* 55 */     if (!starsFolder.exists()) {
/* 56 */       List<Map<String, String>> starsList = (List<Map<String, String>>)this.maps.get("stars");
/* 57 */       for (int i = 0; i < starsList.size(); i++) {
/* 58 */         BufferedInputStream bis2 = new BufferedInputStream(GetSomethingUtils.sendRequestStream((String)((Map)starsList.get(i)).get("img")));
/* 59 */         String starName = (String)((Map)starsList.get(i)).get("name");
/* 60 */         String fullPath = this.writePath + this.basePath + File.separator + "stars" + File.separator;
/* 61 */         GetSomethingUtils.fileUtils(bis2, this.f, fullPath, starName + ".jpg");
/* 62 */         ((Map<String, String>)starsList.get(i)).put("localPath", this.basePath + File.separator + "stars" + File.separator + starName + ".jpg");
/*    */       } 
/*    */       
/* 65 */       this.maps.put("stars", starsList);
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 70 */     File[] files = this.f.listFiles();
/*    */     
/* 72 */     List<String> l = new ArrayList<>();
/* 73 */     for (int j = 0; j < files.length; j++) {
/* 74 */       if (files[j].isFile()) {
/* 75 */         String fn = files[j].getName();
/* 76 */         fn = fn.substring(0, fn.lastIndexOf("."));
/* 77 */         if (files[j].getName().contains("mp4") || files[j].getName().contains("mkv") || files[j].getName().contains("avi")) {
/* 78 */           l.add(this.parentFolderName + File.separator + fn + File.separator + files[j].getName());
/*    */         }
/*    */       } 
/*    */     } 
/* 82 */     this.maps.put("fileName", l);
/*    */     
/* 84 */     String jsonFileName = this.writePath + this.basePath + File.separator + "info.json";
/* 85 */     File jsonFile = new File(jsonFileName);
/*    */     try {
/* 87 */       BufferedWriter bw = new BufferedWriter(new FileWriter(jsonFile));
/* 88 */       bw.write(this.maps.toString());
/* 89 */       bw.flush();
/* 90 */     } catch (IOException e) {
/* 91 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\justl\Desktop\ROOT\WEB-INF\classes\!\com\yellow\forfun\com.yellow.forfun.WritPhotoThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */