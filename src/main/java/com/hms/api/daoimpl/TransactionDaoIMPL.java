package com.hms.api.daoimpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hms.api.dao.TransactionDao;
import com.hms.api.entity.TransactionDetails;

@Repository
public class TransactionDaoIMPL implements TransactionDao {

	@Autowired
	private SessionFactory sf;

	@Override
	public int generateSalaryForUser(TransactionDetails transactionDetails) {
		Session session = sf.getCurrentSession();
		TransactionDetails dbtraTransactionDetails = getTransactionDetails(transactionDetails.getUsername(),
				transactionDetails.getMonth());
		int status = 0;
		try {
			if (dbtraTransactionDetails == null) {
				session.save(transactionDetails);
				status = 1;
			} else {
				session.update(transactionDetails);
				status = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public TransactionDetails getTransactionDetails(String transactionId) {
		Session session = sf.getCurrentSession();
		TransactionDetails transactionDetails = null;
		try {
			transactionDetails = session.get(TransactionDetails.class, transactionId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return transactionDetails;

	}

	@Override
	public TransactionDetails getTransactionDetails(String username, int month) {
		Session session = sf.getCurrentSession();
		List<TransactionDetails> list = null;
		TransactionDetails transactionDetails = null;
		try {

			Criteria criteria = session.createCriteria(TransactionDetails.class);
			SimpleExpression EQusername = Restrictions.eq("username", username);
			Criterion EQmonth = Restrictions.eq("month", month);

			criteria.add(Restrictions.and(EQusername, EQmonth));
			list = criteria.list();
			if (!list.isEmpty()) {
				transactionDetails = list.get(0);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return transactionDetails;
	}

	@Override
	public List<TransactionDetails> getTransactionDetails(String username, int from, int to) {
		List<TransactionDetails> list = null;
		Session session = sf.getCurrentSession();
		try {

			Criteria criteria = session.createCriteria(TransactionDetails.class);
			SimpleExpression EQusername = Restrictions.eq("username", username);
			Criterion between = Restrictions.between("month", from, to);

			criteria.add(Restrictions.and(EQusername, between));
			list = criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
