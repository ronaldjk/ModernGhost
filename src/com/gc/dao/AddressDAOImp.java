package com.gc.dao;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.gc.model.Address;
import com.gc.util.HibernateUtil;

public class AddressDAOImp implements AddressDAO {

	@Override
	public ArrayList<Address> getAllAddress() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Criteria crit = session.createCriteria(Address.class);
		ArrayList<Address> ghostList = (ArrayList<Address>) crit.list();
		tx.commit();
		session.close();

		return ghostList;
	}

	@Override
	public Address getAddress() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Criteria crit = session.createCriteria(Address.class);
		Address ghost = (Address) crit.list();
		tx.commit();
		session.close();
		return ghost;
	}

	@Override
	public void addAddress(Address address) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Address ghost = address;
		session.save(ghost);
		tx.commit();
		session.close();

	}

}
