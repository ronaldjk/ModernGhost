package com.gc.dao;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.gc.model.UserLoc;
import com.gc.util.HibernateUtil;

public class UserLocDAOImp implements UserLocDAO {

	@Override
	public ArrayList<UserLoc> getAllUserLoc() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Criteria crit = session.createCriteria(UserLoc.class);
		ArrayList<UserLoc> ghostList = (ArrayList<UserLoc>) crit.list();
		tx.commit();
		session.close();

		return ghostList;
	}

	@Override
	public UserLoc getUserLoc() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Criteria crit = session.createCriteria(UserLoc.class);
		UserLoc ghost = (UserLoc) crit.list();
		tx.commit();
		session.close();
		return ghost;
	}

	@Override
	public void addUserLoc(UserLoc userLoc) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(userLoc);
		tx.commit();
		session.close();

	}

	@Override
	public void deleteUserLoc(UserLoc userLoc) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(userLoc);
		tx.commit();
		session.close();
	}

}
