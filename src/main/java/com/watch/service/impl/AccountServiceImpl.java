package com.watch.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import com.watch.dao.AccountDao;
import com.watch.dto.UserDto;
import com.watch.entity.Accounts;
import com.watch.entity.Roles;
import com.watch.service.AccountService;
import com.watch.service.RolesService;
import javax.sql.DataSource;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	AccountDao accountDao;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	RolesService rolesService;

	@Autowired
	private DataSource dataSource;

	public AccountServiceImpl(AccountDao accountDao) {
		super();
		this.accountDao = accountDao;
	}

	@Override
	public Optional<Accounts> findByUsername(String username) {
		return accountDao.findByUsername(username);
	}

	@Override
	public Optional<Accounts> findByEmail(String email) {
		return accountDao.findByEmail(email);
	}

	@Override
	public Accounts save(Accounts accounts) {
		accountDao.save(accounts);
		return accounts;
	}

	@Override
	public Boolean existsByUsername(String username) {
		return findByUsername(username).isPresent();
	}

	@Override
	public Boolean existsByEmail(String email) {
		return findByEmail(email).isPresent();
	}

	@Override
	public Accounts register(UserDto userDto) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(userDto.getPassword());
		userDto.setPassword(encodedPassword);
		Accounts accounts = new Accounts();
		modelMapper.map(userDto, accounts);
		Roles roles = rolesService.findByName("ROLE_CUSTOMER");
		accounts.addRole(roles);
		accounts.setStatus(true);
		accounts.setCreate_date(new Date());
		return save(accounts);
	}

	public void updateResetPasswordToken(String token, String email) throws CustomerNotFoundException {
		Accounts accounts = accountDao.findUserByEmail(email);
		if (accounts != null) {
			accounts.setResetPasswordToken(token);
			accountDao.save(accounts);
		} else {
			throw new CustomerNotFoundException("Không tìm thấy email này trong hệ thống" + email);
		}
	}

	@Override
	public Accounts getByResetPasswordToken(String token) {
		return accountDao.findByResetPasswordToken(token);
	}

	@Override
	public void updatePassword(Accounts accounts, String newPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(newPassword);
		accounts.setPassword(encodedPassword);

		accounts.setResetPasswordToken(null);
		accountDao.save(accounts);
	}

	@Override
	public void loginFormOAuth2(OAuth2AuthenticationToken oauth2) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String name = oauth2.getPrincipal().getAttribute("name");
		String email = oauth2.getPrincipal().getAttribute("email");
		String password = Long.toHexString(System.currentTimeMillis());

		UserDetails user = User.withUsername(name).password(passwordEncoder.encode(password)).roles("CUSTOMER").build();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
	
//	public void loginFormOAuth2(OAuth2AuthenticationToken oauth2) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String name = oauth2.getPrincipal().getAttribute("name");
//        String email = oauth2.getPrincipal().getAttribute("email");
//        String password = Long.toHexString(System.currentTimeMillis());
//
//        // Lưu thông tin người dùng vào cơ sở dữ liệu
//        saveUser(name, email, passwordEncoder.encode(password));
//
//        UserDetails user = User.withUsername(name)
//                .password(passwordEncoder.encode(password))
//                .roles("CUSTOMER")
//                .build();
//        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(auth);
//    }

    private void saveUser(String name, String email, String encodedPassword) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, email);
                statement.setString(3, encodedPassword);
                statement.executeUpdate();
            }
        } catch (Exception e) {
            // Xử lý exception nếu cần
            e.printStackTrace();
        }
    }

	@Override
	public List<Accounts> findAll() {
		return accountDao.findAll();
	}

	@Override
	public void delete(Accounts entity) {
		accountDao.delete(entity);
	}

	@Override
	public Object findByUsername1(String username) {
		return accountDao.findByUsername1(username);
	}

	@Override
	public Accounts getById(Long id) {
		return accountDao.getById(id);
	}

	@Override
	public Accounts findUserByUsername(String username) {
		return accountDao.findUserByUsername(username);
	}

	@Override
	public List<Accounts> getAdminstrators() {
		return accountDao.getAdministrators();
	}

	@Override
	public List<Accounts> findByName1(String username) {
		// TODO Auto-generated method stub
		return accountDao.findByName1(username);
	}

	@Override
	public List<Accounts> findByName(String username, Boolean status) {
		// TODO Auto-generated method stub
		return accountDao.findByName(username, status);
	}

	@Override
	public List<Accounts> findByStatus(Boolean status) {
		// TODO Auto-generated method stub
		return accountDao.findByStatus(status);
	}

	@Override
	public List<Accounts> findAllCustomer() {
		return accountDao.findAllCustomer();
	}

	@Override
	public List<Accounts> findAllAccout() {
		return accountDao.findAllAccout();
	}

	@Override
	public int getTrungEmail(String email) {
		// TODO Auto-generated method stub
		return accountDao.getTrungEmail(email);
	}

	@Override
	public int getTrungPhone(String phone) {
		// TODO Auto-generated method stub
		return accountDao.getTrungPhone(phone);
	}

	@Override
	public List<Accounts> getAdminstratorsC() {
		// TODO Auto-generated method stub
		return accountDao.getAdminstratorsC();
	}

	@Override
	public List<Accounts> findByName1C(String string) {
		// TODO Auto-generated method stub
		return accountDao.findByName1C(string);
	}

	@Override
	public List<Accounts> findByNameC(String string, boolean in) {
		// TODO Auto-generated method stub
		return accountDao.findByNameC(string, in);
	}

	@Override
	public List<Accounts> findByStatusC(Boolean status) {
		// TODO Auto-generated method stub
		return accountDao.findByStatusC(status);
	}

}