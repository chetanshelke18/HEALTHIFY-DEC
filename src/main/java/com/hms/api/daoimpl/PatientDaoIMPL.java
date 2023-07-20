package com.hms.api.daoimpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hms.api.dao.PatientDao;
import com.hms.api.entity.Patient;

/**
 * @author RAM
 *
 */
@Repository
@SuppressWarnings({ "deprecation", "unchecked" })
public class PatientDaoIMPL implements PatientDao {

	@Autowired
	private SessionFactory sf;

	@Override
	public List<Patient> findByFirstnameContainingIgnoreCase(String patientName) {

		Session session = null;
		List<Patient> list = new ArrayList<>();

		try {
			session = sf.getCurrentSession();
			Criteria criteria = session.createCriteria(Patient.class);
			criteria.add(Restrictions.or(Restrictions.like("firstName", "%" + patientName + "%"),
					Restrictions.eq("firstName", patientName)));
			list = criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Long getPatientsCount() {

		Session session = null;
		Long patientsCount = 0l;

		try {
			session = sf.getCurrentSession();
			Criteria criteria = session.createCriteria(Patient.class);
			criteria.setProjection(Projections.rowCount());
			List<Long> list = criteria.list();
			if (!list.isEmpty()) {
				patientsCount = list.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return patientsCount;
	}

	@Override
	public Long getPatientsCountByDate(Date registeredDate) {

		Session session = null;
		Long patientsCountByDate = 0l;

		try {
			session = sf.getCurrentSession();
			Criteria criteria = session.createCriteria(Patient.class);
			criteria.add(Restrictions.eq("registerDate", registeredDate));
			criteria.setProjection(Projections.rowCount());
			List<Long> list = criteria.list();

			if (!list.isEmpty()) {
				patientsCountByDate = list.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return patientsCountByDate;
	}

	@Override
	public List<Patient> getTop5PatientAddedByDate() {
		Session session = sf.getCurrentSession();
		List<Patient> list = null;
		try {
			Criteria criteria = session.createCriteria(Patient.class);
			criteria.addOrder(Order.desc("registerDate"));
			criteria.setMaxResults(5);
			list = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Patient addPatient(Patient patient) {

		Session session = null;
		boolean isAdded = false;
		try {

			session = sf.getCurrentSession();
			session.save(patient);
			isAdded = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isAdded) {
			return patient;
		} else {
			return null;
		}

	}

	@Override
	public boolean deletePatientById(String id) {

		Session session = null;
		boolean isDeleted = false;

		try {

			session = sf.getCurrentSession();
			Patient presentPatient = session.get(Patient.class, id);
			if (presentPatient != null) {
				session.delete(presentPatient);
				isDeleted = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return isDeleted;
	}

	@Override
	public Patient getPatientById(String id) {

		Session session = null;
		Patient patient = null;

		try {
			session = sf.getCurrentSession();
			patient = session.get(Patient.class, id);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return patient;
	}

	@Override
	public Patient updatePatient(Patient patient) {

		Session session = null;
		Patient presentPatient = null;

		try {
			session = sf.getCurrentSession();
			presentPatient = session.get(Patient.class, patient.getId());

			if (presentPatient != null) {
				session.evict(presentPatient);
				session.update(patient);
				return patient;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return presentPatient;
	}

	@Override
	public List<Patient> getAllPatients() {
		Session session = null;
		List<Patient> list = new ArrayList<>();

		try {
			session = sf.getCurrentSession();
			Criteria criteria = session.createCriteria(Patient.class);
			list = criteria.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<String> getAllPatientsIds() {
		Session session = sf.getCurrentSession();
		List<String> list = null;
		try {
			Query<String> query = session.createQuery("SELECT id FROM Patient");
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
