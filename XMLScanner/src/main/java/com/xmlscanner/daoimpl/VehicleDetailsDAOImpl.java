package com.xmlscanner.daoimpl;



import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xmlscanner.dao.VehicleDetailsDAO;
import com.xmlscanner.model.VehicleDetails;
@Transactional
@Repository("vehicleDetailsDAO")
public class VehicleDetailsDAOImpl implements VehicleDetailsDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public VehicleDetails get(String id) {
		// TODO Auto-generated method stub
		return (VehicleDetails) sessionFactory.getCurrentSession().createQuery("from VehicleDetails where id='"+id+"'").uniqueResult();
	}

	@Transactional
	public VehicleDetails save(VehicleDetails vehicleDetails) {
		vehicleDetails.setId(getMaxId());
		vehicleDetails.setTimestamp(LocalDateTime.now());
		
		try {
			sessionFactory.getCurrentSession().save(vehicleDetails);
			return vehicleDetails;

		} catch (HibernateException e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	private Long getMaxId() {
		

		Long maxID = 100L;
		try {
			String hql = "select max(id) from VehicleDetails";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			if (query !=null && query.uniqueResult() !=null) {
				maxID = (Long) query.uniqueResult();
			}
			
		} catch (HibernateException e) {
			
			maxID = 100L;
			e.printStackTrace();
		}
		
		return maxID + 1;

	}
	
	@Transactional
	public Long getMaxXMLString() {
		

		Long maxID = 100L;
		try {
			String hql = "select max(XMLString) from VehicleDetails";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			if (query !=null && query.uniqueResult() !=null) {
				maxID = (Long) query.uniqueResult();
			}
			
		} catch (HibernateException e) {
			
			maxID = 100L;
			e.printStackTrace();
		}
		
		return maxID + 1;

	}

	@Override
	public List<VehicleDetails> getAll() {

		
		return sessionFactory.getCurrentSession().createQuery("from VehicleDetails").list(); 
	}

	
	@Override
	public List<VehicleDetails> getVehiclesByFileId(long id) {
		
		
		return sessionFactory.getCurrentSession().createQuery("from VehicleDetails where fileid="+id).list();
	}

}
