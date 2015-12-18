package com.example.service;

import static org.junit.Assert.assertEquals;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Bouquet;
import com.example.domain.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class SellingManagerTest{

	@Autowired
	Manager manager;

	private final String NAME_1 = "Aneta";
	private final String NICK_1 = "kot";

	private final String NAME_2 = "Karol";
	private final String NICK_2 = "mysz";

	private final String TYPE_1 = "wiazanka";
	private final String COLOR_1 = "czerwony";
	
	private final String TYPE_2 = "bukiet";
	private final String COLOR_2 = "bialy";
	
	private final long ID_1 = 1;
	private final long ID_2 = 2;
	
	
	
	private SessionFactory sessionFactory;
	private Session session = null;

	
	@After
	public void after(){
		
	}
	@Before
	public void before(){
		
	}
	
	@Test
	public void addClientCheck() {

		int n = manager.getAllUsers().size();
		User user = new User( NAME_1, NICK_1);
		user.setName(NAME_1);
		user.setNick(NICK_1);

		manager.addUser(user);
		User retrievedUser = manager.findUserById(user);
		assertEquals(user.getId(), retrievedUser.getId());
		assertEquals(NAME_1, retrievedUser.getName());
		assertEquals(NICK_1, retrievedUser.getNick());

		assertEquals(n+1, manager.getAllUsers().size());
	}
	
	@Test
	public void deleteClientCheck(){
		User user = new User(NAME_1, NICK_1);
		user.setName(NAME_1);
		user.setNick(NICK_1);

		manager.addUser(user);
		int n = manager.getAllUsers().size();

		User retrievedUser = manager.findUserById(user);
		assertEquals(user.getId(), retrievedUser.getId());
		assertEquals(NAME_1, retrievedUser.getName());
		assertEquals(NICK_1, retrievedUser.getNick());

		manager.deleteUser(user);
		assertEquals(n-1, manager.getAllUsers().size());
	}
	
	@Test 
	public void updateClientCheck(){
		User user = new User(NAME_1, NICK_1);
		user.setName(NAME_1);
		user.setNick(NICK_1);

		manager.addUser(user);
		
		User retrievedUser = manager.findUserById(user);
		assertEquals(user.getId(), retrievedUser.getId());
		assertEquals(NAME_1, retrievedUser.getName());
		assertEquals(NICK_1, retrievedUser.getNick());
		
		retrievedUser.setName(NAME_2);
		retrievedUser.setNick(NICK_2);
		manager.updateUser(retrievedUser);
		
		User retrievedUser2 = manager.findUserById(retrievedUser);
		assertEquals(retrievedUser.getId(), retrievedUser2.getId());
		assertEquals(NAME_2, retrievedUser2.getName());
		assertEquals(NICK_2, retrievedUser2.getNick());
	}
	
	@Test
	public void addBouquetCheck() {

		int n = manager.getAllBouquets().size();
		
		Bouquet bouquet = new Bouquet(TYPE_1,COLOR_1);
		bouquet.setType(TYPE_1);
		bouquet.setColor(COLOR_1);

		manager.addNewBouquet(bouquet);
	
		Bouquet retrievedBouquet = manager.findBouquetById(bouquet);
		assertEquals(TYPE_1, ((Bouquet) retrievedBouquet).getType());
		assertEquals(COLOR_1, ((Bouquet) retrievedBouquet).getColor());
				
		assertEquals(n+1, manager.getAllBouquets().size());
	}
	
	@Test 
	public void deleteBouquetCheck(){
		User user = new User(NAME_1,NICK_1, ID_1);
		Bouquet bouquet = new Bouquet(TYPE_1, COLOR_1);
		bouquet.setType(TYPE_1);
		bouquet.setColor(COLOR_1);

		manager.addNewBouquet(bouquet);;
		int n = manager.getAllBouquets().size();

		Bouquet retrievedUser = manager.findBouquetById(bouquet);
		assertEquals(bouquet.getId(), retrievedUser.getId());
		assertEquals(TYPE_1, retrievedUser.getType());
		assertEquals(COLOR_1, retrievedUser.getColor());

		manager.deleteBouquet(user, bouquet);
		assertEquals(n, manager.getAllBouquets().size());
	}
	
	@Test 
	public void updateBouquetCheck(){
		Bouquet bouquet = new Bouquet(TYPE_1, COLOR_1);
		bouquet.setType(TYPE_1);
		bouquet.setColor(COLOR_1);

		manager.addNewBouquet(bouquet);
		
		Bouquet retrievedUser = manager.findBouquetById(bouquet);
		assertEquals(bouquet.getId(), retrievedUser.getId());
		assertEquals(TYPE_1, retrievedUser.getType());
		assertEquals(COLOR_1, retrievedUser.getColor());
		
		retrievedUser.setType(TYPE_2);
		retrievedUser.setColor(COLOR_2);
		manager.updateBouquet(retrievedUser);
		
		Bouquet retrievedUser2 = manager.findBouquetById(retrievedUser);
		assertEquals(retrievedUser.getId(), retrievedUser2.getId());
		assertEquals(TYPE_2, retrievedUser2.getType());
		assertEquals(COLOR_2, retrievedUser2.getColor());
	}


}