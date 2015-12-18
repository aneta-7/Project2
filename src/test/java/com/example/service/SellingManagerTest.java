package com.example.service;

import static org.junit.Assert.assertEquals;

import java.awt.Menu;
import java.util.List;

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
	 
	private final boolean available_1= true;
	private final boolean available_2 = false;
	
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
		
		Bouquet bouquet = new Bouquet(TYPE_1, COLOR_1);
		bouquet.setType(TYPE_1);
		bouquet.setColor(COLOR_1);


		manager.addNewBouquet(bouquet);
	
		Bouquet retrievedBouquet = manager.findBouquetById(bouquet);
		assertEquals(TYPE_1, retrievedBouquet.getType());
		assertEquals(COLOR_1, retrievedBouquet.getColor());
				
		assertEquals(n+1, manager.getAllBouquets().size());
	}
	
	@Test 
	public void deleteBouquetCheck(){
		User user = new User(NAME_1,NICK_1);
		Bouquet bouquet = new Bouquet(TYPE_1, COLOR_1);
		bouquet.setType(TYPE_1);
		bouquet.setColor(COLOR_1);		

		manager.addNewBouquet(bouquet);;
		int n = manager.getAllBouquets().size();

		Bouquet retrievedBouquet = manager.findBouquetById(bouquet);
		assertEquals(bouquet.getId(), retrievedBouquet.getId());
		assertEquals(TYPE_1, retrievedBouquet.getType());
		assertEquals(COLOR_1, retrievedBouquet.getColor());

		manager.deleteBouquet(user, bouquet);
		assertEquals(n-1, manager.getAllBouquets().size());
		assertEquals(null,user.getBouquets());
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
	
	@Test 
	public void findBouqetByColorChceck(){
		List<Bouquet> bouquetColor = manager.findBouquetByColor(COLOR_1);
		int n = bouquetColor.size();
		
		Bouquet bouquet = new Bouquet(TYPE_1, COLOR_1);
		bouquet.setType(TYPE_1);
		bouquet.setColor(COLOR_1);


		manager.addNewBouquet(bouquet);
	
		Bouquet retrievedBouquet = manager.findBouquetById(bouquet);
		assertEquals(TYPE_1, retrievedBouquet.getType());
		assertEquals(COLOR_1, retrievedBouquet.getColor());

		
		Bouquet bouquet2 = new Bouquet(TYPE_2, COLOR_1);
		bouquet2.setType(TYPE_2);
		bouquet2.setColor(COLOR_1);

		manager.addNewBouquet(bouquet2);
		
		Bouquet retrievedBouquet2 = manager.findBouquetById(bouquet2);
		assertEquals(TYPE_2, retrievedBouquet2.getType());
		assertEquals(COLOR_1, retrievedBouquet2.getColor());
		
		List<Bouquet> color = manager.findBouquetByColor(COLOR_1);
		
		for(Bouquet aBouquet : color){
			Bouquet bouquet3 = manager.findBouquetById(aBouquet);
			assertEquals(aBouquet.getId(), bouquet3.getId());
			assertEquals(aBouquet.getType(), bouquet3.getType());
			assertEquals(aBouquet.getColor(), bouquet3.getColor());
		}

		assertEquals(n+2, color.size());
		
	}
	@Test
	//szukanie uzytkownikow o zadanym id bukietu
	public void findCheck(){

		Bouquet bouquet = new Bouquet(TYPE_1, COLOR_1);
		bouquet.setType(TYPE_2);
		bouquet.setColor(COLOR_1);

		manager.addNewBouquet(bouquet);
		
		Bouquet retrievedBouquet = manager.findBouquetById(bouquet);
		assertEquals(TYPE_2, retrievedBouquet.getType());
		assertEquals(COLOR_1, retrievedBouquet.getColor());
		
		Bouquet bouquet2 = new Bouquet(TYPE_2, COLOR_1);
		bouquet2.setType(TYPE_2);
		bouquet2.setColor(COLOR_1);

		manager.addNewBouquet(bouquet2);
		
		Bouquet retrievedBouquet2 = manager.findBouquetById(bouquet2);
		assertEquals(TYPE_2, retrievedBouquet2.getType());
		assertEquals(COLOR_1, retrievedBouquet2.getColor());
		
		User user = new User(NAME_1, NICK_1);
		user.setName(NAME_1);
		user.setNick(NICK_1);

		manager.addUser(user);
		
		User retrievedUser = manager.findUserById(user);
		assertEquals(user.getId(), retrievedUser.getId());
		assertEquals(NAME_1, retrievedUser.getName());
		assertEquals(NICK_1, retrievedUser.getNick());
		
		List<Bouquet> userInBouquet = manager.findUserByBouquet(retrievedUser);
		int n = userInBouquet.size();
		
		manager.addBouquetToUser(retrievedBouquet, retrievedUser);
		manager.addBouquetToUser(retrievedBouquet2, retrievedUser);
		
		assertEquals(n+2, userInBouquet.size());
		
		for(Bouquet aBouquet : userInBouquet){
			Bouquet bouquet3 = manager.findBouquetById(aBouquet);
			assertEquals(aBouquet.getId(), bouquet3.getId());
			assertEquals(aBouquet.getType(), bouquet3.getType());
			assertEquals(aBouquet.getColor(), bouquet3.getColor());
		}
	}
}