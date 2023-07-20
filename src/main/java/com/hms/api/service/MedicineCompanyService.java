package com.hms.api.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hms.api.entity.MedicineCompany;

/**
 * @author RAM
 *
 */
@Transactional
public interface MedicineCompanyService {
	MedicineCompany addMedicineCompany(MedicineCompany medicineCompany);
	boolean deleteMedicineCompanyById(String id);
	MedicineCompany getMedicineCompanyById(String id);
	MedicineCompany updateMedicineCompany(MedicineCompany medicineCompany);
	List<MedicineCompany> getAllMedicineCompanys();
	List<MedicineCompany> getCompanysByName(String companyName);
	MedicineCompany getCompanyByName(String companyName);
	Long getCountByRegisteredDate(String registeredDate);
	List<MedicineCompany> getTop5CompanyAddedByDate(String registeredDate);
 }
