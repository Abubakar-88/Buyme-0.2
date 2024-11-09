package com.buyme.admin;

import com.buyme.admin.article.ArticleService;
import com.buyme.admin.category.CategoryRepository;
import com.buyme.admin.category.CategoryService;
import com.buyme.admin.customer.CustomerService;
import com.buyme.admin.dashboard.DashboardInfo;
import com.buyme.admin.dashboard.DashboardService;
import com.buyme.admin.menu.MenuRepository;
import com.buyme.admin.menu.MenuService;
import com.buyme.admin.order.OrderRepository;
import com.buyme.admin.order.OrderService;
import com.buyme.admin.product.ProductRepository;
import com.buyme.admin.product.ProductService;
import com.buyme.admin.question.QuestionRepository;
import com.buyme.admin.question.QuestionService;
import com.buyme.admin.review.ReviewRepository;
import com.buyme.admin.review.ReviewService;
import com.buyme.admin.setting.SettingRepository;
import com.buyme.admin.shippingrate.ShippingRateService;
import com.buyme.admin.user.UserRepository;
import com.buyme.admin.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.buyme.admin.repository.ArticleRepository;

@Controller
public class MainController {

	@Autowired
	private DashboardService dashboardService;

	@GetMapping("")
	public String viewHomePage(Model model) {
		DashboardInfo summary = dashboardService.loadSummary();
		model.addAttribute("summary", summary);
		return "index";
	}
	@GetMapping("/login")
	public String viewLoginPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}
		
		return "redirect:/";
	}
}
