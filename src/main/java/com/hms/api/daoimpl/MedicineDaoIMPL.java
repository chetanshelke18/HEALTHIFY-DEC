package com.hms.api.daoimpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hms.api.dao.MedicineDao;
import com.hms.api.entity.Medicine;

/**
 * @author RAM
 *
 */
@Repository
public class MedicineDaoIMPL implements MedicineDao {

	@Autowired
	private SessionFactory sf;

	@Override
	public Medicine addMedicine(Medicine medicine) {
		Session session = sf.getCurrentSession();
		Medicine medicineDB = null;

		try {
			medicineDB = session.get(Medicine.class, medicine.getId());
			if (medicineDB == null) {
				session.save(medicine);
			} else {
				medicine = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return medicine;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Medicine> findByNameContainingIgnoreCase(String medicineName) {
		Session session = sf.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Medicine.class);
		List<Medicine> medicines = null;

		try {
			Criterion like = Restrictions.like("name", "%"+medicineName+"%");
			criteria.add(like);
			medicines = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return medicines;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Medicine findByName(String medicineName) {
		Session session = sf.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Medicine.class);
		List<Medicine> medicines = null;
		Medicine medicine = null;

		try {
			Criterion like = Restrictions.eq("name", medicineName);
			criteria.add(like);
			medicines = criteria.list();
			medicine = medicines.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return medicine;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Medicine> findByQuantityGreaterThan(int quantity) {
		Session session = sf.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Medicine.class);
		List<Medicine> medicines = null;
		try {
			medicines = criteria.list();
			for (Medicine medicine : medicines) {
				if (medicine.getQuantity() >= quantity) {
					medicines.add(medicine);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return medicines;
	}

	@Override
	public Long countByDateAdded(String dateAdded) {
		Session session = sf.getCurrentSession();
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Long getTotalCount() {
		Session session = sf.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Medicine.class);
		long medicinesCount = 0;
		try {
			criteria.setProjection(Projections.rowCount());
			medicinesCount = (long) criteria.list().get(0);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return medicinesCount;
	}


	@Override
	public List<Medicine> findTop5ByIdDesc(String date) {
		Session session = sf.getCurrentSession();
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	@Override
	public boolean deleteMedicineById(String id) {
		Session session = sf.getCurrentSession();
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public Medicine getMedicineById(String id) {
		Session session = sf.getCurrentSession();
		Medicine medicine = null;
		try {
			medicine = session.get(Medicine.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return medicine;
	}

	
	@Override
	public Medicine updateMedicine(Medicine medicine) {
		Session session = sf.getCurrentSession();
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return medicine;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Medicine> getAllMedicine() {
		Session session = sf.getCurrentSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Medicine.class);
		List<Medicine> medicines = null;
		try {
			medicines = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return medicines;
	}

}
