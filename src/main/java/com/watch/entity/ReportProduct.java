package com.watch.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
//@NoArgsConstructor
//@AllArgsConstructor
public class ReportProduct {
	@Id
	private int productId;
	private String name;
	private String image;
	private double price;
	private long totaleProduct;
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public long getTotaleProduct() {
		return totaleProduct;
	}
	public void setTotaleProduct(long totaleProduct) {
		this.totaleProduct = totaleProduct;
	}
	public ReportProduct() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ReportProduct(int productId, String name, String image, double price, long totaleProduct) {
		super();
		this.productId = productId;
		this.name = name;
		this.image = image;
		this.price = price;
		this.totaleProduct = totaleProduct;
	}
	
	
}