package com.buyme.product;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.buyme.common.entity.question.Question;
import com.buyme.question.QuestionService;
import com.buyme.question.QuestionVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.buyme.ControllerHelper;
import com.buyme.category.CategoryService;
import com.buyme.common.entity.Category;
import com.buyme.common.entity.Customer;
import com.buyme.common.entity.Review;
import com.buyme.common.entity.product.Product;
import com.buyme.common.exception.CategoryNotFoundException;
import com.buyme.common.exception.ProductNotFoundException;
import com.buyme.review.ReviewService;
import com.buyme.review.vote.ReviewVoteService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {
	@Autowired private ProductService productService;
	@Autowired private CategoryService categoryService;
	@Autowired private ReviewService reviewService;
	@Autowired private ReviewVoteService voteService;
	@Autowired private ControllerHelper controllerHelper;
	@Autowired private ProductRepository productRepository;
	private QuestionService questionService;

	private QuestionVoteService questionVoteService;

	@Autowired
	public ProductController(QuestionService questionService,
							 QuestionVoteService questionVoteService) {

		this.questionService = questionService;

		this.questionVoteService = questionVoteService;
	}




	@GetMapping("/c/{category_alias}")
	public String viewCategoryFirstPage(@PathVariable("category_alias") String alias,
										Model model) {
		return viewCategoryByPage(alias, 1, model, null);
	}

	@GetMapping("/c/{category_alias}/page/{pageNum}")
	public String viewCategoryByPage(@PathVariable("category_alias") String alias,
									 @PathVariable("pageNum") int pageNum,
									 Model model, @RequestParam(name = "brand", required = false) List<String> brands) {


		try {
			Category category = categoryService.getCategory(alias);
			List<Category> listCategoryParents = categoryService.getCategoryParents(category);

			Page<Product> pageProducts = productService.listByCategory(pageNum, category.getId());
			List<Product> listProducts = pageProducts.getContent();

			long startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
			long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
			if (endCount > pageProducts.getTotalElements()) {
				endCount = pageProducts.getTotalElements();
			}


			model.addAttribute("currentPage", pageNum);
			model.addAttribute("totalPages", pageProducts.getTotalPages());
			model.addAttribute("startCount", startCount);
			model.addAttribute("endCount", endCount);
			model.addAttribute("totalItems", pageProducts.getTotalElements());
			model.addAttribute("pageTitle", category.getName());
			model.addAttribute("listCategoryParents", listCategoryParents);
			model.addAttribute("listProducts", listProducts);
			model.addAttribute("category", category);

			return "product/products_by_category";
		} catch (CategoryNotFoundException ex) {
			return "error/404";
		}
	}

	@GetMapping("/p/{product_alias}")
	public String viewProductDetail(@PathVariable("product_alias") String alias, Model model,
									HttpServletRequest request) {

		try {
			Product product = productService.getProduct(alias);
			List<Category> listCategoryParents = categoryService.getCategoryParents(product.getCategory());
			Page<Review> listReviews = reviewService.list3MostVotedReviewsByProduct(product);
			List<Question> listQuestions = questionService.getTop3VotedQuestions(product.getId());
			Customer customer = controllerHelper.getAuthenticatedCustomer(request);

			if (customer != null) {
				boolean customerReviewed = reviewService.didCustomerReviewProduct(customer, product.getId());
				voteService.markReviewsVotedForProductByCustomer(listReviews.getContent(), product.getId(), customer.getId());
				questionVoteService.markQuestionsVotedForProductByCustomer(listQuestions, product.getId(), customer.getId());
				if (customerReviewed) {
					model.addAttribute("customerReviewed", customerReviewed);
				} else {
					boolean customerCanReview = reviewService.canCustomerReviewProduct(customer, product.getId());
					model.addAttribute("customerCanReview", customerCanReview);
				}
			}
			int numberOfQuestions = questionService.getNumberOfQuestions(product.getId());
			model.addAttribute("listQuestions", listQuestions);
			model.addAttribute("numberOfQuestions", numberOfQuestions);
			model.addAttribute("listCategoryParents", listCategoryParents);
			model.addAttribute("product", product);
			model.addAttribute("listReviews", listReviews);
			model.addAttribute("pageTitle", product.getShortName());

			return "product/product_detail";
		} catch (ProductNotFoundException e) {
			return "error/404";
		}
	}

	@GetMapping("/search")
	public String searchFirstPage(String keyword, Model model) {
		return searchByPage(keyword, 1, model);
	}

	@GetMapping("/search/page/{pageNum}")
	public String searchByPage(String keyword,
							   @PathVariable("pageNum") int pageNum,
							   Model model) {
		Page<Product> pageProducts = productService.search(keyword, pageNum);
		List<Product> listResult = pageProducts.getContent();

		long startCount = (pageNum - 1) * ProductService.SEARCH_RESULTS_PER_PAGE + 1;
		long endCount = startCount + ProductService.SEARCH_RESULTS_PER_PAGE - 1;
		if (endCount > pageProducts.getTotalElements()) {
			endCount = pageProducts.getTotalElements();
		}

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", pageProducts.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", pageProducts.getTotalElements());
		model.addAttribute("pageTitle", keyword + " - Search Result");

		model.addAttribute("keyword", keyword);
		model.addAttribute("searchKeyword", keyword);
		model.addAttribute("listResult", listResult);

		return "product/search_result";
	}
}
