package com.hms.api.controller;

import java.sql.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.hms.api.entity.Appointment;
import com.hms.api.exception.ResourceNotFoundException;
import com.hms.api.exception.SomethingWentWrongException;
import com.hms.api.service.AppointmentService;


/**
 * @author RAM
 *
 */
@RestController
@RequestMapping(value = "/appointment")
public class AppointmentController {

	@Autowired
	private AppointmentService service;

	private static Logger LOG = LogManager.getLogger(AppointmentController.class);

	@PostMapping(value = "/add-appointment")
	public ResponseEntity<Appointment> addAppointment(@RequestBody Appointment appointment) {
		Appointment addedAppointment = service.addAppointment(appointment);
		if (addedAppointment != null) {
			LOG.info("Appointment Added !");
			return new ResponseEntity<Appointment>(addedAppointment, HttpStatus.OK);
		} else {
			throw new SomethingWentWrongException("Something Went Wrong While Adding Appointment !");
		}
	}

	@PutMapping(value = "/update-appointment")
	public ResponseEntity<Appointment> updateAppointment(@RequestBody Appointment appointment) {
		Appointment updatedAppointment = service.updateAppointment(appointment);
		if (updatedAppointment != null) {
			LOG.info("Appointment Updated !");
			return new ResponseEntity<Appointment>(updatedAppointment, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException(
					"Something Went Wrong While Updating Appointment ! Appoint Not Found To Update");
		}
	}

	@GetMapping(value = "/get-appointment-by-id/{id}")
	public ResponseEntity<Appointment> getAppointmentById(@PathVariable String id) {
		Appointment appointment = service.getAppointmentById(id);
		if (appointment != null) {
			LOG.info("Appointment Fetched !");
			return new ResponseEntity<Appointment>(appointment, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("Appointment Not Exists With ID :" + id);
		}
	}

	@GetMapping(value = "/get-appointment-by-ids/{ids}")
	public ResponseEntity<List<Appointment>> getAppointmentsByPatientsIds(@PathVariable List<String> ids) {
		List<Appointment> appointments = service.getAppointmentsByPatientsIds(ids);
		if (appointments != null) {
			LOG.info("Appointment Fetched !");
			return new ResponseEntity<List<Appointment>>(appointments, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("Appointment Not Exists With ID :" + ids);
		}
	}

	@GetMapping(value = "/get-appointment-by-drid-apointmentdate/{drid}/{date}")
	public ResponseEntity<List<Appointment>> getAppointmentsByDoctorIdAndAppointmentDate(@PathVariable String drid,
			@PathVariable Date date) {
		List<Appointment> appointments = service.getAppointmentsByDoctorIdAndAppointmentDate(drid, date);
		if (appointments != null) {
			LOG.info("Appointment Fetched !");
			return new ResponseEntity<List<Appointment>>(appointments, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("Appointment Not Exists");
		}
	}

	@GetMapping(value = "/get-appointment-by-drid-and-apointmentdate-and-time")
	public ResponseEntity<List<Appointment>> getAppointmentsByDoctorIdAndAppointmentDate(@RequestParam String doctorId,
			@RequestParam Date appointmentDate, @RequestParam String appointmentTime) {
		List<Appointment> appointments = service.getAppointmentsByDoctorIdAndAppointmentDate(doctorId, appointmentDate,
				appointmentTime);
		if (appointments != null) {
			LOG.info("Appointment Fetched !");
			return new ResponseEntity<List<Appointment>>(appointments, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("Appointment Not Exists");
		}
	}

	@GetMapping(value = "/get-appointment-by-apointmentdate")
	public ResponseEntity<List<Appointment>> getAppointmentsByDate(@RequestParam Date appointmentDate) {
		List<Appointment> appointments = service.getAppointmentsByDate(appointmentDate);
		if (appointments != null) {
			LOG.info("Appointment Fetched !");
			return new ResponseEntity<List<Appointment>>(appointments, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("Appointment Not Exists");
		}
	}

	@GetMapping(value = "/get-count-by-appointment-date")
	public ResponseEntity<Long> getCountByAppointmentDate(@RequestParam Date date) {
		Long count = service.getCountByAppointmentDate(date);
		if (count > 0) {
			LOG.info("Appointment Count By Date is " + count);
			return new ResponseEntity<Long>(count, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("Appointment Not Exists");
		}
	}
	
	@GetMapping(value = "/get-appointment-by-billingdate")
	public ResponseEntity<List<Appointment>> getAppointmentsByBillingDate(@RequestParam Date billingDate) {
		List<Appointment> appointments = service.getAppointmentsByBillingDate(billingDate);
		if (appointments != null) {
			LOG.info("Appointment Fetched !");
			return new ResponseEntity<List<Appointment>>(appointments, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("Appointment Not Exists");
		}
	}
	
	@GetMapping(value = "/get-count-of-appointments")
	public ResponseEntity<Long> getAppointmentsTotalCount() {
		Long count = service.getAppointmentsTotalCount();
		if (count > 0) {
			LOG.info("Appointment Count By Date is " + count);
			return new ResponseEntity<Long>(count, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("Appointment Not Exists");
		}
	}
	
	@GetMapping(value = "/get-count-by-appointmenttaken-date")
	public ResponseEntity<Long> getCountByAppointmentTakenDate(@RequestParam Date appointmentTakenDate) {
		Long count = service.getCountByAppointmentTakenDate(appointmentTakenDate);
		if (count > 0) {
			LOG.info("Appointment Count By Date is " + count);
			return new ResponseEntity<Long>(count, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("Appointment Not Exists");
		}
	}
	
	@GetMapping(value = "/get-count-by-treatmentstatus-and billingdate")
	public ResponseEntity<Long> getCountByTreatmentStatusAndBillingDate(@RequestParam String treatmentStatus, @RequestParam Date billingDate) {
		Long count = service.getCountByTreatmentStatusAndBillingDate(treatmentStatus, billingDate);
		if (count > 0) {
			LOG.info("Appointment Count By Date is " + count);
			return new ResponseEntity<Long>(count, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("Appointment Not Exists");
		}
	}
	
	@GetMapping(value = "/get-all-appointments")
	public ResponseEntity<List<Appointment>> getAllAppointments() {
		List<Appointment> appointments = service.getAllAppointments();
		if (appointments != null) {
			LOG.info("Appointment Fetched !");
			return new ResponseEntity<List<Appointment>>(appointments, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("Appointment Not Exists");
		}
	}


	@GetMapping(value = "/get-top5-appointments")
	public ResponseEntity<List<Appointment>> getTop5AppointmentsByDate(@RequestParam Date date) {
		List<Appointment> appointments = service.getTop5AppointmentsByDate(date);
		if (appointments != null) {
			LOG.info("Appointment Fetched !");
			return new ResponseEntity<List<Appointment>>(appointments, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("Appointment Not Exists");
		}
	}
}
