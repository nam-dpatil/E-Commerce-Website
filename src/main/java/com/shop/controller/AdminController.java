 package com.shop.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.shop.dto.ProductDTO;
import com.shop.modal.Category;
import com.shop.modal.Product;
import com.shop.service.CategoryService;
import com.shop.service.ProductService;

@Controller
public class AdminController {
	public static String uploadDir = System.getProperty("user.dir") +"/src/main/resources/static/productImages";
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/admin")
	public String adminHome()
	{
		return "adminHome";
	}
	
	@GetMapping("/admin/categories")
	public String getCat(Model model)
	{
		model.addAttribute("categories", categoryService.getAllCategory());
		return "categories";
	}
	
	@GetMapping("/admin/categories/add")
	public String getCatAdd(Model model)
	{
		model.addAttribute("category", new Category());
		return "categoriesAdd";
	}
	
	@PostMapping("/admin/categories/add")
	public String postCatAdd(@ModelAttribute("category") Category category)
	{
		categoryService.addCategory(category);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCat(@PathVariable int id)
	{
		categoryService.removeCategoryById(id);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/update/{id}")
	public String updateCat(@PathVariable int id, Model model)
	{
		Optional<Category> category = categoryService.getCategoryById(id);
		if(category.isPresent())
		{
			model.addAttribute("category", category.get());
			return "categoriesAdd";
		}
		else {
			return "404";
		}
	}
	
	
	// Product Section ----------------
	
	@GetMapping("/admin/products")
	public String getProducts(Model model)
	{
		model.addAttribute("products",productService.getAllProduct());
		return "products";
	}
	
	@GetMapping("/admin/products/add")
	public String productAddGet(Model model)
	{
		model.addAttribute("productDTO", new ProductDTO());
		model.addAttribute("categories", categoryService.getAllCategory());
		return "productsAdd";
	}
	
	@PostMapping("/admin/products/add")
	public String productAddPost(@ModelAttribute("ProductDTO") ProductDTO productDTO,
								@RequestParam("productImage") MultipartFile file,
								@RequestParam("imgName") String imgName) throws IOException
	{
		Product product=new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
		product.setPrice(productDTO.getPrice());
		product.setWeight(productDTO.getWeight());
		product.setDescription(productDTO.getDescription());
		String imageUUID; 
		if(!file.isEmpty())
		{
			imageUUID=file.getOriginalFilename();
			System.out.println(uploadDir);
			Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
			Files.write(fileNameAndPath, file.getBytes());
		}
		else {
			imageUUID = imgName;
		}
		product.setImageName(imageUUID);
		productService.addProducts(product);
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/product/delete/{id}")
	public String productDelete(@PathVariable long id)
	{
		productService.removeProductById(id);
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/product/update/{id}")
	public String productUpdate(@PathVariable long id, Model model)
	{
		
		Optional<Product> productOptional = productService.getProductById(id);
		if(productOptional.isPresent())
		{
			Product product = productOptional.get();
			ProductDTO productDTO=new ProductDTO();
			productDTO.setId(product.getId());
			productDTO.setDescription(product.getDescription());
			productDTO.setCategoryId(product.getCategory().getId());
			productDTO.setImageName(product.getImageName());
			productDTO.setName(product.getName());
			productDTO.setPrice(product.getPrice());
			productDTO.setWeight(product.getWeight());
			model.addAttribute("productDTO", productDTO);
			 model.addAttribute("categories", categoryService.getAllCategory());
			return "productsAdd";		}
		else {
			return "404";
		}
	}

	
	

}
