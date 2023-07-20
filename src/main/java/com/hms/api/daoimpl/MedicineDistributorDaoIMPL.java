package com.hms.api.daoimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hms.api.dao.MedicineDistributorDao;
import com.hms.api.entity.MedicineDistributor;

/**
 * @author RAM
 *
 */
@Repository
public class MedicineDistributorDaoIMPL implements MedicineDistributorDao{

	@Autowired
	private SessionFactory sf;
	
	@Override
	public MedicineDistributor addMedicineDistributor(MedicineDistributor medicineDistributor) {
		Session session = sf.getCurrentSession();
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deleteMedicineDistributorById(String id) {
		Session session = sf.getCurrentSession();
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public MedicineDistributor getMedicineDistributorById(String id) {
		Session session = sf.getCurrentSession();
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public MedicineDistributor updateMedicineDistributor(MedicineDistributor medicineDistributor) {
		Session session = sf.getCurrentSession();
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<MedicineDistributor> getAllDistributors() {
		Session session = sf.getCurrentSession();
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<MedicineDistributor> getDistributorsByName(String distributorName) {
		Session session = sf.getCurrentSession();
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public MedicineDistributor getDistributorByName(String distributorName) {
		Session session = sf.getCurrentSession();
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Long getCountByRegisteredDate(String date) {
		Session session = sf.getCurrentSession();
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<MedicineDistributor> getTop5CompanyAddedByDate(String date) {
		Session session = sf.getCurrentSession();
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
