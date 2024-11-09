package com.buyme;

import java.util.List;

import com.buyme.category.CategoryService;
import com.buyme.section.SectionService;
import com.buyme.section.SectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.buyme.common.entity.Category;
import com.buyme.common.entity.section.Section;

@Controller
public class MainController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private SectionService sectionService;


	@GetMapping("")
	public String viewHomePage(Model model) {

		LOGGER.info("MainController | viewHomePage is called");

		List<Section> listSections = sectionService.listEnabledSections();
		model.addAttribute("listSections", listSections);

		LOGGER.info("MainController | viewHomePage | listSections : " + listSections.size());

		if (SectionUtil.hasAllCategoriesSection(listSections)) {
			List<Category> listCategories = categoryService.listNoChildrenCategories();
			model.addAttribute("listCategories", listCategories);
			LOGGER.info("MainController | viewHomePage | listCategories : " + listCategories.size());
		}


		return "index";
	}

	@GetMapping("/login")
	public String viewLoginPage() {

		LOGGER.info("MainController | viewLoginPage is called");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		LOGGER.info("MainController | viewLoginPage | authentication : " + authentication.toString());
		LOGGER.info("MainController | viewLoginPage | authentication instanceof AnonymousAuthenticationToken : " + (authentication instanceof AnonymousAuthenticationToken));

		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}

		return "redirect:/";
	}



}