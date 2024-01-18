package com.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.shop.service.CategoryService;
import com.shop.service.ProductService;


@Controller
public class HomeController {
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping({"/","/home"})
	public String home(Model model)
	{
		return "index";
	}
	
	@GetMapping("/shop")
	public String shop(Model model)
	{
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getAllProduct());
		return "shop";
	}
	@GetMapping("/shop/category/{id}")
	public String shopByCategory(Model model, @PathVariable int id)
	{
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getAllProductByCategoryId(id));
		return "shop";
	}
	
	@GetMapping("/shop/viewproduct/{id}")
	public String viewProduct(Model model, @PathVariable long id)
	{
		model.addAttribute("product", productService.getProductById(id).get());
		return "viewProduct";
	}
}
