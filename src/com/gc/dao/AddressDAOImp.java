package com.gc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.gc.model.Address;
import com.gc.util.APICredentials;
import com.gc.util.HibernateUtil;

public class AddressDAOImp implements AddressDAO {

	// this method creates an array list of all rows in the database
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

	// this method creates an address object of a row from the table in the database
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

	// this method adds/creates a new row in the table
	@Override
	public void addAddress(Address address) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(address);
		tx.commit();
		session.close();

	}
	
	public static void addPlace(String place, String address) throws ClassNotFoundException, SQLException {
        
        String url = "jdbc:mysql://localhost:3306/modernghost";
        String userName = "root";
        String password = APICredentials.JDBCCONNECTION;

        Class.forName("com.mysql.jdbc.Driver");

        // Step #3: Create Connection
        Connection con = DriverManager.getConnection(url, userName, password);
        String preparedSql = "Update address SET Place = \"" + place + "\" WHERE Address = \"" + address + "\";";
        
        
        PreparedStatement add = con.prepareStatement(preparedSql);
        add.execute();
        con.close();
        
    }

}
