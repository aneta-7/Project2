package service;

import java.util.List;

import java.util.ArrayList;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import domain.Bouqet;
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
		for (Bouqet bouqet : user.getBouqets()) {
			bouqet.setSold(false);
			sessionFactory.getCurrentSession().update(bouqet);
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

	public Long addNewBouqet(Bouqet bouqet) {
		bouqet.setId(null);
		return (Long) sessionFactory.getCurrentSession().save(bouqet);
	}
	@SuppressWarnings("unchecked")
	public List<Bouqet> getAvailableBouqets(User user) {
		return sessionFactory.getCurrentSession().getNamedQuery("bouqet.unsold").list();
	}

	public void disposeBouqet(User user, Bouqet bouqet) {

		user = (User) sessionFactory.getCurrentSession().get(User.class,user.getId());
		bouqet = (Bouqet) sessionFactory.getCurrentSession().get(Bouqet.class, bouqet.getId());

		Bouqet toRemove = null;
		// lazy loading here (person.getCars)
		for (Bouqet aBouqet : user.getBouqets())
			if (aBouqet.getId().compareTo(bouqet.getId()) == 0) {
				toRemove = aBouqet;
				break;
			}

		if (toRemove != null)
			user.getBouqets().remove(toRemove);

		bouqet.setSold(false);
	}

	public Bouqet findBouqetById(Long id) {
		return (Bouqet) sessionFactory.getCurrentSession().get(Bouqet.class, id);
	}

	public void sellUser(Long userId, Long bouqetId) {
		User user = (User) sessionFactory.getCurrentSession().get(User.class, userId);
		Bouqet bouqet = (Bouqet) sessionFactory.getCurrentSession().get(Bouqet.class, bouqetId);
		bouqet.setSold(true);
		user.getBouqets().add(bouqet);
	}

	public List<Bouqet> getAvailableBouqets() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Bouqet> getOwnedBouqets(User user) {
		user = (User) sessionFactory.getCurrentSession().get(User.class,
				user.getId());
		// lazy loading here - try this code without (shallow) copying
		List<Bouqet> bouqets = new ArrayList<Bouqet>(user.getBouqets());
		return bouqets;
	}


}
