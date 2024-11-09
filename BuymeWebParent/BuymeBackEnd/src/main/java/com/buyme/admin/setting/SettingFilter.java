package com.buyme.admin.setting;

import com.buyme.common.entity.setting.Setting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Component
@Order(-121)
public class SettingFilter implements Filter {
	private SettingService service;
	private static final Logger LOGGER = LoggerFactory.getLogger(SettingFilter.class);


	@Autowired
	public SettingFilter(SettingService service ) {
		super();
		this.service = service;
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
		
		List<Setting> generalSettings = service.getGeneralSettings2();
		
		generalSettings.forEach(setting -> {
			request.setAttribute(setting.getKey(), setting.getValue());
			System.out.println(setting.getKey() + " == > " + setting.getValue());
		});

		chain.doFilter(request, response);

	}



}
