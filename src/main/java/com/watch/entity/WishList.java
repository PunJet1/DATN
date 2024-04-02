package com.watch.entity;

import java.io.Serializable;
import java.util.Date;

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
public class WishList implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int wishListId;
	private Date likeDate;
	
	@ManyToOne
	@JoinColumn(name = "accountId")
	private Accounts account;
	
	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;

	public int getWishListId() {
		return wishListId;
	}

	public void setWishListId(int wishListId) {
		this.wishListId = wishListId;
	}

	public Date getLikeDate() {
		return likeDate;
	}

	public void setLikeDate(Date likeDate) {
		this.likeDate = likeDate;
	}

	public Accounts getAccount() {
		return account;
	}

	public void setAccount(Accounts account) {
		this.account = account;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public WishList(int wishListId, Date likeDate, Accounts account, Product product) {
		super();
		this.wishListId = wishListId;
		this.likeDate = likeDate;
		this.account = account;
		this.product = product;
	}
	public WishList() {
		// TODO Auto-generated constructor stub
	}
	
		
}
