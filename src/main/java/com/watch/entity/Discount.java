package com.watch.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


@SuppressWarnings("serial")
@Data
@Entity 
public class Discount implements Serializable{

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int discountId;
	private int percentDiscount;
	private boolean status;
	
	@JsonIgnore
	@OneToMany(mappedBy = "discount")
	List<Category> categories;

	public int getDiscountId() {
		return discountId;
	}

	public void setDiscountId(int discountId) {
		this.discountId = discountId;
	}

	public int getPercentDiscount() {
		return percentDiscount;
	}

	public void setPercentDiscount(int percentDiscount) {
		this.percentDiscount = percentDiscount;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public Discount(int discountId, int percentDiscount, boolean status, List<Category> categories) {
		super();
		this.discountId = discountId;
		this.percentDiscount = percentDiscount;
		this.status = status;
		this.categories = categories;
	}
	
	public Discount() {
		// TODO Auto-generated constructor stub
	}
	
}
