package com.watch.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity 
public class Category implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int categoryId;
	@Column(columnDefinition = "NVARCHAR(255) NULL")
	private String name;
	private boolean status;
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "category")
	private List<Product> products;
	
	@ManyToOne
	@JoinColumn(name = "discountId")
	private Discount discount;

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}

	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", name=" + name + ", products=" + products + ", discount="
				+ discount + "]";
	}
	
	public Category() {
		// TODO Auto-generated constructor stub
	}

	public Category(int categoryId, String name, List<Product> products, Discount discount) {
		super();
		this.categoryId = categoryId;
		this.name = name;
		this.products = products;
		this.discount = discount;
	}
	
	
	
}
