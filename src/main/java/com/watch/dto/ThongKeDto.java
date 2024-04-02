package com.watch.dto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@SuppressWarnings("serial")
@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Entity
public class ThongKeDto implements Serializable{
	
	@Id
	private int id;
	private String name;
	private String brand;
	private String cate;
	private long quantity;
	private double total;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getCate() {
		return cate;
	}
	public void setCate(String cate) {
		this.cate = cate;
	}
	
	
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	public ThongKeDto(int id, String name, String brand, String cate, long quantity, double total) {
		super();
		this.id = id;
		this.name = name;
		this.brand = brand;
		this.cate = cate;
		this.quantity = quantity;
		this.total = total;
	}
	public ThongKeDto() {
		super();
		// TODO Auto-generated constructor stub
	}

}