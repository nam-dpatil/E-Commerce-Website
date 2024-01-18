package com.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shop.modal.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findAllByCategory_id(int id);

}
