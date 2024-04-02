//package com.watch.config;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.ckfinder.connector.ConnectorServlet;
//@Configuration
//public class CKFinderServletConfig {
//    @Value("${ckeditor.storage.image.path}")
//    private String baseDir;
//    @Value("${ckeditor.access.image.url}")
//    private String baseURL;
//
//    @Bean
//    public ServletRegistrationBean connectCKFinder(){
//        ServletRegistrationBean registrationBean=new ServletRegistrationBean(new ConnectorServlet()
//        		,"/assets/admin/main/ckfinder/core/connector/java/connector.java");
//        registrationBean.addInitParameter("XMLConfig","classpath:/static/ckfinder.xml");
//        registrationBean.addInitParameter("debug","false");
//        registrationBean.addInitParameter("configuration","com.watch.config.CKFinderConfig");
//        //ckfinder.xml 
//        registrationBean.addInitParameter("baseDir",baseDir);
//        registrationBean.addInitParameter("baseURL",baseURL);
////        System.out.println(com.watch.config.CKFinderConfig);
//        return registrationBean;
//    }
//}