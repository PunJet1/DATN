package com.watch.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Entity 
public class Roles implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer roleId;

    @Column(length = 20)
    private String name;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Roles(Integer roleId, String name) {
		super();
		this.roleId = roleId;
		this.name = name;
	}

	public Roles() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    

}