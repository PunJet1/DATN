package com.watch.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity
public class Vouchers implements Serializable{
	
	@Id
	@Column(length = 50, nullable = false,columnDefinition = "NVARCHAR(30)")
	private String voucherName;
	private double valued;
	private int quantity;
	private double conditions;
	private boolean statustt;
	@Column(columnDefinition = "NVARCHAR(255) NULL")
	private String note;
	
	private Date ngayBatDau;
	
	private Date ngayKetThuc;
	
	
	@OneToMany(mappedBy = "voucher")
	@JsonIgnore
	private List<Orders> orders;

	
	
	public Date getNgayBatDau() {
		return ngayBatDau;
	}

	public void setNgayBatDau(Date ngayBatDau) {
		this.ngayBatDau = ngayBatDau;
	}

	public Date getNgayKetThuc() {
		return ngayKetThuc;
	}

	public void setNgayKetThuc(Date ngayKetThuc) {
		this.ngayKetThuc = ngayKetThuc;
	}

	public String getVoucherName() {
		return voucherName;
	}

	public void setVoucherName(String voucherName) {
		this.voucherName = voucherName;
	}

	public double getValued() {
		return valued;
	}

	public void setValued(double valued) {
		this.valued = valued;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getConditions() {
		return conditions;
	}

	public void setConditions(double conditions) {
		this.conditions = conditions;
	}

	public boolean isStatustt() {
		return statustt;
	}

	public void setStatustt(boolean statustt) {
		this.statustt = statustt;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<Orders> getOrders() {
		return orders;
	}

	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}

	
	public Vouchers(String voucherName, double valued, int quantity, double conditions, boolean statustt, String note,
			Date ngayBatDau, Date ngayKetThuc, List<Orders> orders) {
		super();
		this.voucherName = voucherName;
		this.valued = valued;
		this.quantity = quantity;
		this.conditions = conditions;
		this.statustt = statustt;
		this.note = note;
		this.ngayBatDau = ngayBatDau;
		this.ngayKetThuc = ngayKetThuc;
		this.orders = orders;
	}

	public Vouchers() {
		// TODO Auto-generated constructor stub
	}
	
	
}
