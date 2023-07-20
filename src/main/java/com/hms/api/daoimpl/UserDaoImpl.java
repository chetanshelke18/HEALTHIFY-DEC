package com.hms.api.daoimpl;

import java.sql.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import com.hms.api.dao.UserDao;
import com.hms.api.entity.Otp;
import com.hms.api.entity.Role;
import com.hms.api.entity.User;
import com.hms.api.security.CustomUserDetail;

@Repository
public class UserDaoImpl implements UserDao {
	private static Logger LOG = LogManager.getLogger(UserDaoImpl.class);

	@Autowired
	private SessionFactory sf;

	@Autowired
	public PasswordEncoder passwordEncoder;

	@Override
	public boolean addUser(User user) {
		Session session = sf.getCurrentSession();
		boolean isAdded = false;
		System.out.println("dao add user>> " + user);
		try {
			User usr = session.get(User.class, user.getUsername());
			if (usr == null) {
				session.save(user);
				isAdded = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isAdded;
	}

	@Override
	public User loginUser(User user) {
		Session session = sf.getCurrentSession();
		User usr = null;
		try {
			usr = session.get(User.class, user.getUsername());
			boolean matches = passwordEncoder.matches(user.getPassword(), usr.getPassword());
			if (matches) {
				return usr;
			} else {
				usr = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usr;

	}

	@Override
	public CustomUserDetail loadUserByUserId(String userId) {
		Session session = sf.getCurrentSession();
		CustomUserDetail user = new CustomUserDetail();
		User usr = null;
		try {
			usr = session.get(User.class, userId);
			if (usr != null) {
				user.setUserid(usr.getUsername());
				user.setPassword(usr.getPassword());
				user.setRoles(usr.getRoles());
			}
			System.out.println("dao ..." + user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;

	}

	@Override
	public boolean deleteUserById(String id) {
		Session session = sf.getCurrentSession();
		boolean isDeleted = false;
		try {
			User user = getUserById(id);
			if (user != null) {
				session.delete(user);
				isDeleted = true;
			}
		} catch (Exception e) {
			LOG.info(e.getMessage());
			e.printStackTrace();
		}
		return isDeleted;
	}

	@Override
	public User getUserById(String id) {
		Session session = sf.getCurrentSession();
		User user = null;
		try {
			user = session.get(User.class, id);
		} catch (Exception e) {
			LOG.info(e.getMessage());

		}
		return user;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<User> getAllUsers() {
		Session session = sf.getCurrentSession();
		List<User> list = null;
		try {
			list = session.createCriteria(User.class).list();
		} catch (Exception e) {
			LOG.info(e.getMessage());
		}
		return list;
	}

	@Override
	public User updateUser(User user) {
		Session session = sf.getCurrentSession();
		User dbuser = null;
		boolean isUpdated = false;
		try {
			dbuser = getUserById(user.getUsername());
			if (dbuser != null) {
				session.evict(dbuser);
				session.update(user);
				isUpdated = true;
			}
			if (isUpdated == false) {
				user = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	@Override
	public Long getUsersTotalCounts() {
		long count = 0;
		try {
			Session session = sf.getCurrentSession();
			@SuppressWarnings("deprecation")
			Criteria criteria = session.createCriteria(User.class);
			criteria.setProjection(Projections.rowCount());
			List list = criteria.list();
			if (!list.isEmpty()) {
				count = (long) list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}

	@Override
	public Long getUsersTotalCounts(String type) {
		long count = 0;
		try {
			Session session = sf.getCurrentSession();
			@SuppressWarnings("deprecation")
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("type", type));
			criteria.setProjection(Projections.rowCount());
			List list = criteria.list();
			if (!list.isEmpty()) {
				count = (long) list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public Long getUserCountByDateAndType(Date registeredDate, String type) {
		long count = 0;
		try {
			Session session = sf.getCurrentSession();
			@SuppressWarnings("deprecation")
			Criteria criteria = session.createCriteria(User.class);
			SimpleExpression date = Restrictions.eq("createdDate", registeredDate);
			SimpleExpression userType = Restrictions.eq("type", type);
			criteria.add(Restrictions.and(date, userType));
			criteria.setProjection(Projections.rowCount());
			List list = criteria.list();
			if (!list.isEmpty()) {
				count = (long) list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<User> getUserByFirstName(String firstName) {
		List<User> list=null;
		try {
			Session session = sf.getCurrentSession();
			@SuppressWarnings("deprecation")
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("firstname", firstName));
			list = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean saveOtp(Otp otp) {
		Session session = sf.getCurrentSession();
		boolean isAdded = false;
		try {
			session.saveOrUpdate(otp);
			isAdded = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isAdded;
	}

	@Override
	public Otp getOtpByUser(String userId) {
		Session session = sf.getCurrentSession();
		Otp otp = null;
		try {
			otp = session.get(Otp.class, userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return otp;
	}

	@Override
	public Role addRole(Role role) {
		Session session = sf.getCurrentSession();
		boolean isAdded = false;
		try {
			session.saveOrUpdate(role);
			isAdded = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isAdded) {
			return role;
		} else {
			return null;
		}
	}

	@Override
	public Role getRoleById(int roleId) {
		Session session = sf.getCurrentSession();
		Role role = null;
		try {
			role = session.get(Role.class, roleId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return role;
	}

}
