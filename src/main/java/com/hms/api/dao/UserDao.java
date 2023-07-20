package com.hms.api.dao;

import java.sql.Date;
import java.util.List;

import com.hms.api.entity.Otp;
import com.hms.api.entity.Role;
import com.hms.api.entity.User;
import com.hms.api.security.CustomUserDetail;

public interface UserDao {
	public CustomUserDetail loadUserByUserId(String userId);

	boolean addUser(User user);

	User loginUser(User user);

	boolean deleteUserById(String id);

	User getUserById(String id);

	List<User> getAllUsers();

	User updateUser(User user);

	Long getUsersTotalCounts();

	Long getUsersTotalCounts(String type);

	Long getUserCountByDateAndType(Date registeredDate, String type);

	List<User> getUserByFirstName(String firstName);

	Role addRole(Role role);

	public Role getRoleById(int roleId);

//////////////////////////////////////////
	public boolean saveOtp(Otp otp);

	public Otp getOtpByUser(String userId);
}
