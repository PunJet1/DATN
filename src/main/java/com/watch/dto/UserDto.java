package com.watch.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotBlank(message = "Vui lòng điền tài khoản")
    private String username;
    @NotBlank(message = "Vui lòng điền mật khẩu")
    @Size(min = 6,message = "Vui lòng điền từ 6 ký tự trở lên")
    private String password;
    @NotBlank(message = "Vui lòng điền lại mật khẩu")
    private String rpassword;
    @NotBlank(message = "Vui lòng điền email")
    @Email(message = "Vui lòng điền đúng định dạng email")
    private String email;
    
    
    @NotBlank(message = "Vui lòng điền họ và tên")
    private String fullname;
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRpassword() {
		return rpassword;
	}
	public void setRpassword(String rpassword) {
		this.rpassword = rpassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
    
    

}