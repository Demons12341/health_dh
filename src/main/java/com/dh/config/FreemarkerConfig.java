package com.dh.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@Configuration
public class FreemarkerConfig {

   @Value("${spring.freemarker.template-loader-path}")
   private String templateLoaderPath;

   @Value("${spring.freemarker.charset}")
   private String defaultEncoding;

   @Bean
   public FreeMarkerConfigurer freeMarkerConfigurer() {
      FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
      configurer.setTemplateLoaderPath(templateLoaderPath);
      configurer.setDefaultEncoding(defaultEncoding);
      return configurer;
   }

   @Bean
   public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
      return new PropertySourcesPlaceholderConfigurer();
   }
}
