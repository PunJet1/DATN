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
public class ReportQuantityProduct {
	@Id
	private int productId;
	private String name;
	private double price;
	private long totaleQuantity;
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public long getTotaleQuantity() {
		return totaleQuantity;
	}
	public void setTotaleQuantity(long totaleQuantity) {
		this.totaleQuantity = totaleQuantity;
	}
	public ReportQuantityProduct() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ReportQuantityProduct(int productId, String name, double price, long totaleQuantity) {
		super();
		this.productId = productId;
		this.name = name;
		this.price = price;
		this.totaleQuantity = totaleQuantity;
	}
	
	
}