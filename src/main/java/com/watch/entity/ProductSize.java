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
public class ProductSize implements Serializable{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_size_id")
    int productSizeId;

    @ManyToOne
    @JoinColumn(name = "productid", nullable = false)
    Product product;

    @ManyToOne
    @JoinColumn(name = "sizeid", nullable = false)
    Size size;

    @Column(name = "price", nullable = false)
    double price;
    
    public ProductSize() {
    }

    public ProductSize(Product product, Size size, double price) {
        this.product = product;
        this.size = size;
        this.price = price;
    }

    // Getters and Setters
    public int getProductSizeId() {
        return productSizeId;
    }

    public void setProductSizeId(int productSizeId) {
        this.productSizeId = productSizeId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
