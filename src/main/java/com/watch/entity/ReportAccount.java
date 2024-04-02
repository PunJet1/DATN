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
public class ReportAccount {
	@Id
	private long accountId;
	private String fullname;
	private String image;
	private long totalOrder;
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public long getTotalOrder() {
		return totalOrder;
	}
	public void setTotalOrder(long totalOrder) {
		this.totalOrder = totalOrder;
	}
	public ReportAccount(long accountId, String fullname, String image, long totalOrder) {
		super();
		this.accountId = accountId;
		this.fullname = fullname;
		this.image = image;
		this.totalOrder = totalOrder;
	}
	public ReportAccount() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}