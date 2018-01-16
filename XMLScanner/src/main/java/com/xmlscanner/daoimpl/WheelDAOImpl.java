package com.xmlscanner.daoimpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xmlscanner.dao.WheelDAO;
import com.xmlscanner.model.Wheel;

@Transactional
@Repository("wheelDAO")
public class WheelDAOImpl implements WheelDAO{
	
@Autowired
private SessionFactory sessionFactory;
	
	@Transactional
	public Wheel get(String id) {
		// TODO Auto-generated method stub
		return (Wheel) sessionFactory.getCurrentSession().createQuery("from Wheel where id='"+id+"'").uniqueResult();
	}

	@Transactional
	public Wheel save(Wheel wheel) {
		wheel.setId(getMaxId());
		
		try {
			sessionFactory.getCurrentSession().save(wheel);
			return wheel;

		} catch (HibernateException e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	private Long getMaxId() {
		

		Long maxID = 100L;
		try {
			String hql = "select max(id) from Wheel";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			if (query !=null && query.uniqueResult() !=null) {
				maxID = (Long) query.uniqueResult();
			}
			
		} catch (HibernateException e) {
			
			maxID = 100L;
			return maxID + 1;
			//e.printStackTrace();
		}
		
		return maxID + 1;

	}
	
	

	@Override
	public List<Wheel> getAll() {
		return sessionFactory.getCurrentSession().createQuery("from Wheel").list(); 
	}
	

}
