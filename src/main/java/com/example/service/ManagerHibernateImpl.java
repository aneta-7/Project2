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

import domain.Bouquet;
import domain.User;

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
	
	
	
	
	
	
	
	public void deleteUser(User user) {
		sessionFactory.getCurrentSession().delete(user);	
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		return sessionFactory.getCurrentSession().getNamedQuery("user.all").list();
	}
	
	public void updateUser(User user) {
		sessionFactory.getCurrentSession().merge(user);
	}

	public User findUserById(User user) {
		return (User) sessionFactory.getCurrentSession().get(User.class, user.getId());
	}
	
	public void addUser(User user) {
		user.setId(null);
		sessionFactory.getCurrentSession().persist(user);
	}
	public User findUserByNick(String nick) {
		return (User) sessionFactory.getCurrentSession().getNamedQuery("user.byNick").setString("nick",nick).list();
	}
	
	
	
	public void addNewBouquet(Bouquet bouquet) {
		bouquet.setId(null);
		sessionFactory.getCurrentSession().persist(bouquet);	
	}
	public Bouquet findBouquetById(Bouquet bouquet) {
		return (Bouquet) sessionFactory.getCurrentSession().get(Bouquet.class, bouquet.getId());
	}
	
	public List<Bouquet> findBouquetByColor(String color) {
		return sessionFactory.getCurrentSession().getNamedQuery("bouquet.color").setString("color", color).list();
	}
	
	public void updateBouquet(Bouquet bouquet) {
		sessionFactory.getCurrentSession().merge(bouquet);	
		
	}
	//usuawnie kaskadowe
	public void deleteBouquet(Bouquet bouquet) {
		bouquet = (Bouquet) sessionFactory.getCurrentSession().get(Bouquet.class, bouquet.getId());
		
		// lazy loading here
		for (User user : bouquet.getUsers()) {
			user.setId(null);
			sessionFactory.getCurrentSession().update(user);
		}
		sessionFactory.getCurrentSession().delete(bouquet);
		
	}
	
	
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<Bouquet> getAvailableBouquets(User user) {
		return sessionFactory.getCurrentSession().getNamedQuery("bouquet.unsold").list();
	}

	public void disposeBouquet(User user, Bouquet bouquet) {

		user = (User) sessionFactory.getCurrentSession().get(User.class,user.getId());
		bouquet = (Bouquet) sessionFactory.getCurrentSession().get(Bouquet.class, bouquet.getId());

		Bouquet toRemove = null;
		// lazy loading here (person.getCars)
		for (Bouquet aBouquet : user.getBouquets())
			if (aBouquet.getId().compareTo(bouquet.getId()) == 0) {
				toRemove = aBouquet;
				break;
			}

		if (toRemove != null)
			user.getBouquets().remove(toRemove);

		bouquet.setSold(false);
	}


	public void sellBouqet(Long userId, Long bouquetId) {
		User user = (User) sessionFactory.getCurrentSession().get(User.class, userId);
		Bouquet bouquet = (Bouquet) sessionFactory.getCurrentSession().get(Bouquet.class, bouquetId);
		bouquet.setSold(true);
		user.getBouquets().add(bouquet);
	}



	public List<Bouquet> getOwnedBouquets(User user) {
		user = (User) sessionFactory.getCurrentSession().get(User.class, user.getId());
		// lazy loading here - try this code without (shallow) copying
		List<Bouquet> bouquets = new ArrayList<Bouquet>(user.getBouquets());
		return bouquets;
	}



	public List<Bouquet> getAvailableBouquets() {
		
		return null;
	}

	public void sellBouquet(Long userId, Long bouquetId) {

	}


}
