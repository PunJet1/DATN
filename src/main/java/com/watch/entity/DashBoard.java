package com.watch.entity;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@SuppressWarnings("serial")
@Data
@Entity 
public class DashBoard implements Serializable{
	@Id
	private int date;
	private double sum;
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public double getSum() {
		return sum;
	}
	public void setSum(double sum) {
		this.sum = sum;
	}
	public DashBoard() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DashBoard(int date, double sum) {
		super();
		this.date = date;
		this.sum = sum;
	}
	
	
	
	
}