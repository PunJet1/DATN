package com.watch.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity 
public class ImageProduct implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int imageId;
	@Column(columnDefinition = "NVARCHAR(200) Not NULL")
	String images;
	
	@ManyToOne
	@JoinColumn(name = "productId")
	Product product;

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ImageProduct(int imageId, String images, Product product) {
		super();
		this.imageId = imageId;
		this.images = images;
		this.product = product;
	}
	
	public ImageProduct() {
		// TODO Auto-generated constructor stub
	}
	
	
}
