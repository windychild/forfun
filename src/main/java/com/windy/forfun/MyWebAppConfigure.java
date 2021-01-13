package com.windy.forfun;/*    */
/*    */ 
/*    */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*    */
/*    */ @Configuration
/*    */ public class MyWebAppConfigure
/*    */   implements WebMvcConfigurer {
/*    */   @Value("${mappingFilePath}")
/*    */   private String mappingFilePath;
/*    */   
/*    */   public void addResourceHandlers(ResourceHandlerRegistry registry) {
/* 15 */     registry.addResourceHandler(new String[] { "/fff/**" }).addResourceLocations(new String[] { "file:" + this.mappingFilePath });
/*    */   }
/*    */ }


/* Location:              C:\Users\justl\Desktop\ROOT\WEB-INF\classes\!\com\yellow\forfun\com.yellow.forfun.MyWebAppConfigure.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */