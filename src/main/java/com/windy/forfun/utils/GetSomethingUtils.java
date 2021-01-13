/*    */ package com.windy.forfun.utils;
/*    */

import com.alibaba.fastjson.JSON;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/*    */
/*    */ public class GetSomethingUtils {
/*    */   public static String sendRequest(String url) {
/*    */     try {
/* 14 */       URL obj = new URL(url);
/* 15 */       HttpURLConnection con = null;
/*    */       
/* 17 */       con = (HttpURLConnection)obj.openConnection();
/*    */ 
/*    */ 
/*    */       
/* 21 */       con.setRequestMethod("GET");
/*    */ 
/*    */       
/* 24 */       con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.122 Safari/537.36");
/*    */       
/* 26 */       int responseCode = con.getResponseCode();
/* 27 */       System.out.println("\nSending 'GET' request to URL : " + url);
/* 28 */       System.out.println("Response Code : " + responseCode);
/*    */ 
/*    */       
/* 31 */       BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
/*    */       
/* 33 */       StringBuffer response = new StringBuffer();
/*    */       String inputLine;
/* 35 */       while ((inputLine = in.readLine()) != null) {
/* 36 */         response.append(inputLine);
/*    */       }
/* 38 */       in.close();
/*    */ 
/*    */       
/* 41 */       return response.toString();
/* 42 */     } catch (IOException e) {
/* 43 */       e.printStackTrace();
/*    */       
/* 45 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static InputStream sendRequestStream(String url) {
/*    */     try {
/* 51 */       URL obj = new URL(url);
/* 52 */       HttpURLConnection con = null;
/*    */       
/* 54 */       con = (HttpURLConnection)obj.openConnection();
/*    */ 
/*    */ 
/*    */       
/* 58 */       con.setRequestMethod("GET");
/*    */ 
/*    */       
/* 61 */       con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.122 Safari/537.36");
/*    */       
/* 63 */       int responseCode = con.getResponseCode();
/* 64 */       System.out.println("\nSending 'GET' request to URL : " + url);
/*    */       
/* 66 */       return con.getInputStream();
/* 67 */     } catch (IOException e) {
/* 68 */       e.printStackTrace();
/*    */       
/* 70 */       return null;
/*    */     } 
/*    */   } public static Map<String, Object> parseJsonString(String jsonString) {
/* 73 */     Map<String, Object> maps = (Map<String, Object>) JSON.parse(jsonString);
/* 74 */     return maps;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void fileUtils(BufferedInputStream bis, File f, String folderName, String fileName) {
/*    */     try {
/* 82 */       File coverFolder = new File(folderName);
/* 83 */       if (!coverFolder.exists()) {
/* 84 */         coverFolder.mkdirs();
/*    */       }
/* 86 */       File coverFile = new File(folderName + fileName);
/* 87 */       System.out.println("-=-=-=-=-0=-023-=4023=-042=-304-=3204=2304-=23402" + folderName + File.separator + fileName);
/* 88 */       coverFile.createNewFile();
/* 89 */       BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(coverFile));
/* 90 */       byte[] buffer = new byte[4094];
/* 91 */       int n = 0;
/* 92 */       while ((n = bis.read(buffer)) != -1) {
/* 93 */         bos.write(buffer, 0, n);
/*    */       }
/* 95 */       bos.flush();
/* 96 */     } catch (IOException e) {
/* 97 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\justl\Desktop\ROOT\WEB-INF\classes\!\com\yellow\forfu\\utils\GetSomethingUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */