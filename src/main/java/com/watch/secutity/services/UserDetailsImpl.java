package com.watch.secutity.services;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.watch.entity.Accounts;


public class UserDetailsImpl implements UserDetails {
	 private static final long serialVersionUID = 1L;
	  public static UserDetailsImpl User;
	    private Long accountId;
	    private String username;
	    @JsonIgnore
	    private String password;
	    private String fullname;
	    private String email;
	    private String phone;
	    private String address;
	    private boolean status;
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

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public Date getBirthdate() {
			return birthdate;
		}

		public void setBirthdate(Date birthdate) {
			this.birthdate = birthdate;
		}

		public void setAccountId(Long accountId) {
			this.accountId = accountId;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public void setFullname(String fullname) {
			this.fullname = fullname;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public void setAccount(Accounts account) {
			this.account = account;
		}

		public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
			this.authorities = authorities;
		}
		
		public boolean isStatus() {
			return status;
		}

		public void setStatus(boolean status) {
			this.status = status;
		}



		private String image;
	    private Date birthdate;
	    private Accounts account;

	    private Collection<? extends GrantedAuthority> authorities;

	    public UserDetailsImpl() {
	    }

	    public UserDetailsImpl(Long accountId, String username, String password, String fullname, String email, String phone, String address,
	                           String image, Date birthdate, Collection<? extends GrantedAuthority> authorities,boolean status) {
	        this.accountId = accountId;
	        this.username = username;
	        this.password = password;
	        this.fullname = fullname;
	        this.email = email;
	        this.phone = phone;
	        this.address = address;
	        this.image = image;
	        this.birthdate = birthdate;
	        this.authorities = authorities;
	        this.status = status;
	    }

	    public UserDetailsImpl(Accounts account) {
	        this.account = account;
	    }
	    

	    public static UserDetailsImpl build(Accounts account) {
	        List<GrantedAuthority> authorities = account.getRoles().stream()
	                .map(role -> new SimpleGrantedAuthority(role.getName()))
	                .collect(Collectors.toList());
	        	User = new UserDetailsImpl(
		                account.getAccountId(),
		                account.getUsername(),
		                account.getPassword(),
		                account.getFullname(),
		                account.getEmail(),
		                account.getPhone(),
		                account.getAddress(),
		                account.getImage(),
		                account.getBirthdate(),
		                authorities,
		                account.isStatus());
	        return User;
	    }

	    public Long getAccountId() {
	        return accountId;
	    }

	    public String getFullname() {
	        return fullname;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public Accounts getAccount() {
	        return account;
	    }

	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return authorities;
	    }

	    @Override
	    public String getPassword() {
	        return password;
	    }

	    @Override
	    public String getUsername() {
	        return username;
	    }

	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isEnabled() {
	        return true;
	    }
}