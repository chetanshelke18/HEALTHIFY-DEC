package com.hms.api.controller;

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

import com.hms.api.entity.Medicine;
import com.hms.api.exception.ResourceNotFoundException;
import com.hms.api.service.MedicineService;

/**
 * @author RAM
 *
 */
@RestController
@RequestMapping(value = "/medicine")
public class MedicineController {

	@Autowired
	private MedicineService medicineService;

	private static Logger LOG = LogManager.getLogger(MedicineController.class);

	@PostMapping(value = "/add-medicine")
	public ResponseEntity<Medicine> addMedicine(@RequestBody Medicine medicine) {

		Medicine addMedicine = medicineService.addMedicine(medicine);
		if (addMedicine != null) {
			LOG.info("Medicine Saved Successfully: " + medicine);
			return new ResponseEntity<Medicine>(addMedicine, HttpStatus.ACCEPTED);
		} else {
			LOG.info("Medicine Failed To Add: " + medicine.getId());
			throw new ResourceNotFoundException(
					"The Medicine which you are trying to add that can't be Saved because it already Exists: "
							+ medicine.getId());
		}
	}

	@GetMapping(value = "/get-medicine-by-name-ignorecase/{medicineName}")
	public ResponseEntity<List<Medicine>> findByNameContainingIgnoreCase(@PathVariable String medicineName) {
		List<Medicine> medicines = medicineService.getMedicinesByName(medicineName);
		if (!medicines.isEmpty()) {
			LOG.info("Medicines Found Successfully " + medicines);
			return new ResponseEntity<List<Medicine>>(medicines, HttpStatus.FOUND);
		} else {
			LOG.info("Medicines Not Found For " + medicineName);
			throw new ResourceNotFoundException("Medicines Not Found for given " + medicineName);
		}
	}

	@GetMapping(value = "/get-medicine-by-name/{medicineName}")
	public ResponseEntity<Medicine> getMedicineByName(@PathVariable String medicineName) {
		Medicine medicine = medicineService.getMedicineByName(medicineName);
		if (medicine != null) {
			LOG.info("Medicine Found Successfully " + medicine);
			return new ResponseEntity<>(medicine, HttpStatus.FOUND);
		} else {
			throw new ResourceNotFoundException("Medicine Not Found For " + medicineName);
		}
	}

	@GetMapping(value = "/get-medicines-with-quantity-more-than-zero/{quantity}")
	public ResponseEntity<List<Medicine>> getMedicinesWithQuantityMoreThanZero(@PathVariable int quantity) {
		List<Medicine> medicines = medicineService.getMedicinesWithQuantityMoreThanZero(quantity);
		if (!medicines.isEmpty()) {
			LOG.info("Medicines Found Successfully for Quantity " + quantity);
			return new ResponseEntity<List<Medicine>>(medicines, HttpStatus.FOUND);
		} else {
			LOG.info("No Medicines Found greater than " + quantity + "quantity");
			throw new ResourceNotFoundException("No Medicines Found greater than " + quantity + "quantity");
		}
	}

	// One API is pending

	@GetMapping(value = "/get-medicines-total-count")
	public ResponseEntity<Long> getMedicinesTotalCount() {
		Long medicinesTotalCount = medicineService.getMedicinesTotalCount();
		if (medicinesTotalCount != null) {
			LOG.info("Medicines Count Method Called");
			return new ResponseEntity<Long>(medicinesTotalCount, HttpStatus.OK);
		} else {
			LOG.info("No Medicines Present in DB so Count Method Failed");
			throw new ResourceNotFoundException("No Medicines Are Stored in Database");
		}
	}

	@GetMapping(value = "/get-medicine-by-id/{id}")
	public ResponseEntity<Medicine> getMedicineById(@PathVariable String id) {
		Medicine medicineById = medicineService.getMedicineById(id);
		if (medicineById != null) {
			LOG.info("Medicine Found Successfully for " + id);
			return new ResponseEntity<Medicine>(medicineById, HttpStatus.OK);
		} else {
			LOG.info("No Medicines Present in DB so Method Failed for " + id);
			throw new ResourceNotFoundException("No Medicines Are Stored in Database");
		}
	}

	@GetMapping(value = "/get-all-medicine")
	public ResponseEntity<List<Medicine>> getAllMedicine() {
		List<Medicine> allMedicine = medicineService.getAllMedicine();
		if (!allMedicine.isEmpty()) {
			LOG.info("Medicine Found Successfully");
			return new ResponseEntity<List<Medicine>>(allMedicine, HttpStatus.OK);
		} else {
			LOG.info("No Medicines Present in DB so Method Failed");
			throw new ResourceNotFoundException("No Medicines Are Stored in Database");
		}
	}
}
