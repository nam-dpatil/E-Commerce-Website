package com.shop.modal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="category_id")
	private int id;
	
	private String name;

}
