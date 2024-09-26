package com.booking.config;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

//相當於mvc-servlet.xml的Java程式組態

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.booking" })
public class WebAppConfig implements WebMvcConfigurer {
//
//	@Bean
//	public MappingJackson2JsonView jsonView() {
//		MappingJackson2JsonView jv = new MappingJackson2JsonView();
//		jv.setPrettyPrint(true);
//		return jv;
//	}

//	@Bean
//	public ContentNegotiatingViewResolver cnViewResolver() {
//		ContentNegotiatingViewResolver cnv = new ContentNegotiatingViewResolver();
//		ArrayList<View> lists = new ArrayList<View>();
//		lists.add(jsonView());
//		cnv.setDefaultViews(lists);
//		return cnv;
//	}

//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/adminsystem/**").addResourceLocations("/adminsystem/");
//	}

	@Bean
	public InternalResourceViewResolver irViewResolver() {
		InternalResourceViewResolver irv1 = new InternalResourceViewResolver("/", ".jsp");
		irv1.setOrder(1);
		return irv1;
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	// 設置 UTF-8 編碼的 StringHttpMessageConverter
	@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converters.add(stringConverter);
        MappingJackson2HttpMessageConverter mj2hmc = new MappingJackson2HttpMessageConverter();
        converters.add(mj2hmc);
    }

}
