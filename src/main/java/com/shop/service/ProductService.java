package com.shop.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.modal.Product;
import com.shop.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	public List<Product> getAllProduct()
	{
		return productRepository.findAll();
	}
	
	public void addProducts(Product product)
	{
		productRepository.save(product);
	}
	
	public void removeProductById(long id)
	{
		productRepository.deleteById(id);;
	}
	
	public Optional<Product> getProductById(long id)
	{
		return productRepository.findById(id);
	}
	
	public List<Product> getAllProductByCategoryId(int id)
	{
		return productRepository.findAllByCategory_id(id);
	}

}
