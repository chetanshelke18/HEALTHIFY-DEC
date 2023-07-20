package com.hms.api.serviceimpl;

import java.time.LocalDate;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hms.api.dao.MedicineDao;
import com.hms.api.entity.Medicine;
import com.hms.api.service.MedicineService;

/**
 * @author RAM
 *
 */
@Service
public class MedicineServiceImp implements MedicineService {

	@Autowired
	private MedicineDao medicineDao;

	@Override
	public Medicine addMedicine(Medicine medicine) {
		Date date = Date.valueOf(LocalDate.now());
		String id = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date());
		medicine.setId(id);
		medicine.setMedicineAddedDateInStock(date);

		return medicineDao.addMedicine(medicine);
	}

	@Override
	public boolean deleteMedicineById(String id) {
		return medicineDao.deleteMedicineById(id);
	}

	@Override
	public Medicine getMedicineById(String id) {
		Medicine medicine = medicineDao.getMedicineById(id);

		return medicine;
	}

	@Override
	public Medicine updateMedicine(Medicine medicine) {
		return medicineDao.updateMedicine(medicine);
	}

	@Override
	public List<Medicine> getAllMedicine() {
		return medicineDao.getAllMedicine();
	}

	@Override
	public List<Medicine> getMedicinesByName(String medicineName) {
		return medicineDao.findByNameContainingIgnoreCase(medicineName);
	}

	@Override
	public Medicine getMedicineByName(String medicineName) {
		return medicineDao.findByName(medicineName);
	}

	@Override
	public List<Medicine> getMedicinesWithQuantityMoreThanZero(int quantity) {
		return medicineDao.findByQuantityGreaterThan(quantity);
	}

	@Override
	public Long getCountOfMedicineByDateAdded(String dateAdded) {
		return medicineDao.countByDateAdded(dateAdded);
	}

	@Override
	public Long getMedicinesTotalCount() {
		return medicineDao.getTotalCount();
	}

	@Override
	public List<Medicine> getTop5MedicineAddedByDate(String date) {
		return medicineDao.findTop5ByIdDesc(date);
	}

}
