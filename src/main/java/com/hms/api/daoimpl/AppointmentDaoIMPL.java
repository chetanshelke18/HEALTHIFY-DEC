package com.hms.api.daoimpl;

import java.sql.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.hms.api.dao.AppointmentDao;
import com.hms.api.entity.Appointment;

/**
 * @author RAM
 *
 */
@Repository
public class AppointmentDaoIMPL implements AppointmentDao {

	@Autowired
	private SessionFactory sf;

	@Override
	public Appointment addAppointment(Appointment appointment) {
		Session session = sf.getCurrentSession();
		boolean isSaved = false;
		try {
			session.save(appointment);
			isSaved = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isSaved) {
			return appointment;
		} else {
			return null;
		}
	}

	@Override
	public Appointment updateAppointment(Appointment appointment) {
		Session session = sf.getCurrentSession();
		boolean isUpdated = false;
		try {
			Appointment dbAppointment = getAppointmentById(appointment.getAppointmentpatientid());
			if (dbAppointment != null) {
				session.update(appointment);
				isUpdated = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isUpdated) {
			return appointment;
		} else {
			return null;
		}
	}

	@Override
	public Appointment getAppointmentById(String patientId) {
		Session session = sf.getCurrentSession();
		Appointment appointment = null;
		try {
			appointment = session.get(Appointment.class, patientId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appointment;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Appointment> getAppointmentsByPatientsIds(List<String> patientsId) {
		Session session = sf.getCurrentSession();
		List<Appointment> list=null;
		try {
			 @SuppressWarnings("deprecation")
			Criteria criteria = session.createCriteria(Appointment.class);
			 criteria.add(Restrictions.in("appointmentpatientid", patientsId));
			list= criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Appointment> getAppointmentsByDoctorIdAndAppointmentDate(String doctorId, Date appointmentDate) {
		Session session = sf.getCurrentSession();
		List<Appointment> list=null;
		try {
			 @SuppressWarnings("deprecation")
			Criteria criteria = session.createCriteria(Appointment.class);
			SimpleExpression doctorIdExpression = Restrictions.eq("doctorid", doctorId);
			SimpleExpression dateExpression = Restrictions.eq("appointmentdate", appointmentDate);
			criteria.add(Restrictions.and(doctorIdExpression,dateExpression));
			list= criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Appointment> getAppointmentsByDoctorIdAndAppointmentDate(String doctorId, Date appointmentDate,
			String appointmentTime) {
		Session session = sf.getCurrentSession();
		List<Appointment> list=null;
		try {
			 @SuppressWarnings("deprecation")
			Criteria criteria = session.createCriteria(Appointment.class);
			SimpleExpression doctorIdExpression = Restrictions.eq("doctorid", doctorId);
			SimpleExpression dateExpression = Restrictions.eq("appointmentdate", appointmentDate);
			SimpleExpression timeExpression = Restrictions.eq("appointmenttime", appointmentTime);
			criteria.add(Restrictions.and(doctorIdExpression,dateExpression,timeExpression));
			list= criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Appointment> getAppointmentsByDate(Date date) {
		Session session = sf.getCurrentSession();
		List<Appointment> list=null;
		try {
			 @SuppressWarnings("deprecation")
			Criteria criteria = session.createCriteria(Appointment.class);
			criteria.add(Restrictions.eq("appointmentdate",date));
			list= criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Long getCountByAppointmentDate(Date appointmentDate) {
		Session session = sf.getCurrentSession();
		Long count=0L;
		try {
			 @SuppressWarnings("deprecation")
			Criteria criteria = session.createCriteria(Appointment.class);
			criteria.add(Restrictions.eq("appointmentdate",appointmentDate));
			criteria.setProjection(Projections.rowCount());
			@SuppressWarnings("unchecked")
			List<Long> countList= criteria.list();
			if(!countList.isEmpty()) {
				count=countList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Appointment> getAppointmentsByBillingDate(Date billingDate) {
		Session session = sf.getCurrentSession();
		List<Appointment> list=null;
		try {
			 @SuppressWarnings("deprecation")
			Criteria criteria = session.createCriteria(Appointment.class); 
			criteria.add(Restrictions.eq("billingDate",billingDate));
			list= criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Long getAppointmentsTotalCount() {
		Session session = sf.getCurrentSession();
		Long count=0L;
		try {
			 @SuppressWarnings("deprecation")
			Criteria criteria = session.createCriteria(Appointment.class);
			criteria.setProjection(Projections.rowCount());
			@SuppressWarnings("unchecked")
			List<Long> countList= criteria.list();
			if(!countList.isEmpty()) {
				count=countList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public Long getCountByAppointmentTakenDate(Date appointmentTakenDate) {
		Session session = sf.getCurrentSession();
		Long count=0L;
		try {
			 @SuppressWarnings("deprecation")
			Criteria criteria = session.createCriteria(Appointment.class);
			criteria.add(Restrictions.eq("appointmenttakendate",appointmentTakenDate));
			criteria.setProjection(Projections.rowCount());
			@SuppressWarnings("unchecked")
			List<Long> countList= criteria.list();
			if(!countList.isEmpty()) {
				count=countList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public Long getCountByTreatmentStatusAndBillingDate(String treatmentStatus, Date billingDate) {
		Session session = sf.getCurrentSession();
		Long count=0L;
		try {
			 @SuppressWarnings("deprecation")
			Criteria criteria = session.createCriteria(Appointment.class);
			 SimpleExpression treatmentStatusExpression = Restrictions.eq("treatmentstatus",treatmentStatus);
			 SimpleExpression billingDateExpression = Restrictions.eq("billingDate",billingDate);
			 criteria.add(Restrictions.and(treatmentStatusExpression,billingDateExpression));
			 criteria.setProjection(Projections.rowCount());
			@SuppressWarnings("unchecked")
			List<Long> countList= criteria.list();
			if(!countList.isEmpty()) {
				count=countList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Appointment> getAllAppointments() {
		Session session = sf.getCurrentSession();
		List<Appointment> list=null;
		try {
			 @SuppressWarnings("deprecation")
			Criteria criteria = session.createCriteria(Appointment.class);
			list= criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Appointment> getTop5AppointmentsByDate(Date date) {
		Session session = sf.getCurrentSession();
		List<Appointment> list = null;
		try {
			@SuppressWarnings("deprecation")
			Criteria criteria = session.createCriteria(Appointment.class);
			criteria.addOrder(Order.desc("appointmentdate"));
			criteria.setMaxResults(5);
			list = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
