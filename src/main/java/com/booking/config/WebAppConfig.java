package com.booking.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

//相當於mvc-servlet.xml的Java程式組態

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.booking" })
public class WebAppConfig implements WebMvcConfigurer {

	@Bean
	public MappingJackson2JsonView jsonView() {
		MappingJackson2JsonView jv = new MappingJackson2JsonView();
		jv.setPrettyPrint(true);
		return jv;
	}

	@Bean
	public ContentNegotiatingViewResolver cnViewResolver() {
		ContentNegotiatingViewResolver cnv = new ContentNegotiatingViewResolver();
		ArrayList<View> lists = new ArrayList<View>();
		lists.add(jsonView());
		cnv.setDefaultViews(lists);
		return cnv;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/adminsystem/**").addResourceLocations("/adminsystem/");
	}

	@Bean
	public InternalResourceViewResolver irViewResolver() {
		InternalResourceViewResolver irv1 = new InternalResourceViewResolver("/booking/adminsystem/", ".jsp");
		irv1.setOrder(1);
		return irv1;
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

}
