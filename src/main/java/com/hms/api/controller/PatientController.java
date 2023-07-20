package com.hms.api.controller;

import java.sql.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hms.api.entity.Patient;
import com.hms.api.exception.ResourceAlreadyExistsException;
import com.hms.api.exception.ResourceNotFoundException;
import com.hms.api.service.PatientService;

/**
 * @author RAM
 *
 */
@RestController
@RequestMapping(value = "/patient")
public class PatientController {

	private static Logger LOG = LogManager.getLogger(PatientController.class);

	@Autowired
	private PatientService patientService;

	@PostMapping(value = "/add-patient")
	public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {

		Patient addedPatient = patientService.addPatient(patient);

		if (addedPatient != null) {
			LOG.info("Added Patient :" + addedPatient);
			return new ResponseEntity<>(addedPatient, HttpStatus.CREATED);
		} else {
			LOG.info("Patient Already Exixts With >ID:" + patient.getId());
			throw new ResourceAlreadyExistsException("Patient Already Exixts With >ID: " + patient.getId());
		}
	}

	@DeleteMapping(value = "/delete-patient-by-id/{id}")
	public ResponseEntity<String> deletePatientById(@PathVariable String id) {

		boolean deletedPatient = patientService.deletePatientById(id);
		if (deletedPatient) {
			LOG.info("Patient deleted successfully");
			return new ResponseEntity<String>("Patient deleted successfully", HttpStatus.OK);
		} else {
			LOG.info("Something went to wrong Patient not deleted with id : " + id);
			throw new ResourceNotFoundException("Patient not found with >ID: " + id);
		}

	}

	@GetMapping(value = "/get-patient-by-id/{id}")
	public ResponseEntity<Patient> getPatientById(@PathVariable String id) {

		Patient patient = patientService.getPatientById(id);

		if (patient != null) {
			LOG.info("Get Patient by Id : " + id);
			return new ResponseEntity<Patient>(patient, HttpStatus.OK);
		} else {
			LOG.info("Patient not found by ID : " + id);
			throw new ResourceNotFoundException("Patient not found with >ID: " + id);
		}

	}

	@PutMapping(value = "/update-patient")
	public ResponseEntity<String> updatePatient(@RequestBody Patient patient) {

		Patient updatedPatient = patientService.updatePatient(patient);
		String msg = "";
		if (updatedPatient != null) {
			LOG.info("Patient updated successfully and patient details is " + updatedPatient);
			msg = "Patient updated successfully";
		} else {
			LOG.info("Patient failed to updated and patient details is " + patient);
			msg = "Patient failed to update";
		}
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}

	@GetMapping(value = "/get-all-patient")
	public ResponseEntity<List<Patient>> getAllPatient() {

		List<Patient> listOfPatients = patientService.getAllPatients();

		if (listOfPatients.isEmpty()) {
			LOG.info("Patient not found");
			throw new ResourceNotFoundException("Patient not found");
		} else {
			LOG.info("Get all Patient.");
			return new ResponseEntity<List<Patient>>(listOfPatients, HttpStatus.OK);
		}

	}

	@GetMapping(value = "/get-patientsId-by-patientname/{name}")
	public ResponseEntity<List<Patient>> findByFirstnameContainingIgnoreCase(@PathVariable String name) {
		List<Patient> listOfPatients = patientService.findByFirstnameContainingIgnoreCase(name);

		if (listOfPatients.isEmpty()) {
			LOG.info("Patient found with Name: " + name);
			throw new ResourceNotFoundException("Patient not found with >NAME: " + name);
		} else {
			LOG.info("Patient not found with Name: " + name);
			return new ResponseEntity<List<Patient>>(listOfPatients, HttpStatus.OK);
		}

	}

	@GetMapping(value = "/get-total-count-of-patient")
	public ResponseEntity<Long> getPatientsCount() {

		Long patientsCount = patientService.getPatientsCount();
		if (patientsCount > 0) {
			LOG.info("total count of Patients is " + patientsCount);
			return new ResponseEntity<Long>(patientsCount, HttpStatus.OK);

		} else {
			LOG.info("Patients Not Exists");
			throw new ResourceNotFoundException("Patients Not Exists");
		}

	}

	@GetMapping(value = "/count-by-registerdate")
	public ResponseEntity<Long> getPatientsCountByDate(Date registeredDate) {

		Long patientsCountByDate = patientService.getPatientsCountByDate(registeredDate);

		if (patientsCountByDate > 0) {
			LOG.info("total count of Patients is " + patientsCountByDate + " by registerdate: " + registeredDate);
			return new ResponseEntity<Long>(patientsCountByDate, HttpStatus.OK);

		} else {
			LOG.info("Patients Not Exists");
			throw new ResourceNotFoundException("Patients Not Exists");
		}


	}

	@GetMapping(value = "/top5-patients-by-date")
	public ResponseEntity<List<Patient>> getTop5PatientAddedByDate() {

		List<Patient> patients = patientService.getTop5PatientAddedByDate();
		if (!patients.isEmpty()) {
			LOG.info("all Patient.");
			return new ResponseEntity<List<Patient>>(patients, HttpStatus.OK);
			
		} else {
			LOG.info("Patients not found");
			throw new ResourceNotFoundException("Patient not found");
		}

	}

	@GetMapping(value = "/get-all-patients-ids")
	public ResponseEntity<List<String>> getAllPatientsIds() {

		List<String> patients = patientService.getAllPatientsIds();

		if (!patients.isEmpty()) {
			LOG.info("all Patient IDs.");
			return new ResponseEntity<List<String>>(patients, HttpStatus.OK);
			
		} else {
			LOG.info("Patients not found");
			throw new ResourceNotFoundException("Patient not found");
		}
		
	}

}
