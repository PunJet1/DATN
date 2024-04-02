package com.watch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@SuppressWarnings("deprecation")
@Configuration
public class WebConfigCkFinder extends WebMvcConfigurerAdapter{
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//   file:D:\\data\\file\\image\\
		registry.addResourceHandler("/public/image/**").addResourceLocations("classpath:/static/uploadmedia/");
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/ckfinder/**").addResourceLocations("classpath:/static/assets/admin/main/ckfinder/");
		super.addResourceHandlers(registry);
	}
}