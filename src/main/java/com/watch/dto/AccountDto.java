package com.watch.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
// @AllArgsConstructor
// @NoArgsConstructor
public class AccountDto {
	private int accountId;
	private String userName;
	private String image;
	private String passwords;
	private String fullname;
	private String email;
	private String phone;
	private String address;
	private Date birthdate;
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getPasswords() {
		return passwords;
	}
	public void setPasswords(String passwords) {
		this.passwords = passwords;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public AccountDto(int accountId, String userName, String image, String passwords, String fullname, String email,
			String phone, String address, Date birthdate) {
		super();
		this.accountId = accountId;
		this.userName = userName;
		this.image = image;
		this.passwords = passwords;
		this.fullname = fullname;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.birthdate = birthdate;
	}
	public AccountDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}