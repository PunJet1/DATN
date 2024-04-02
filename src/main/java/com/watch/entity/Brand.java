package com.watch.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
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
public class Brand implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int brandId;
	@Column(columnDefinition = "NVARCHAR(255) NULL")
	private String name;
	@Column(columnDefinition = "NVARCHAR(300) NULL")
	private String image;
	private boolean status;
	
	@JsonIgnore
	@OneToMany(mappedBy = "brand")
	private List<Product> products;

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public Brand() {
		// TODO Auto-generated constructor stub
	}

	public Brand(int brandId, String name, String image, boolean status, List<Product> products) {
		super();
		this.brandId = brandId;
		this.name = name;
		this.image = image;
		this.status = status;
		this.products = products;
	}
	
	
}
