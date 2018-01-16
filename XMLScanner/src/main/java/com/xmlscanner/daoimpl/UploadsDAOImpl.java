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

import com.xmlscanner.dao.UploadsDAO;
import com.xmlscanner.model.Uploads;

@Transactional
@Repository("uploadsDAO")
public class UploadsDAOImpl implements UploadsDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public Uploads get(String id) {
		// TODO Auto-generated method stub
		return (Uploads) sessionFactory.getCurrentSession().createQuery("from Uploads where id='"+id+"'").uniqueResult();
	}

	@Transactional
	public Uploads save(Uploads uploads) {
		uploads.setId(getMaxId());
		uploads.setTimestamp(LocalDateTime.now());
		try {
			sessionFactory.getCurrentSession().save(uploads);
			return uploads;

		} catch (HibernateException e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	private Long getMaxId() {
		

		Long maxID = 100L;
		try {
			String hql = "select max(id) from Uploads";
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
	public List<Uploads> getAll() {

		
		return sessionFactory.getCurrentSession().createQuery("from Uploads").list(); 
	}

}
