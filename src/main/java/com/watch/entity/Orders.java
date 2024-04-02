package com.watch.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
public class Orders implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;
	private Date createDate = new Date();
	@Column(columnDefinition = "NVARCHAR(200) Not NULL")
	private String address;
	@Column(columnDefinition = "NVARCHAR(50) Not NULL")
	private int status;
	private double total;
	@Column(columnDefinition="int default 0")
	private int tthaiThanhToan;
	
	@Column(columnDefinition = "NVARCHAR(200)  NULL")
	private String tenNn;
	
	@Column(columnDefinition = "NVARCHAR(15)  NULL")
	private String sdtNn;

	
	@ManyToOne
	@JoinColumn(name = "accountId")
	private Accounts account;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "voucherName")
	private Vouchers voucher;
	
	@JsonIgnore
	@OneToMany(mappedBy = "order")
	private List<OrderDetail> orderDetails;
	



	public int getTthaiThanhToan() {
		return tthaiThanhToan;
	}

	public void setTthaiThanhToan(int tthaiThanhToan) {
		this.tthaiThanhToan = tthaiThanhToan;
	}

	public String getTenNn() {
		return tenNn;
	}

	public void setTenNn(String tenNn) {
		this.tenNn = tenNn;
	}

	public String getSdtNn() {
		return sdtNn;
	}

	public void setSdtNn(String sdtNn) {
		this.sdtNn = sdtNn;
	}
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	public int getStatus() {
		return status;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Accounts getAccount() {
		return account;
	}

	public void setAccount(Accounts account) {
		this.account = account;
	}

	public Vouchers getVoucher() {
		return voucher;
	}

	public void setVoucher(Vouchers voucher) {
		this.voucher = voucher;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}


	
	public Orders(int orderId, Date createDate, String address, int status, double total, int tthaiThanhToan,
			String tenNn, String sdtNn, Accounts account, Vouchers voucher, List<OrderDetail> orderDetails) {
		super();
		this.orderId = orderId;
		this.createDate = createDate;
		this.address = address;
		this.status = status;
		this.total = total;
		this.tthaiThanhToan = tthaiThanhToan;
		this.tenNn = tenNn;
		this.sdtNn = sdtNn;
		this.account = account;
		this.voucher = voucher;
		this.orderDetails = orderDetails;
	}

	public Orders() {
		// TODO Auto-generated constructor stub
	}
	
	
	
}
