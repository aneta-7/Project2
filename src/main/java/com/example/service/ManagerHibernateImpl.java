package com.example.service;

import java.util.List;
import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Bouquet;
import com.example.domain.User;

@Component
@Transactional
public class ManagerHibernateImpl implements Manager{

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	

	
	
	@Override
	public void deleteUser(User user) {
		sessionFactory.getCurrentSession().delete(user);	
	}
	
	@Override
	public List<User> getAllUsers() {
		return sessionFactory.getCurrentSession().getNamedQuery("user.all").list();
	}
	@Override
	public void updateUser(User user) {
		sessionFactory.getCurrentSession().merge(user);
	}
	@Override
	public User findUserById(User user) {
		return (User) sessionFactory.getCurrentSession().get(User.class, user.getId());
	}
	@Override
	public void addUser(User user) {
		sessionFactory.getCurrentSession().persist(user);
	}
	@Override
	public User findUserByNick(String nick) {
		return (User) sessionFactory.getCurrentSession().getNamedQuery("user.byNick").setString("nick",nick).list();
	}
	
	
	
	
	@Override
	public void addNewBouquet(Bouquet bouquet) {
		sessionFactory.getCurrentSession().persist(bouquet);	
	}
	@Override
	public Bouquet findBouquetById(Bouquet bouquet) {
		return (Bouquet) sessionFactory.getCurrentSession().get(Bouquet.class, bouquet.getId());
	}
	@Override
	public List<Bouquet> findBouquetByColor(String color) {
		return sessionFactory.getCurrentSession().getNamedQuery("bouquet.color").setString("color", color).list();
	}
	@Override
	public void updateBouquet(Bouquet bouquet) {
		sessionFactory.getCurrentSession().merge(bouquet);	
		
	}
	//usuawnie kaskadowe
	public void deleteBouquet(User user, Bouquet bouquet) {
		sessionFactory.getCurrentSession().delete(bouquet);
		user.setBouquets(null);
	}
	
	@Override
	public List<Bouquet> getAllBouquets() {
		return sessionFactory.getCurrentSession().getNamedQuery("bouquet.all").list();
	}

	@Override
	public List<Bouquet> findUserByBouquet(User user) {
		User u = (User) sessionFactory.getCurrentSession().get(User.class, user.getId());
		return u.getBouquets();
	}
	
	@Override
	public void addBouquetToUser(Bouquet bouquet, User user) {
		User u = (User) sessionFactory.getCurrentSession().get(User.class, user.getId());
		u.getBouquets().add(bouquet);
	}
}
