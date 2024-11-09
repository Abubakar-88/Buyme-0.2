package com.buyme.setting;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.buyme.common.entity.menu.Menu;
import com.buyme.menu.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.buyme.common.entity.setting.Setting;

import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat.URI;

@Component
@Order(-121)
public class SettingFilter implements Filter {
	private SettingService service;
	private static final Logger LOGGER = LoggerFactory.getLogger(com.buyme.setting.SettingFilter.class);
	private MenuService menuService;

	@Autowired
	public SettingFilter(SettingService service, MenuService menuService) {
		super();
		this.service = service;
		this.menuService = menuService;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		String url = servletRequest.getRequestURL().toString();
		
		if (url.endsWith(".css") || url.endsWith(".js") || url.endsWith(".png") ||
				url.endsWith(".jpg")) {
			chain.doFilter(request, response);
			return;
		}
		
		List<Setting> generalSettings = service.getGeneralSettings();
		
		generalSettings.forEach(setting -> {
			request.setAttribute(setting.getKey(), setting.getValue());
			System.out.println(setting.getKey() + " == > " + setting.getValue());
		});
		loadGeneralSettings(request);
		loadMenuSettings(request);
		chain.doFilter(request, response);

	}
	private void loadMenuSettings(ServletRequest request) {

		LOGGER.info("SettingFilter | loadMenuSettings is called");

		List<Menu> headerMenuItems = menuService.getHeaderMenuItems();
		LOGGER.info("SettingFilter | loadMenuSettings | headerMenuItems size : " + headerMenuItems.size());
		request.setAttribute("headerMenuItems", headerMenuItems);

		List<Menu> footerMenuItems = menuService.getFooterMenuItems();
		LOGGER.info("SettingFilter | loadMenuSettings | footerMenuItems size : " + footerMenuItems.size());
		request.setAttribute("footerMenuItems", footerMenuItems);
	}

	private void loadGeneralSettings(ServletRequest request) {

		LOGGER.info("SettingFilter | loadGeneralSettings is called");

		List<Setting> generalSettings = service.getGeneralSettings();

		generalSettings.forEach(setting -> {
			LOGGER.info("SettingFilter | loadGeneralSettings | generalSettings : " + generalSettings);
			request.setAttribute(setting.getKey(), setting.getValue());
		});

		  request.setAttribute("/", URI);

	}
}
