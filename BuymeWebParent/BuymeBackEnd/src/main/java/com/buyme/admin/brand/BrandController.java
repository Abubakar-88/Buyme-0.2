package com.buyme.admin.brand;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.buyme.admin.FileUploadUtil;
import com.buyme.admin.category.CategoryService;
import com.buyme.admin.paging.PagingAndSortingHelper;
import com.buyme.admin.paging.PagingAndSortingParam;
import com.buyme.common.entity.Brand;
import com.buyme.common.entity.Category;

@Controller
public class BrandController {
	private String defaultRedirectURL = "redirect:/brands/page/1?sortField=name&sortDir=asc";
	@Autowired private BrandService brandService;	
	@Autowired private CategoryService categoryService;
	
	@GetMapping("/brands")
	public String listFirstPage() {
		return defaultRedirectURL;
	}
	
	@GetMapping("/brands/page/{pageNum}")
	public String listByPage(
			@PagingAndSortingParam(listName = "listBrands", moduleURL = "/brands") PagingAndSortingHelper helper,
			@PathVariable(name = "pageNum") int pageNum
			) {
		brandService.listByPage(pageNum, helper);
		return "brands/brands";		
	}
	
	@GetMapping("/brands/new")
	public String newBrand(Model model) {
		List<Category> listCategories = categoryService.listCategoriesUsedInForm();
		
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("brand", new Brand());
		model.addAttribute("pageTitle", "Create New Brand");
		
		return "brands/brand_form";		
	}
	
	@PostMapping("/brands/save")
	public String saveBrand(Brand brand, @RequestParam("fileImage") MultipartFile multipartFile,
			RedirectAttributes ra) throws IOException {
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			brand.setLogo(fileName);
			
			Brand savedBrand = brandService.save(brand);
			String uploadDir = "../brand-logos/" + savedBrand.getId();
			
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			
		} else {
			brandService.save(brand);
		}
		
		ra.addFlashAttribute("message", "The brand has been saved successfully.");
		return defaultRedirectURL;		
	}
	
	@GetMapping("/brands/edit/{id}")
	public String editBrand(@PathVariable(name = "id") Integer id, Model model,
			RedirectAttributes ra) {
		try {
			Brand brand = brandService.get(id);
			List<Category> listCategories = categoryService.listCategoriesUsedInForm();
			
			model.addAttribute("brand", brand);
			model.addAttribute("listCategories", listCategories);
			model.addAttribute("pageTitle", "Edit Brand (ID: " + id + ")");
			
			return "brands/brand_form";			
		} catch (BrandNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			return defaultRedirectURL;
		}
	}
	
	@GetMapping("/brands/delete/{id}")
	public String deleteBrand(@PathVariable(name = "id") Integer id, 
			Model model,
			RedirectAttributes redirectAttributes) {
		try {
			brandService.delete(id);
			String brandDir = "../brand-logos/" + id;
			FileUploadUtil.removeDir(brandDir);
			
			redirectAttributes.addFlashAttribute("message", 
					"The brand ID " + id + " has been deleted successfully");
		} catch (BrandNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}
		
		return defaultRedirectURL;
	}	
}
