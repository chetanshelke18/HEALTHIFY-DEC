package com.hms.api.serviceimpl;


import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hms.api.dao.UserDao;
import com.hms.api.entity.Role;
import com.hms.api.entity.User;
import com.hms.api.security.CustomUserDetail;
import com.hms.api.service.UserService;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	public BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserDao dao;

	@Override
	public boolean addUser(User user) {
		return false;
	}

	@Override
	public User loginUser(User user) {

		return dao.loginUser(user);
	}

	@Override
	public CustomUserDetail loadUserByUserId(String userId) {
		return dao.loadUserByUserId(userId);
	}

	@Override
	public boolean deleteUserById(String id) {
		return dao.deleteUserById(id);
	}

	@Override
	public User getUserById(String id) {
		return dao.getUserById(id);
	}

	@Override
	public List<User> getAllUsers() {
		return dao.getAllUsers();
	}

	@Override
	public User updateUser(User user) {
		String password = passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
//		Set<Role> roles = user.getRoles();
//		List<Role> list=new ArrayList<>(roles);
//		Set<Role> updatedRoles = new HashSet<>();
//		user.getRoles().clear();
//		for (Role role : list) {
//			Role userRole = getRoleById(role.getId());
//			updatedRoles.add(userRole);	
//		}
//		user.setRoles(updatedRoles);
		return dao.updateUser(user);
	}

	@Override
	public Long getUsersTotalCounts() {
		return dao.getUsersTotalCounts();
	}

	@Override
	public Long getUsersTotalCounts(String type) {
		return dao.getUsersTotalCounts(type);
	}

	@Override
	public Long getUserCountByDateAndType(Date registereddate, String type) {
		return dao.getUserCountByDateAndType(registereddate, type);
	}

	@Override
	public List<User> getUserByFirstName(String firstName) {
		return dao.getUserByFirstName(firstName);
	}

	@Override
	public Role addRole(Role role) {

		return dao.addRole(role);

	}

	@Override
	public Role getRoleById(int roleId) {

		return dao.getRoleById(roleId);
	}

	@Override
	public String generateReport() {
		return null;
	}
}