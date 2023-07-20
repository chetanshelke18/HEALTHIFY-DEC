package com.hms.api.serviceimpl;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hms.api.dao.AppointmentDao;
import com.hms.api.entity.Appointment;
import com.hms.api.service.AppointmentService;

/**
 * @author RAM
 *
 */
@Service()
public class AppointmentServiceImp implements AppointmentService {

	@Autowired
	private AppointmentDao appointmentDao;

	@Override
	public Appointment addAppointment(Appointment appointment) {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String id = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date());
		Date date = Date.valueOf(LocalDate.now());

		appointment.setAppointmentpatientid(id);
		appointment.setAppointmenttakentime(dtf.format(now));
		appointment.setAppointmenttakendate(date);

		return appointmentDao.addAppointment(appointment);
	}

	@Override
	public Appointment updateAppointment(Appointment appointment) {
		Appointment updatedAppointment = appointmentDao.updateAppointment(appointment);
		return updatedAppointment;
	}

	@Override
	public Appointment getAppointmentById(String appointmentId) {
		Appointment appointment = appointmentDao.getAppointmentById(appointmentId);
		return appointment;
	}

	@Override
	public List<Appointment> getAppointmentsByPatientsIds(List<String> patientsId) {
		List<Appointment> appointmentsByPatientsIds = appointmentDao.getAppointmentsByPatientsIds(patientsId);
		return appointmentsByPatientsIds;
	}

	@Override
	public List<Appointment> getAppointmentsByDoctorIdAndAppointmentDate(String doctorId, Date appointmentDate) {
		List<Appointment> appointments = appointmentDao.getAppointmentsByDoctorIdAndAppointmentDate(doctorId,
				appointmentDate);
		return appointments;
	}

	@Override
	public List<Appointment> getAppointmentsByDoctorIdAndAppointmentDate(String doctorId, Date appointmentDate,
			String appointmentTime) {
		List<Appointment> appointments = appointmentDao.getAppointmentsByDoctorIdAndAppointmentDate(doctorId,
				appointmentDate);
		return appointments;
	}

	@Override
	public List<Appointment> getAppointmentsByDate(Date date) {
		List<Appointment> appointmentsByDate = appointmentDao.getAppointmentsByDate(date);
		return appointmentsByDate;
	}

	@Override
	public Long getCountByAppointmentDate(Date appointmentDate) {
		Long countByAppointmentDate = appointmentDao.getCountByAppointmentDate(appointmentDate);
		return countByAppointmentDate;
	}

	@Override
	public List<Appointment> getAppointmentsByBillingDate(Date billingDate) {
		List<Appointment> appointmentsByBillingDate = appointmentDao.getAppointmentsByBillingDate(billingDate);
		return appointmentsByBillingDate;
	}

	@Override
	public Long getAppointmentsTotalCount() {
		Long count = appointmentDao.getAppointmentsTotalCount();
		return count;
	}

	@Override
	public Long getCountByAppointmentTakenDate(Date appointmentTakenDate) {
		Long count = appointmentDao.getCountByAppointmentTakenDate(appointmentTakenDate);
		return count;
	}

	@Override
	public Long getCountByTreatmentStatusAndBillingDate(String treatmentStatus, Date billingDate) {
		Long count = appointmentDao.getCountByTreatmentStatusAndBillingDate(treatmentStatus, billingDate);
		return count;
	}

	@Override
	public List<Appointment> getAllAppointments() {
		List<Appointment> allAppointments = appointmentDao.getAllAppointments();
		return allAppointments;
	}

	@Override
	public List<Appointment> getTop5AppointmentsByDate(Date date) {
		List<Appointment> top5AppointmentsByDate = appointmentDao.getTop5AppointmentsByDate(date);
		return top5AppointmentsByDate;
	}

}
