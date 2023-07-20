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

	@Value("${user.roles}")
	private String[] roles;

	@Override
	public boolean addUser(User user) {
		Date date = Date.valueOf(LocalDate.now());
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setCreatedDate(date);
		user.setPassword(encodedPassword);
		boolean isAdded = dao.addUser(user);

		return isAdded;
	}

	@Override
	public User loginUser(User user) {

		return dao.loginUser(user);
	}

	@Override
	public CustomUserDetail loadUserByUserId(String userId) {
		System.out.println("service..." + userId);
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

		boolean contains = Arrays.stream(roles).anyMatch(role.getName()::equals);
		if (contains) {
			return dao.addRole(role);
		} else {
			return null;
		}

	}

	@Override
	public Role getRoleById(int roleId) {

		return dao.getRoleById(roleId);
	}

	@Override
	public String generateReport() {
		List<User> allUsers = dao.getAllUsers();
		try {
			// Fetching the .jrxml file from the resources folder.
	        final InputStream stream = this.getClass().getResourceAsStream("/User.jrxml");
	 
	        // Compile the Jasper report from .jrxml to .japser
	        final JasperReport report = JasperCompileManager.compileReport(stream);
	 
	        // Fetching the employees from the data source.
	        final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(allUsers);
	        
	        // Filling the report with the employee data and additional parameters information.
	        final JasperPrint print = JasperFillManager.fillReport(report, null, source);
	        
	        //final String filePath = "C:\\Users\\RAM\\Downloads\\";
	        String filePath = System.getProperty("user.home");
	        // Export the report to a PDF file.
	        JasperExportManager.exportReportToPdfFile(print, filePath + "/Downloads/User.pdf");
	 
	 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "generated";
	}

}
