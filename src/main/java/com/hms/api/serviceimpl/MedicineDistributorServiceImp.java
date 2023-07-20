package com.hms.api.serviceimpl;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hms.api.dao.MedicineDistributorDao;
import com.hms.api.entity.MedicineCompany;
import com.hms.api.entity.MedicineDistributor;
import com.hms.api.service.MedicineDistributorService;

/**
 * @author RAM
 *
 */
@Service
public class MedicineDistributorServiceImp implements MedicineDistributorService {

	@Autowired
	private MedicineDistributorDao medicineDistributorDao;
	
	@Override
	public MedicineDistributor addMedicineDistributor(MedicineDistributor medicineDistributor) {
		Date date = Date.valueOf(LocalDate.now());
		String id = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date());
		
		medicineDistributor.setId(id);
		medicineDistributor.setRegisterDate(date);
		
		return medicineDistributorDao.addMedicineDistributor(medicineDistributor);
	}

	@Override
	public boolean deleteMedicineDistributorById(String id) {
		return medicineDistributorDao.deleteMedicineDistributorById(id);
	}

	@Override
	public MedicineDistributor getMedicineDistributorById(String id) {
		MedicineDistributor medicineDistributor = medicineDistributorDao.getMedicineDistributorById(id);

		
			return medicineDistributor;
	}

	@Override
	public MedicineDistributor updateMedicineDistributor(MedicineDistributor medicineDistributor) {
		return medicineDistributorDao.updateMedicineDistributor(medicineDistributor);
	}

	@Override
	public List<MedicineDistributor> getAllDistributors() {
		return medicineDistributorDao.getAllDistributors();
	}

	@Override
	public List<MedicineDistributor> getDistributorsByName(String distributorName) {
		return medicineDistributorDao.getDistributorsByName(distributorName);
	}

	@Override
	public MedicineDistributor getDistributorByName(String distributorName) {
		return medicineDistributorDao.getDistributorByName(distributorName);
	}

	@Override
	public Long getCountByRegisteredDate(String registeredDate) {
		return medicineDistributorDao.getCountByRegisteredDate(registeredDate);
	}

	@Override
	public List<MedicineDistributor> getTop5CompanyAddedByDate(String date) {
		return medicineDistributorDao.getTop5CompanyAddedByDate(date);
	}

}
