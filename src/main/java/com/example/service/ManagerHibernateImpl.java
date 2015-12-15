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
	
	public void addUser(User user) {
		user.setId(null);
		sessionFactory.getCurrentSession().persist(user);
	}
	public void deleteUser(User user) {
		user = (User) sessionFactory.getCurrentSession().get(User.class,
				user.getId());
		
		// lazy loading here
		for (Bouquet bouquet : user.getBouquets()) {
			bouquet.setSold(false);
			sessionFactory.getCurrentSession().update(bouquet);
		}
		sessionFactory.getCurrentSession().delete(user);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		return sessionFactory.getCurrentSession().getNamedQuery("user.all").list();
	}
	
	public User findUserByNick(String nick) {
		return (User) sessionFactory.getCurrentSession().getNamedQuery("user.byNick").setString("nick", nick).uniqueResult();
	}
	
	public void updateUser(User user) {
		 Session session = sessionFactory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         user = (User)session.get(User.class, user.getId()); 
	         user.setName(null);
	         session.update(user); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
		
	}

	public User findUserById(Long id) {
		return (User) sessionFactory.getCurrentSession().get(User.class, id);
	}
	
	public Long addNewBouquet(Bouquet bouquet) {
		bouquet.setId(null);
		return (Long) sessionFactory.getCurrentSession().save(bouquet);
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

	public Bouquet findBouquetById(Long id) {
		return (Bouquet) sessionFactory.getCurrentSession().get(Bouquet.class, id);
	}
	
	public Bouquet findBouquetByColor(String color) {
		return (Bouquet) sessionFactory.getCurrentSession().get(Bouquet.class, color);
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

	public void updateBouquet(Bouquet bouquet) {
		Session session = sessionFactory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         bouquet = (Bouquet)session.get(Bouquet.class, bouquet.getId()); 
	         bouquet.setType(null);
	         session.update(bouquet); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
		
	}
	//usuawnie kaskadowe
	public void deleteBouquet(Bouquet bouquet) {
		bouquet = (Bouquet) sessionFactory.getCurrentSession().get(Bouquet.class,
				bouquet.getId());
		
		// lazy loading here
		for (User user : bouquet.getUsers()) {
			user.setId(null);
			sessionFactory.getCurrentSession().update(user);
		}
		sessionFactory.getCurrentSession().delete(bouquet);
		
	}

	public List<Bouquet> getAvailableBouquets() {
		// TODO Auto-generated method stub
		return null;
	}

	public void sellBouquet(Long userId, Long bouquetId) {
		// TODO Auto-generated method stub
		
	}

}
