package com.watch.entity;

import java.io.Serializable;
import java.util.Date;

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
public class Feedback implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int feedbackId;	
	private int rate;
	@Column(columnDefinition = "NVARCHAR(255) NULL")
	private String comment;
	private Date feedbackDate;
	
	@ManyToOne
	@JoinColumn(name = "accountId")
	private Accounts account;
	
	@ManyToOne
	@JoinColumn(name = "productId")
	private Product product;

	public int getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(int feedbackId) {
		this.feedbackId = feedbackId;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
	public void setFeedbackDate(Date feedbackDate) {
		this.feedbackDate = feedbackDate;
	}
	
	public Date getFeedbackDate() {
		return feedbackDate;
	}
	
	
	public Feedback(int feedbackId, int rate, String comment, Date feedbackDate, Accounts account, Product product) {
		super();
		this.feedbackId = feedbackId;
		this.rate = rate;
		this.comment = comment;
		this.feedbackDate = feedbackDate;
		this.account = account;
		this.product = product;
	}

	public Feedback() {
		// TODO Auto-generated constructor stub
	}
	
	

}
