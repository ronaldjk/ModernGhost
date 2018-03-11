package com.gc.dao;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.gc.model.Admin;
import com.gc.util.HibernateUtil;

public class AdminDAOImp implements AdminDAO {

	@Override
	public ArrayList<Admin> getAllAdmin() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Criteria crit = session.createCriteria(Admin.class);
		ArrayList<Admin> adminList = (ArrayList<Admin>) crit.list();
		tx.commit();
		session.close();

		return adminList;
	}

}
