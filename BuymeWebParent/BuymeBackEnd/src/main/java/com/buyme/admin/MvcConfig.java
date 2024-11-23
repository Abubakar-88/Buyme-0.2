package com.buyme.admin;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.buyme.admin.paging.PagingAndSortingArgumentResolver;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		exposeDirectory("/root/Buyme-0.2/user-photos", "user-photos", registry);
		exposeDirectory("/root/Buyme-0.2/category-images", "category-images", registry);
		exposeDirectory("/root/Buyme-0.2/brand-logos", "brand-logos", registry);
		exposeDirectory("/root/Buyme-0.2/product-images", "product-images", registry);
		exposeDirectory("/root/Buyme-0.2/site-logo", "site-logo", registry);
	}

	private void exposeDirectory(String absolutePath, String logicalName, ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/" + logicalName + "/**")
				.addResourceLocations("file:" + absolutePath + "/");
	}
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new PagingAndSortingArgumentResolver());
	}

}
